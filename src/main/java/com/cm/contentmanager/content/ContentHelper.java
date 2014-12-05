package com.cm.contentmanager.content;

import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cm.contentserver.ContentRequest;
import com.cm.contentserver.ContentServerService;
import com.cm.util.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Component
public class ContentHelper {
	@Autowired
	public ContentServerService contentServerService;
	private static final Logger LOGGER = Logger.getLogger(ContentHelper.class
			.getName());

	/**
	 * 
	 * @param pContentRequest
	 * @return
	 */
	public String getContent(ContentRequest pContentRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// create a new content request from the registration request
			List<com.cm.contentserver.transfer.Content> lContentList = Utils
					.convertToTransferFormat(contentServerService
							.getContent(pContentRequest));
			// add an additional attribute to indicate wifi only download
			// status, which is being managed at the content level here,
			// instead of application level in the application
			if (contentServerService.isUpdateOverWifiOnly(pContentRequest)) {
				for (com.cm.contentserver.transfer.Content lContent : lContentList) {
					lContent.setUpdateOverWifiOnly(true);
				}
			}
			if (contentServerService.isCollectUsageData(pContentRequest)) {
				for (com.cm.contentserver.transfer.Content lContent : lContentList) {
					lContent.setCollectUsageData(true);
				}
			}
			// convert the list to a JSON Array
			JSONArray lJsonArray = new JSONArray();
			for (com.cm.contentserver.transfer.Content content : lContentList) {
				JSONObject lJsonObject = new JSONObject();
				try {
					lJsonObject.put("id", content.getId());
					lJsonObject
							.put("applicationId", content.getApplicationId());
					lJsonObject.put("contentGroupId",
							content.getContentGroupId());
					lJsonObject.put("name", content.getName());
					lJsonObject
							.put("timeCreatedMs", content.getTimeCreatedMs());
					lJsonObject
							.put("timeUpdatedMs", content.getTimeUpdatedMs());
					lJsonObject.put("type", content.getType());
					lJsonObject.put("uri", content.getUri());
					lJsonObject.put("updateOverWifiOnly",
							content.isUpdateOverWifiOnly());
					lJsonObject.put("sizeInBytes",
							content.getSizeInBytes());
					lJsonObject.put("tags", content.getTags());
					
					lJsonArray.put(lJsonObject);
				} catch (JSONException e) {
					LOGGER.log(Level.WARNING,
							"Error converting Contents to JSON representation");
				}
			}
			return lJsonArray.toString();
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * 
	 * @param pTrackingId
	 * @return
	 */
	public ContentRequest getGenericContentRequest(String pTrackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering convertToContentRequest");
			ContentRequest lContentRequest = new ContentRequest();
			lContentRequest.setTrackingId(pTrackingId);
			lContentRequest.setTimeCreatedMs(System.currentTimeMillis());
			lContentRequest.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
					.getDefault().getRawOffset());

			return lContentRequest;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting convertToContentRequest");

		}

	}
}
