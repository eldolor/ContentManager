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

package com.cm.contentmanager.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
import com.cm.contentmanager.search.transfer.Searchable;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;

@Controller
public class SearchController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger
			.getLogger(SearchController.class.getName());

	public SearchController() {
		super();
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/{searchTerm}", method = RequestMethod.GET)
	public ModelAndView displaySearchResults(@PathVariable String searchTerm,
			ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			model.addAttribute("searchTerm", searchTerm);
			return new ModelAndView("search", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/secured/search/{searchTerm}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, List<Searchable>> getSearchResults(
			@PathVariable String searchTerm, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("Entering getSearchResults");
				LOGGER.info("Search Term: " + searchTerm);
			}
			User user = userService.getLoggedInUser();
			Map<String, List<Searchable>> lSearchResults = new HashMap<String, List<Searchable>>();
			List<Application> lApplications = new ArrayList<Application>();
			List<ContentGroup> lContentGroups = new ArrayList<ContentGroup>();
			List<Content> lContents = new ArrayList<Content>();

			if (user.getRole().equals(User.ROLE_SUPER_ADMIN)) {
				lApplications = applicationService.search(searchTerm);
				lContentGroups = contentGroupService.search(searchTerm);
				lContents = contentService.search(searchTerm);
			} else if (user.getRole().equals(User.ROLE_ADMIN)) {
				lApplications = applicationService.searchByAccountId(
						user.getAccountId(), searchTerm);
				lContentGroups = contentGroupService.searchByAccountId(
						user.getAccountId(), searchTerm);
				lContents = contentService.searchByAccountId(
						user.getAccountId(), searchTerm);
			} else if (user.getRole().equals(User.ROLE_USER)) {
				lApplications = applicationService.searchByUserId(user.getId(),
						searchTerm);
				lContentGroups = contentGroupService.searchByUserId(
						user.getId(), searchTerm);
				lContents = contentService.searchByUserId(user.getId(),
						searchTerm);
			}

			{
				List<Searchable> lSearchables = new ArrayList<Searchable>();
				for (Application application : lApplications)
					lSearchables.add(application);
				lSearchResults.put(
						CanonicalSearchableType.APPLICATION.getType(),
						lSearchables);
			}
			{
				List<Searchable> lSearchables = new ArrayList<Searchable>();
				for (ContentGroup contentGroup : lContentGroups)
					lSearchables.add(contentGroup);
				lSearchResults.put(
						CanonicalSearchableType.CONTENT_GROUP.getType(),
						lSearchables);
			}
			{
				List<Searchable> lSearchables = new ArrayList<Searchable>();
				for (Content content : lContents)
					lSearchables.add(content);
				lSearchResults.put(CanonicalSearchableType.CONTENT.getType(),
						lSearchables);
			}

			response.setStatus(HttpServletResponse.SC_OK);
			return lSearchResults;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getSearchResults");
		}
	}

}
