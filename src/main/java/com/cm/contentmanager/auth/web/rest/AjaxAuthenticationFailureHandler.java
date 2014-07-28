package com.cm.contentmanager.auth.web.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("ajaxAuthenticationFailureHandler")
public class AjaxAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	private static final Logger LOGGER = Logger
			.getLogger(AjaxAuthenticationFailureHandler.class.getName());

	public AjaxAuthenticationFailureHandler() {
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering onAuthenticationFailure");
		try {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			response.getWriter().print("{result:\"FAILURE\"}");// return
//			response.getWriter().flush();

//		} catch (IOException e) {
//			// handle exception...
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting onAuthenticationFailure");
		}
	}

}
