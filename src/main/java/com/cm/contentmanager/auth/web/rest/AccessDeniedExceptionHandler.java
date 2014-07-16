package com.cm.contentmanager.auth.web.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class AccessDeniedExceptionHandler implements AccessDeniedHandler {
	private static final Logger LOGGER = Logger
			.getLogger(AccessDeniedExceptionHandler.class.getName());

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {
		LOGGER.log(Level.WARNING, "Entering handle");
		LOGGER.log(Level.WARNING, "Access Denied!" + e.getMessage());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		LOGGER.log(Level.WARNING, "Exiting handle");
	}

}
