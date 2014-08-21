package com.cm.contentserver;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
import com.cm.usermanagement.user.User;
import com.cm.util.Utils;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Controller
public class ContentServerController {
	@Autowired
	private ContentServerService contentServerService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private ContentService contentService;

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
			ContentRequest lContentRequest = convertToDomainFormat(pContentRequest);
			List<com.cm.contentserver.transfer.Content> lContentList = Utils
					.convertToTransferFormat(contentServerService
							.getContent(lContentRequest));
			if (lContentList != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(lContentList.size() + " Content found");
				// add an additional attribute to indicate wifi only download
				// status, which is being managed at the content level here,
				// instead of application level in the application
				if (contentServerService.isUpdateOverWifiOnly(lContentRequest)) {
					for (com.cm.contentserver.transfer.Content lContent : lContentList) {
						lContent.setUpdateOverWifiOnly(true);
					}
				}
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return lContentList;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	/**
	 * Unsecured URI
	 * 
	 * @param trackingId
	 * @param gcmRegistrationId
	 * @param lastKnownTimestamp
	 * @param response
	 */
	@RequestMapping(value = "/handshake/{trackingId}/{gcmRegistrationId}/{lastKnownTimestamp}", method = RequestMethod.GET)
	public void doHandshake(@PathVariable String trackingId,
			@PathVariable String gcmRegistrationId,
			@PathVariable Long lastKnownTimestamp, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doHandshake");
			Application lApplication = applicationService
					.getApplicationByTrackingId(trackingId);
			List<ContentGroup> lContentGroups = contentGroupService
					.getContentGroupsByApplicationId(lApplication.getId());
			List<Content> lContents = contentService.getAllContent(lApplication
					.getId());

			boolean lSendMessage = false;

			// eval timestamp
			if (lApplication.getTimeUpdatedMs() > lastKnownTimestamp) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("doHandshake:: Application was updated after "
							+ lastKnownTimestamp);
				lSendMessage = true;
			}
			// eval content groups
			if (!lSendMessage) {
				for (Iterator<ContentGroup> iterator = lContentGroups
						.iterator(); iterator.hasNext();) {
					ContentGroup lContentGroup = iterator.next();
					if (lContentGroup.getTimeUpdatedMs() > lastKnownTimestamp) {
						if (LOGGER.isLoggable(Level.INFO))
							LOGGER.info("doHandshake:: Content Group was updated after "
									+ lastKnownTimestamp);
						lSendMessage = true;
						break;
					}

				}
			}
			if (!lSendMessage) {
				for (Iterator iterator = lContents.iterator(); iterator
						.hasNext();) {
					Content lContent = (Content) iterator.next();
					if (lContent.getTimeUpdatedMs() > lastKnownTimestamp) {
						if (LOGGER.isLoggable(Level.INFO))
							LOGGER.info("doHandshake:: Content was updated after "
									+ lastKnownTimestamp);
						lSendMessage = true;
						break;
					}

				}
			}
			// send the GCM message to the device
			if (lSendMessage) {
				Queue queue = QueueFactory.getQueue("gcmqueue");
				TaskOptions taskOptions = TaskOptions.Builder
						.withUrl(
								"/tasks/gcm/sendcontentlistmessages/"
										+ trackingId)
						.param("trackingId", trackingId).method(Method.POST);
				queue.add(taskOptions);
			}

			// always
			response.setStatus(HttpServletResponse.SC_OK);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doHandshake");
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
