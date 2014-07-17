package com.cm.contentmanager.web.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cm.usermanagement.user.entity.User;
import com.cm.usermanagement.user.service.UserService;

@Controller
public class MainController {
	private static final Logger LOGGER = Logger.getLogger(MainController.class
			.getName());
	@Autowired
	private UserService userService;

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView doMain(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doMain");
		try {
			User user = userService.getLoggedInUser();
			if (user == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("User is not logged in ");
				return new ModelAndView("index", model);
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("User is logged in ");
				return new ModelAndView("content_groups", model);
			}
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

}
