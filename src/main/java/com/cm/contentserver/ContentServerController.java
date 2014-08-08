

package com.cm.contentserver;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.util.Utils;

@Controller
public class ContentServerController {
	@Autowired
	private ContentServerService contentServerService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentServerController.class.getName());

	/**
	 * @param adGroupUuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentserver/contentlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<com.cm.contentserver.transfer.Content> getContent(
			@RequestBody com.cm.contentserver.transfer.ContentRequest pContentRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			if (pContentRequest == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				LOGGER.warning("No Content Request Found!");
				return null;
			}

			List<com.cm.contentserver.transfer.Content> contentList = Utils
					.convertToTransferFormat(contentServerService
							.getContent(convertToDomainFormat(pContentRequest)));
			if (contentList != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(contentList.size() + " Content found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return contentList;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	private ContentRequest convertToDomainFormat(
			com.cm.contentserver.transfer.ContentRequest pContentRequest) {
		ContentRequest lContentRequest = new ContentRequest();
		lContentRequest.setAccuracy(pContentRequest.getAccuracy());
		lContentRequest.setAltitude(pContentRequest.getAltitude());
		lContentRequest.setTrackingId(pContentRequest.getTrackingId());
		lContentRequest.setBearing(pContentRequest.getBearing());
		lContentRequest.setDeviceId(pContentRequest.getDeviceId());
		lContentRequest.setLatitude(pContentRequest.getLatitude());
		lContentRequest.setLongitude(pContentRequest.getLongitude());
		lContentRequest.setProvider(pContentRequest.getProvider());
		lContentRequest.setSpeed(pContentRequest.getSpeed());
		lContentRequest.setTimeCreatedMs(pContentRequest.getTimeCreatedMs());
		lContentRequest.setTimeCreatedTimeZoneOffsetMs(pContentRequest
				.getTimeCreatedTimeZoneOffsetMs());

		return lContentRequest;
	}

}
