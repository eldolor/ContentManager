package com.cm.contentmanager.auth.web.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("ajaxAuthenticationSuccessHandler")
public class AjaxAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {
	private static final Logger LOGGER = Logger
			.getLogger(AjaxAuthenticationSuccessHandler.class.getName());

	public AjaxAuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering onAuthenticationSuccess");
		try {
			response.setStatus(HttpServletResponse.SC_OK);
//			response.getWriter().print("{result:\"SUCCESS\"}");
//			response.getWriter().flush();

//		} catch (IOException e) {
//			// handle exception...
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting onAuthenticationSuccess");
		}

		// HttpSession session = request.getSession();
		// DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest)
		// session
		// .getAttribute(WebAttributes.SAVED_REQUEST);
		// check if login is originated from ajax call
	}
}
