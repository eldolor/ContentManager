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

package com.cm.demo;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

	private static final Logger LOGGER = Logger.getLogger(DemoController.class
			.getName());

	private Cache mCache;

	public DemoController() {
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
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public ModelAndView displayApplications(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayApplications");
		try {
			return new ModelAndView("skokdemo/index", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayApplications");
		}
	}

	// @RequestMapping(value = "/applications/{tour}", method =
	// RequestMethod.GET)
	// public ModelAndView displayApplications(ModelMap model,
	// @PathVariable String tour) {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering displayApplications");
	// try {
	// model.addAttribute("tour", tour);
	// return new ModelAndView("applications", model);
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting displayApplications");
	// }
	// }

}
