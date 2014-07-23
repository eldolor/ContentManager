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

package com.cm.contentmanager.auth.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cm.usermanagement.user.UserService;
import com.cm.util.ValidationError;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger.getLogger(LoginController.class
			.getName());

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView doLogin(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doLogin");
		try {
			return new ModelAndView("login", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doLogin");
		}
	}

	/**
	 * @param uuid
	 * @param response
	 */
	@RequestMapping(value = "/secured/isroleadmin/{id}", method = RequestMethod.GET)
	public void isRoleAdmin(@PathVariable Long id, HttpServletResponse response) {
		com.cm.usermanagement.user.User user = userService.getUser(id);
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		for (Iterator iterator = authorities.iterator(); iterator.hasNext();) {
			GrantedAuthority grantedAuthority = (GrantedAuthority) iterator
					.next();
			if (grantedAuthority.getAuthority().equals(
					com.cm.usermanagement.user.User.ROLE_ADMIN)) {
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * @param uuid
	 * @param response
	 */
	@RequestMapping(value = "/secured/isrolesuperadmin/{id}", method = RequestMethod.GET)
	public void isRoleSuperAdmin(@PathVariable Long id,
			HttpServletResponse response) {
		com.cm.usermanagement.user.User user = userService.getUser(id);
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		for (Iterator iterator = authorities.iterator(); iterator.hasNext();) {
			GrantedAuthority grantedAuthority = (GrantedAuthority) iterator
					.next();
			if (grantedAuthority.getAuthority().equals(
					com.cm.usermanagement.user.User.ROLE_SUPER_ADMIN)) {
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * If we have a login failure this request mapping flags the error to be
	 * shown in the UI.
	 * 
	 * @param model
	 *            the spring model for the request
	 */
	@RequestMapping(value = "/loginfailure", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doLoginFailure(ModelMap map) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doLoginFailure");
			// doHome(map);
			map.addAttribute("popupScreen", "login_div");
			return new ModelAndView("login", map);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doLoginFailure");
		}
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public ModelAndView doLogout(ModelMap model) throws IOException {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doLogout");
			return new ModelAndView("login", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doLogout");
		}
	}

	private List<ValidationError> validate(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		if (userAccount.getUserName().equals("")) {
			ValidationError error = new ValidationError();
			error.setCode("username");
			error.setDescription("Username cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Username cannot be blank");
		}
		if (userAccount.getPassword().equals("")) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Password cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Password cannot be blank");
		}
		if (!userAccount.getPassword().equals(userAccount.getPassword2())) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Passwords do not match");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Passwords do not match");
		}
		// check to make sure we don't have a user account already
		com.cm.usermanagement.user.User user = userService
				.getUserByUserName(userAccount.getUserName());
		if (user != null) {
			ValidationError error = new ValidationError();
			error.setCode("username");
			error.setDescription("The user already exists");
			errors.add(error);
			LOGGER.log(Level.WARNING, "The user already exists");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

}
