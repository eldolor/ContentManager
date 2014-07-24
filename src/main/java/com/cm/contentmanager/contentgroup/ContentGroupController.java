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

package com.cm.contentmanager.contentgroup;

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

import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.ValidationError;

@Controller
public class ContentGroupController {
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentGroupController.class.getName());

	/**
	 * Entry point
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{applicationId}/contentgroups", method = RequestMethod.GET)
	public ModelAndView displayContentGroups(@PathVariable Long applicationId,
			ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayContentGroups");
		try {
			return new ModelAndView("content_groups", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayContentGroups");
		}
	}

	/**
	 * 
	 * @param campaignUuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/{applicationId}/contentgroups", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ContentGroup> getContentGroups(@PathVariable Long applicationId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroups");
			User user = userService.getLoggedInUser();
			List<ContentGroup> contentGroups = null;

			if (user.getRole().equals(User.ROLE_SUPER_ADMIN))
				contentGroups = contentGroupService
						.getContentGroupsByApplicationId(applicationId);
			else if (user.getRole().equals(User.ROLE_ADMIN))
				contentGroups = contentGroupService
						.getContentGroupsByAccountId(user.getAccountId());
			else if (user.getRole().equals(User.ROLE_USER))
				contentGroups = contentGroupService
						.getContentGroupsByUserId(user.getId());

			if (contentGroups != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(contentGroups.size() + " Content Groups found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Groups Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return contentGroups;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroups");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/contentgroup/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ContentGroup getContentGroup(@PathVariable Long id,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroup");
			response.setStatus(HttpServletResponse.SC_OK);
			return contentGroupService.getContentGroup(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroup");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 */
	@RequestMapping(value = "/secured/contentgroup/{id}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE)
	public void deleteContentGroup(@PathVariable Long id,
			@PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContentGroup");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Content Group ID: " + id);
			response.setStatus(HttpServletResponse.SC_OK);
			contentGroupService.deleteContentGroup(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContentGroup");
		}
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/secured/contentgroup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doCreateContentGroup(
			@RequestBody ContentGroup contentGroup, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateContentGroup");

			List<ValidationError> errors = validate(contentGroup);

			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				contentGroupService.saveContentGroup(
						userService.getLoggedInUser(), contentGroup);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentGroup");
		}
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/secured/contentgroup", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateContentGroup(
			@RequestBody ContentGroup contentGroup, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateContentGroup");
			List<ValidationError> errors = validate(contentGroup);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				contentGroupService.updateContentGroup(contentGroup);
				response.setStatus(HttpServletResponse.SC_OK);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateContentGroup");
		}
	}

	private List<ValidationError> validate(ContentGroup contentGroup) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (contentGroup.getName().length() == 0) {
			ValidationError error = new ValidationError();
			error.setCode("name");
			error.setDescription("Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Name cannot be blank");
		}
		if (((contentGroup.getStartDateIso8601() != null) && (contentGroup
				.getStartDateIso8601().length() == 0))
				|| (contentGroup.getStartDateIso8601() == null)) {
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

}
