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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

import sun.org.mozilla.javascript.internal.ast.ForInLoop;

import com.cm.config.CanonicalApplicationQuota;
import com.cm.config.CanonicalPlanName;
import com.cm.config.CanonicalStorageQuota;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.stripe.StripeCustomer;
import com.cm.stripe.StripeCustomerService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;

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
	@Autowired
	private StripeCustomerService stripeCustomerService;

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
	List<com.cm.quota.transfer.Quota> getQuota(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getQuota");
			User lUser = userService.getLoggedInUser();

			List<Quota> lQuotas = quotaService.getAll(lUser.getAccountId());

			response.setStatus(HttpServletResponse.SC_OK);
			return convert(lQuotas,
					quotaService.getApplicationQuotaUsed(lUser.getAccountId()),
					quotaService.getStorageQuotaUsed(lUser.getAccountId()));
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

	@RequestMapping(value = "/secured/quota/{applicationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	com.cm.quota.transfer.Quota getQuota(@PathVariable Long applicationId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getQuota");

			User lUser = userService.getLoggedInUser();
			Quota lQuota = quotaService.get(applicationId);

			response.setStatus(HttpServletResponse.SC_OK);
			return convert(lQuota, quotaService.getApplicationQuotaUsed(lUser
					.getAccountId()), quotaService.getStorageQuotaUsed(
					lUser.getAccountId(), applicationId));
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

	@RequestMapping(value = "/tasks/quota/utilization/update/{accountId}", method = RequestMethod.POST)
	public void updateQuotaUtilizaton(@PathVariable Long accountId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			updateQuotaUtilizaton(accountId);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/tasks/quota/update/{accountId}", method = RequestMethod.POST)
	public void updateQuota(@PathVariable Long accountId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			StripeCustomer lStripeCustomer = stripeCustomerService
					.get(accountId);
			String lNewCanonicalPlanName = lStripeCustomer
					.getCanonicalPlanName();

			Quota lExistingQuota = quotaService.get(accountId);

			boolean lIsDowngrade = isDowngrade(
					lExistingQuota.getCanonicalPlanName(),
					lNewCanonicalPlanName);

			// evaluate and update the quota allocated to the account
			// appropriately
			if (lNewCanonicalPlanName.equals(CanonicalPlanName.FREE.getValue())) {
				quotaService.updatePlan(accountId, CanonicalPlanName.FREE,
						CanonicalStorageQuota.FREE,
						CanonicalApplicationQuota.FREE);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalApplicationQuota.FREE.getValue());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalApplicationQuota.FREE.getValue());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlanName.LARGE
					.getValue())) {
				quotaService.updatePlan(accountId, CanonicalPlanName.LARGE,
						CanonicalStorageQuota.LARGE,
						CanonicalApplicationQuota.LARGE);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalApplicationQuota.LARGE.getValue());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalApplicationQuota.LARGE.getValue());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlanName.MEDIUM
					.getValue())) {
				quotaService.updatePlan(accountId, CanonicalPlanName.MEDIUM,
						CanonicalStorageQuota.MEDIUM,
						CanonicalApplicationQuota.MEDIUM);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalApplicationQuota.MEDIUM.getValue());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalApplicationQuota.MEDIUM.getValue());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlanName.MICRO
					.getValue())) {
				quotaService.updatePlan(accountId, CanonicalPlanName.MICRO,
						CanonicalStorageQuota.MICRO,
						CanonicalApplicationQuota.MICRO);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalApplicationQuota.MICRO.getValue());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalApplicationQuota.MICRO.getValue());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlanName.SMALL
					.getValue())) {
				quotaService.updatePlan(accountId, CanonicalPlanName.SMALL,
						CanonicalStorageQuota.SMALL,
						CanonicalApplicationQuota.SMALL);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalApplicationQuota.SMALL.getValue());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalApplicationQuota.SMALL.getValue());
			}

			// update the quota utilization; messaging will add the desired
			// latency required for the quota related changes to be committed to
			// the DB prior to lookup
			// TODO: Should this be removed for production environment?
			Utils.triggerUpdateQuotaUtilizationMessage(accountId, 3000);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	private void updateQuotaUtilizaton(Long accountId) {
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

	private boolean isDowngrade(String pExistingPlanName,
			String pNewCanonicalPlanName) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (getCanonicalPlanLevel(pExistingPlanName) > getCanonicalPlanLevel(pNewCanonicalPlanName)) {
				return true;
			}
			return false;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	private int getCanonicalPlanLevel(String pCanonicalPlanName) {
		if (pCanonicalPlanName.equals(CanonicalPlanName.FREE.getValue())) {
			return CanonicalPlanName.FREE.getLevel();
		} else if (pCanonicalPlanName
				.equals(CanonicalPlanName.LARGE.getValue())) {
			return CanonicalPlanName.LARGE.getLevel();
		} else if (pCanonicalPlanName.equals(CanonicalPlanName.MEDIUM
				.getValue())) {
			return CanonicalPlanName.MEDIUM.getLevel();
		} else if (pCanonicalPlanName
				.equals(CanonicalPlanName.MICRO.getValue())) {
			return CanonicalPlanName.MICRO.getLevel();
		} else if (pCanonicalPlanName
				.equals(CanonicalPlanName.SMALL.getValue())) {
			return CanonicalPlanName.SMALL.getLevel();
		}
		return -1;
	}

	private void deleteApplicationsOnPlanDowngrade(Long pAccountId, int quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// only select those applications that have not been deleted by the
			// user(FIFO order)
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(pAccountId);
			// disable beyond the quota limit
			for (int i = quota; i < lApplications.size(); i++) {
				Application lApplication = lApplications.get(i);
				applicationService
						.deleteApplicationOnPlanDowngrade(lApplication.getId());

			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Disabled "
						+ ((lApplications.size() > quota) ? (lApplications
								.size() - quota) : 0) + " applications");

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	private void restoreApplicationsOnPlanUpgrade(Long pAccountId, int quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Application> lApplications = applicationService
					.getDeletedApplicationsOnPlanDowngradeByAccountId(pAccountId);
			int lRestoredCount = 0;
			// enable upto the quota limit
			for (int i = 0; (i < quota) && (i < lApplications.size()); i++) {
				Application lApplication = lApplications.get(i);
				applicationService.restoreApplicationOnPlanUpgrade(lApplication
						.getId());
				lRestoredCount++;
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Enabled " + lRestoredCount + " applications");

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	private List<com.cm.quota.transfer.Quota> convert(List<Quota> pQuotas,
			ApplicationQuotaUsed pApplicationQuotaUsed,
			List<StorageQuotaUsed> pStorageQuotas) {
		List<com.cm.quota.transfer.Quota> lList = new ArrayList<com.cm.quota.transfer.Quota>();
		for (Quota lQuota : pQuotas) {
			lList.addAll(convert(lQuota, pApplicationQuotaUsed, pStorageQuotas));
		}

		return lList;
	}

	private List<com.cm.quota.transfer.Quota> convert(Quota pQuota,
			ApplicationQuotaUsed pApplicationQuotaUsed,
			List<StorageQuotaUsed> pStorageQuotas) {
		List<com.cm.quota.transfer.Quota> lList = new ArrayList<com.cm.quota.transfer.Quota>();

		for (StorageQuotaUsed lStorageQuotaUsed : pStorageQuotas) {
			lList.add(convert(pQuota, pApplicationQuotaUsed, lStorageQuotaUsed));
		}
		return lList;
	}

	private com.cm.quota.transfer.Quota convert(Quota pQuota,
			ApplicationQuotaUsed pApplicationQuotaUsed,
			StorageQuotaUsed pStorageQuotaUsed) {
		com.cm.quota.transfer.Quota lQuota = new com.cm.quota.transfer.Quota();
		lQuota.setCanonicalPlanName(pQuota.getCanonicalPlanName());

		lQuota.setApplicationLimit(pQuota.getApplicationLimit());
		lQuota.setApplicationsUsed(pApplicationQuotaUsed.getApplicationsUsed());
		int lPercentageApplicationUtilized = Math.round((pApplicationQuotaUsed
				.getApplicationsUsed() / pQuota.getApplicationLimit()) * 100);
		lQuota.setPercentageApplicationUsed(lPercentageApplicationUtilized);

		lQuota.setStorageLimitInBytes(pQuota.getStorageLimitInBytes());
		lQuota.setApplicationId(pStorageQuotaUsed.getApplicationId());
		lQuota.setStorageUsedInBytes(pStorageQuotaUsed.getStorageUsedInBytes());
		int lPercentageStorageUtilized = Math
				.round((pStorageQuotaUsed.getStorageUsedInBytes() / pQuota
						.getStorageLimitInBytes()) * 100);
		lQuota.setPercentageStorageUsed(lPercentageStorageUtilized);
		return lQuota;

	}

}
