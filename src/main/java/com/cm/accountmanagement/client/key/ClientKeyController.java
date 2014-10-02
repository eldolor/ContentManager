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

package com.cm.accountmanagement.client.key;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;

@Controller
public class ClientKeyController {
	@Autowired
	private ClientKeyService clientKeyService;
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger
			.getLogger(ClientKeyController.class.getName());


	public ClientKeyController() {
		super();
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/clientkeys", method = RequestMethod.GET)
	public ModelAndView displayApplications(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			return new ModelAndView("settings_client_keys", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/secured/clientkeys", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ClientKey> getClientKeys(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			User user = userService.getLoggedInUser();
			List<ClientKey> lClientKeys = null;

			lClientKeys = clientKeyService.getClientKeys(user.getAccountId());

			if (lClientKeys != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(lClientKeys.size() + " keys found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No client keys Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return lClientKeys;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting ");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 */
	@RequestMapping(value = "/secured/clientkey/{id}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id, @PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering ");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("client key id: " + id);
			clientKeyService.deleteClientKey(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
			response.setStatus(HttpServletResponse.SC_OK);

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
	@RequestMapping(value = "/secured/clientkey", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void generateClientKey(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			User user = userService.getLoggedInUser();
			clientKeyService.generateClientKey(user.getAccountId());
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
