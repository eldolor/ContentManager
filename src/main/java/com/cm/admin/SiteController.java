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

package com.cm.admin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteController {

	private static final Logger LOGGER = Logger.getLogger(SiteController.class
			.getName());

	@RequestMapping(value = "/site/terms", method = RequestMethod.GET)
	public ModelAndView displayTerms(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayTerms");
		try {
			return new ModelAndView("terms_of_service", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayTerms");
		}
	}

	@RequestMapping(value = "/site/privacy", method = RequestMethod.GET)
	public ModelAndView displayPrivacy(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayPrivacy");
		try {
			return new ModelAndView("privacy", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayPrivacy");
		}
	}

	@RequestMapping(value = "/site/contact", method = RequestMethod.GET)
	public ModelAndView displayContact(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayContact");
		try {
			return new ModelAndView("contact", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayContact");
		}
	}

}
