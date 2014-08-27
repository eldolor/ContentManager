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

package com.cm.contentmanager.application;

import java.util.ArrayList;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.contentmanager.content.ContentHelper;
import com.cm.quota.QuotaService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;
import com.cm.util.ValidationError;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Controller
public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContentHelper contentHelper;
	@Autowired
	private QuotaService quotaService;

	private static final Logger LOGGER = Logger
			.getLogger(ApplicationController.class.getName());

	private Cache mCache;

	public ApplicationController() {
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
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	public ModelAndView displayApplications(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayApplications");
		try {
			return new ModelAndView("applications", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayApplications");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/application/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Application getApplication(@PathVariable Long id,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplication");
			response.setStatus(HttpServletResponse.SC_OK);
			return applicationService.getApplication(id);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplication");
		}
	}

	/**
	 * 
	 * @param campaignUuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/applications", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Application> getApplications(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplications");
			User user = userService.getLoggedInUser();
			List<Application> applications = null;

			if (user.getRole().equals(User.ROLE_SUPER_ADMIN))
				applications = applicationService.getAllApplications();
			else if (user.getRole().equals(User.ROLE_ADMIN))
				applications = applicationService
						.getApplicationsByAccountId(user.getAccountId());
			else if (user.getRole().equals(User.ROLE_USER))
				applications = applicationService.getApplicationsByUserId(user
						.getId());

			if (applications != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(applications.size() + " Content Groups found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Groups Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return applications;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplications");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 */
	@RequestMapping(value = "/secured/application/{id}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE)
	public void deleteApplication(@PathVariable Long id,
			@PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteApplication");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Content Group ID: " + id);
			response.setStatus(HttpServletResponse.SC_OK);
			applicationService.deleteApplication(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
			String lTrackingId = applicationService.getApplication(id)
					.getTrackingId();
			Utils.triggerChangesStagedMessage(id);
			Utils.triggerUpdateLastKnownTimestampMessage(lTrackingId);

			// trigger message to update quota
			Utils.triggerUpdateQuotaMessage(userService.getLoggedInUser()
					.getAccountId());

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteApplication");
		}
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/secured/application", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doCreateApplication(
			@RequestBody Application application, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateApplication");

			List<ValidationError> errors = validate(application);

			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				// validate if there is a sufficient quota
				if (!quotaService.hasSufficientApplicationQuota(userService
						.getLoggedInUser().getAccountId())) {
					ValidationError error = new ValidationError();
					error.setCode(QuotaService.APPLICATION_QUOTA_REACHED_ERROR_CODE);
					error.setDescription("Application quota reached");
					errors.add(error);
					LOGGER.log(Level.WARNING, "Application quota reached");
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					return errors;
				}

				String lTrackingId = createTrackingId(userService
						.getLoggedInUser());
				applicationService
						.saveApplication(userService.getLoggedInUser(),
								lTrackingId, application);
				Utils.triggerUpdateLastKnownTimestampMessage(lTrackingId);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateApplication");
		}
	}

	private String createTrackingId(User pUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createTrackingId");
			Long lAccountId = pUser.getAccountId();

			// include all deleted applications, to ensure that the tracking id
			// of a deleted application is not reassigned.
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(lAccountId, true);

			String lTrackingId = "AI_" + lAccountId + "_"
					+ (lApplications.size() + 1); // the collection will have
													// size 0 at first

			return lTrackingId;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createTrackingId");
		}
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/secured/application", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateApplication(
			@RequestBody Application application, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateApplication");
			List<ValidationError> errors = validate(application);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				applicationService.updateApplication(application);
				String lTrackingId = applicationService.getApplication(
						application.getId()).getTrackingId();
				Utils.triggerChangesStagedMessage(application.getId());
				Utils.triggerUpdateLastKnownTimestampMessage(lTrackingId);

				response.setStatus(HttpServletResponse.SC_OK);

				return null;
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateApplication");
		}
	}

	@RequestMapping(value = "/tasks/application/changesstaged/{applicationId}", method = RequestMethod.POST)
	public void markChangesStaged(@PathVariable Long applicationId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering markChangesStaged");

			applicationService.updateChangesStaged(applicationId, true);
			String lTrackingId = applicationService.getApplication(
					applicationId).getTrackingId();

			// update memcache
			try {
				if (mCache != null) {
					mCache.put(lTrackingId + "changes_staged", true);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("updating memcache");
				}
			} catch (Throwable t) {
				LOGGER.log(Level.SEVERE, "Unable to update Memcache", t);
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting markChangesStaged");

		}
	}

	@RequestMapping(value = "/secured/pushchanges/{applicationId}", method = RequestMethod.POST)
	public void pushChanges(@PathVariable Long applicationId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering pushChanges");
			Application lApplication = applicationService
					.getApplication(applicationId);
			if (lApplication == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				LOGGER.warning("No application found for id " + applicationId);
				return;
			}
			applicationService.updateChangesStaged(lApplication.getId(), false);
			Utils.triggerSendContentListMessages(lApplication.getTrackingId());
			// remove from memcache
			try {
				if (mCache != null) {
					mCache.remove(lApplication.getTrackingId()
							+ "changes_staged");
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("removed from memcache");
				}
			} catch (Throwable t) {
				LOGGER.log(Level.SEVERE, "Unable to remove from Memcache", t);
			}

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting pushChanges");

		}
	}

	private List<ValidationError> validate(Application application) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (application.getName().length() == 0) {
			ValidationError error = new ValidationError();
			error.setCode("name");
			error.setDescription("Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Name cannot be blank");
		}

		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

}
