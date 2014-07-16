package com.cm.contentmanager.web.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	private static final Logger LOGGER = Logger.getLogger(MainController.class
			.getName());

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView doMain(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doMain");
		try {
			return new ModelAndView("index", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doMain");
		}
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/error")
	public ModelAndView doError(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doError");
		try {
			return new ModelAndView("error", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doError");
		}
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public ModelAndView doContent(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doContent");
		try {
			return new ModelAndView("content", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doContent");
		}
	}

}
