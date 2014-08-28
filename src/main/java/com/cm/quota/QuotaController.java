/*
 * Copyright 2010-2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.cm.quota;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;

@Controller
public class QuotaController {
	@Autowired
	private QuotaService quotaService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationService applicationService;

	private static final Logger LOGGER = Logger.getLogger(QuotaController.class
			.getName());

	private Cache mCache;

	public QuotaController() {
		super();
		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			mCache = cacheFactory.createCache(Collections.emptyMap());
		} catch (Throwable t) {
			LOGGER.log(Level.SEVERE, "Memcache not initialized!", t);
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/quota", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	com.cm.quota.transfer.Quota getQuota(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getQuota");
			User lUser = userService.getLoggedInUser();

			Quota lQuota = quotaService.get(lUser.getAccountId());

			response.setStatus(HttpServletResponse.SC_OK);
			return convert(lQuota);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getQuota");
		}
	}

	@RequestMapping(value = "/secured/quota/application/validate", method = RequestMethod.GET, produces = "application/json")
	public void validateApplicationQuota(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering ");
			response.setStatus(HttpServletResponse.SC_OK);
			User lUser = userService.getLoggedInUser();

			if (quotaService
					.hasSufficientApplicationQuota(lUser.getAccountId())) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting ");
		}
	}

	@RequestMapping(value = "/tasks/quota/update/{accountId}", method = RequestMethod.POST)
	public void updateQuota(@PathVariable Long accountId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(accountId);
			int lApplicationsUsed = lApplications.size();
			quotaService.upsertApplicationUtilization(accountId,
					lApplicationsUsed);
			for (Application lApplication : lApplications) {
				long lStorageUsedInBytes = 0;
				List<Content> lContentList = contentService.get(
						lApplication.getId(), false);
				for (Content lContent : lContentList) {
					// roll up size
					lStorageUsedInBytes += lContent.getSizeInBytes();
				}
				// update for each application
				quotaService.upsertStorageUtilization(accountId,
						lApplication.getId(), lStorageUsedInBytes);
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	private com.cm.quota.transfer.Quota convert(Quota pQuota) {
		com.cm.quota.transfer.Quota lQuota = new com.cm.quota.transfer.Quota();
		lQuota.setApplicationLimit(pQuota.getApplicationLimit());
		lQuota.setCanonicalPlanName(pQuota.getCanonicalPlanName());
		lQuota.setStorageLimitInBytes(pQuota.getStorageLimitInBytes());
		lQuota.setApplicationLimit(pQuota.getApplicationLimit());

		// int lPercentageStorageUtilized = Math
		// .round((pQuota.getStorageUsedInBytes() / pQuota
		// .getStorageLimitInBytes()) * 100);
		// lQuota.setPercentageStorageUsed(lPercentageStorageUtilized);
		// int lPercentageApplicationUtilized = Math.round((pQuota
		// .getApplicationsUsed() / pQuota.getApplicationLimit()) * 100);
		// lQuota.setPercentageApplicationUsed(lPercentageApplicationUtilized);
		return lQuota;
	}

}
