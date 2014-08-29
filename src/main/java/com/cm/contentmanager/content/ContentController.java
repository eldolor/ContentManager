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

package com.cm.contentmanager.content;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.contentmanager.application.ApplicationService;
import com.cm.quota.QuotaService;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;
import com.cm.util.ValidationError;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContentHelper contentHelper;
	@Autowired
	private ApplicationService applicationService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentController.class.getName());
	private final BlobInfoFactory mBlobInfoFactory = new BlobInfoFactory();

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{applicationId}/{contentGroupId}/content", method = RequestMethod.GET)
	public ModelAndView displayContent(@PathVariable Long applicationId,
			@PathVariable Long contentGroupId, ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayContent");
		try {
			// pass it along to the view
			// model.addAttribute("contentGroupId", contentGroupId);
			return new ModelAndView("content", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayContent");
		}
	}

	/**
	 * 
	 * @param contentGroupUuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/{applicationId}/{contentGroupId}/content", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Content> getAllContent(@PathVariable Long applicationId,
			@PathVariable Long contentGroupId, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			if (contentGroupId == null || contentGroupId.equals("")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Group Id Found!");
				return null;
			}
			List<Content> content = contentService.get(applicationId,
					contentGroupId, false);
			if (content != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(content.size() + " Content found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return content;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/content/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Content getContent(@PathVariable Long id, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Content ID: " + id);
			if (id == null || id.equals("")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Id Found!");
				return null;
			}
			Content content = contentService.get(id);
			if (content == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return content;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	@RequestMapping(value = "/secured/content", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doCreateContent(@RequestBody Content content,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateContentGroup");

			List<ValidationError> errors = validate(content);

			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				

				Content lContent = contentService.save(
						userService.getLoggedInUser(), content);
				response.setStatus(HttpServletResponse.SC_CREATED);
				String lTrackingId = applicationService.getApplication(
						content.getApplicationId()).getTrackingId();
				Utils.triggerChangesStagedMessage(content.getApplicationId(), 0);
				Utils.triggerUpdateLastKnownTimestampMessage(lTrackingId, 0);
				if (!Utils.isEmpty(content.getId())
						&& (!Utils.isEmpty(content.getUri())))
					Utils.triggerUpdateContentSizeInBytesMessage(
							lContent.getId(), lContent.getUri(), 0);
				// trigger message to update quota
				Utils.triggerUpdateQuotaUtilizationMessage(userService.getLoggedInUser()
						.getAccountId(), 0);
				return null;
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentGroup");
		}
	}

	@RequestMapping(value = "/secured/content", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateContent(@RequestBody Content content,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateContentGroup");
			List<ValidationError> errors = validate(content);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				contentService.update(content);
				response.setStatus(HttpServletResponse.SC_OK);

				String lTrackingId = applicationService.getApplication(
						content.getApplicationId()).getTrackingId();
				Utils.triggerChangesStagedMessage(content.getApplicationId(), 0);
				Utils.triggerUpdateLastKnownTimestampMessage(lTrackingId, 0);
				if (!Utils.isEmpty(content.getId())
						&& (!Utils.isEmpty(content.getUri())))
					Utils.triggerUpdateContentSizeInBytesMessage(
							content.getId(), content.getUri(), 0);
				// trigger message to update quota
				//TODO: added artificial delay. Should this be removed in prod?
				Utils.triggerUpdateQuotaUtilizationMessage(userService.getLoggedInUser()
						.getAccountId(), 3000);
				return null;
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateContentGroup");
		}
	}

	@RequestMapping(value = "/secured/content/{id}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteContent(@PathVariable Long id,
			@PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContent");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Content ID: " + id);
			if (id == null || id.equals("")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Id Found!");
			}
			// Get the application id for the content that is about to be
			// deleted
			Long lApplicationId = contentService.get(id).getApplicationId();
			contentService.delete(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
			response.setStatus(HttpServletResponse.SC_OK);
			String lTrackingId = applicationService.getApplication(
					lApplicationId).getTrackingId();
			Utils.triggerChangesStagedMessage(id, 0);
			Utils.triggerUpdateLastKnownTimestampMessage(lTrackingId, 0);
			// trigger message to update quota
			Utils.triggerUpdateQuotaUtilizationMessage(userService.getLoggedInUser()
					.getAccountId(), 0);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContent");
		}
	}

	@RequestMapping(value = "/secured/content/enabled", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateEnabled(
			@RequestBody com.cm.contentmanager.content.Content content,
			HttpServletResponse response) {
		// TODO: Figure out why Jackson Mapper couldn't map the request to the
		// Content transfer object
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateCampaign");
			if (content.getId() == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				List<ValidationError> errors = new ArrayList<ValidationError>();
				ValidationError error = new ValidationError();
				error.setCode("id");
				error.setDescription("Missing content id");
				errors.add(error);
				LOGGER.log(Level.WARNING, "Missing content id");
				return errors;
			} else {
				contentService.updateEnabled(content);
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
				LOGGER.info("Exiting doUpdateCampaign");
		}
	}

	private List<ValidationError> validate(Content content) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (content.getName().length() == 0) {
			ValidationError error = new ValidationError();
			error.setCode("name");
			error.setDescription("Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Name cannot be blank");
		}
		if (((content.getStartDateIso8601() != null) && (content
				.getStartDateIso8601().length() == 0))
				|| (content.getStartDateIso8601() == null)) {
			ValidationError error = new ValidationError();
			error.setCode("start date");
			error.setDescription("Start Date cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Start Date cannot be blank");
		}

		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	@RequestMapping(value = "/tasks/content/updatesize/{id}/{uri}", method = RequestMethod.POST)
	public void updateSize(@PathVariable Long id, @PathVariable String uri,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateSize");
			if (Utils.isEmpty(uri)) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("URI is null. Skipping...");
				return;
			}

			BlobKey blobKey = new BlobKey(uri);
			final BlobInfo blobInfo = mBlobInfoFactory.loadBlobInfo(blobKey);
			if (blobInfo != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Content size is " + blobInfo.getSize());
				contentService.updateContentSize(id, blobInfo.getSize());
			} else {
				LOGGER.warning("No size found for uri " + uri);
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateSize");

		}
	}
}
