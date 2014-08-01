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

package com.cm.gcm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cm.config.Configuration;
import com.cm.contentserver.ContentRequest;
import com.cm.contentserver.ContentServerService;
import com.cm.gcm.transfer.GcmRegistrationRequest;
import com.cm.util.Utils;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Controller
public class GcmController {

	private static final String API_KEY = Configuration.GOOGLE_API_KEY
			.getValue();
	// private static final int MULTICAST_SIZE = 1000;

	private static final Logger LOGGER = Logger.getLogger(GcmController.class
			.getName());
	@Autowired
	private ContentServerService contentServerService;

	@Autowired
	private GcmService gcmService;

	// @Autowired
	// private DeviceService deviceService;

	private Sender mSender;

	/**
	 * User facing action. Used by devices to register their GCM Id
	 * 
	 * @param gcmid
	 * @param userid
	 * @param deviceid
	 * @param response
	 */
	@RequestMapping(value = "/gcm/register", method = RequestMethod.POST)
	public void register(
			@RequestBody GcmRegistrationRequest gcmRegistrationRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering register");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering Registering GCM:"
						+ gcmRegistrationRequest.getGcmId());
			com.cm.gcm.GcmRegistrationRequest lDomainObject = convertToDomainObject(gcmRegistrationRequest);
			gcmService.register(lDomainObject);
			// // send the content list to the device
			//
			// // create a new content request from the registration request
			// List<com.cm.contentserver.transfer.Content> contentList = Utils
			// .convertToTransferFormat(contentServerService
			// .getContent(convertToContentRequest(lDomainObject)));
			// if (contentList != null) {
			// if (LOGGER.isLoggable(Level.INFO))
			// LOGGER.info(contentList.size() + " Content found");
			// // convert the list to a JSON Array
			// JSONArray lJsonArray = new JSONArray();
			// for (com.cm.contentserver.transfer.Content content : contentList)
			// {
			// JSONObject lJsonObject = new JSONObject();
			// lJsonObject.put("id", content.getId());
			// lJsonObject
			// .put("applicationId", content.getApplicationId());
			// lJsonObject.put("contentGroupId",
			// content.getContentGroupId());
			// lJsonObject.put("name", content.getName());
			// lJsonObject
			// .put("timeCreatedMs", content.getTimeCreatedMs());
			// lJsonObject
			// .put("timeUpdatedMs", content.getTimeUpdatedMs());
			// lJsonObject.put("type", content.getType());
			// lJsonObject.put("uri", content.getUri());
			//
			// lJsonArray.put(lJsonObject);
			// }
			// send the gcm message to the device
			// sendSingleMessage(lDomainObject.getGcmId(),
			// Configuration.MESSAGE_TYPE_CONTENT_LIST.getValue(),
			// lJsonArray.toString(), response);

			// send an SEND_TO_SYNC message to the device
			HashMap<String, String> values = new HashMap<String, String>();
			values.put(Configuration.MESSAGE_TYPE.getValue(),
					Configuration.MESSAGE_TYPE_SEND_TO_SYNC.getValue());
			sendSingleMessage(lDomainObject.getGcmId(), values, response);

			// } else {
			// if (LOGGER.isLoggable(Level.INFO))
			// LOGGER.info("No Content Found!");
			// }
			// handled by sendSingleMessage()
			// response.setStatus(HttpServletResponse.SC_OK);
			// } catch (JSONException e) {
			// LOGGER.log(Level.SEVERE, e.getMessage());
			// response.setStatus(HttpServletResponse.SC_CONFLICT);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting register");

		}
	}

	private ContentRequest convertToContentRequest(
			com.cm.gcm.GcmRegistrationRequest pGcmRegistrationRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering convertToContentRequest");
			ContentRequest lContentRequest = new ContentRequest();
			lContentRequest.setAccuracy(pGcmRegistrationRequest.getAccuracy());
			lContentRequest.setAltitude(pGcmRegistrationRequest.getAltitude());
			lContentRequest.setApplicationId(pGcmRegistrationRequest
					.getApplicationId());
			lContentRequest.setBearing(pGcmRegistrationRequest.getBearing());
			lContentRequest.setDeviceId(pGcmRegistrationRequest.getDeviceId());
			lContentRequest.setLatitude(pGcmRegistrationRequest.getLatitude());
			lContentRequest
					.setLongitude(pGcmRegistrationRequest.getLongitude());
			lContentRequest.setProvider(pGcmRegistrationRequest.getProvider());
			lContentRequest.setSpeed(pGcmRegistrationRequest.getSpeed());
			lContentRequest.setTimeCreatedMs(pGcmRegistrationRequest
					.getTimeCreatedMs());
			lContentRequest
					.setTimeCreatedTimeZoneOffsetMs(pGcmRegistrationRequest
							.getTimeCreatedTimeZoneOffsetMs());

			return lContentRequest;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting convertToContentRequest");

		}

	}

	private com.cm.gcm.GcmRegistrationRequest convertToDomainObject(
			GcmRegistrationRequest pGcmRegistrationRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering convertToDomainObject");
			com.cm.gcm.GcmRegistrationRequest lGcmRegistrationRequest = new com.cm.gcm.GcmRegistrationRequest();
			lGcmRegistrationRequest.setApplicationId(pGcmRegistrationRequest
					.getApplicationId());
			lGcmRegistrationRequest.setDeviceId(pGcmRegistrationRequest
					.getDeviceId());
			lGcmRegistrationRequest.setAccuracy(pGcmRegistrationRequest
					.getAccuracy());
			lGcmRegistrationRequest.setAltitude(pGcmRegistrationRequest
					.getAltitude());
			lGcmRegistrationRequest.setApplicationId(pGcmRegistrationRequest
					.getApplicationId());
			lGcmRegistrationRequest.setBearing(pGcmRegistrationRequest
					.getBearing());
			lGcmRegistrationRequest.setDeviceId(pGcmRegistrationRequest
					.getDeviceId());
			lGcmRegistrationRequest.setLatitude(pGcmRegistrationRequest
					.getLatitude());
			lGcmRegistrationRequest.setLongitude(pGcmRegistrationRequest
					.getLongitude());
			lGcmRegistrationRequest.setProvider(pGcmRegistrationRequest
					.getProvider());
			lGcmRegistrationRequest
					.setSpeed(pGcmRegistrationRequest.getSpeed());
			lGcmRegistrationRequest
					.setGcmId(pGcmRegistrationRequest.getGcmId());
			lGcmRegistrationRequest.setTimeCreatedMs(pGcmRegistrationRequest
					.getTimeCreatedMs());
			lGcmRegistrationRequest
					.setTimeCreatedTimeZoneOffsetMs(pGcmRegistrationRequest
							.getTimeCreatedTimeZoneOffsetMs());
			return lGcmRegistrationRequest;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting convertToDomainObject");

		}

	}

	/**
	 * User facing action. Used by devices to unregister
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/gcm/unregister/{gcmId}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE)
	public void unregister(@PathVariable String gcmId,
			@PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering unregister");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Unregistering " + gcmId);
			gcmService.unRegister(gcmId, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);

			response.setStatus(HttpServletResponse.SC_OK);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting register");
		}
	}

	@RequestMapping(value = "/gcm/sendmessage", method = RequestMethod.POST)
	public void sendMessage(@RequestParam("gcmId") String gcmId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering sendMessage");
			HashMap<String, String> values = new HashMap<String, String>();
			values.put("message", "Hello Anshu");
			sendSingleMessage(gcmId, values, response);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting sendMessage");
		}

	}

	/**
	 * Admin action
	 * 
	 * @param gcmId
	 * @param key
	 * @param value
	 * @param resp
	 */
	private void sendSingleMessage(String gcmId, Map<String, String> values,
			HttpServletResponse resp) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Sending message to device " + gcmId);

			if (mSender == null) {
				mSender = new Sender(API_KEY);
			}

			Builder builder = new Message.Builder();
			for (Iterator<String> iterator = values.keySet().iterator(); iterator
					.hasNext();) {
				String key = iterator.next();
				builder.addData(key, values.get(key));
			}
			Message message = builder.build();

			Result result;
			try {
				result = mSender.send(message, gcmId, Integer
						.valueOf(Configuration.GCM_MAX_ATTEMPTS.getValue()));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception posting " + message, e);
				taskDone(resp);
				return;
			}
			if (result == null) {
				retryTask(resp);
				return;
			}
			if (result.getMessageId() != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Succesfully sent message to device " + gcmId);
				String canonicalRegId = result.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					// same device has more than on registration id: update it
					LOGGER.finest("canonicalRegId " + canonicalRegId);
					// GcmDataStore.updateRegistration(gcmId, canonicalRegId);
				}
			} else {
				String error = result.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister it
					// GcmDataStore.unregister(gcmId);
				} else {
					LOGGER.severe("Error sending message to device " + gcmId
							+ ": " + error);
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * 
	 * @param pGcmId
	 * @param pMessageType
	 * @param pMessage
	 * @param pResponse
	 */
	private void sendSingleMessage(String pGcmId, String pMessageType,
			String pMessage, HttpServletResponse pResponse) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Sending message to device " + pGcmId);

			if (mSender == null) {
				mSender = new Sender(API_KEY);
			}

			Builder builder = new Message.Builder();
			builder.addData(pMessageType, pMessage);
			Message message = builder.build();

			Result result;
			try {
				result = mSender.send(message, pGcmId, Integer
						.valueOf(Configuration.GCM_MAX_ATTEMPTS.getValue()));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception posting " + message, e);
				taskDone(pResponse);
				return;
			}
			if (result == null) {
				retryTask(pResponse);
				return;
			}
			if (result.getMessageId() != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Succesfully sent message to device " + pGcmId);
				String canonicalRegId = result.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					// same device has more than on registration id: update it
					LOGGER.finest("canonicalRegId " + canonicalRegId);
					// GcmDataStore.updateRegistration(gcmId, canonicalRegId);
				}
			} else {
				String error = result.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister it
					// GcmDataStore.unregister(gcmId);
				} else {
					LOGGER.severe("Error sending message to device " + pGcmId
							+ ": " + error);
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * Admin action Indicates to App Engine that this task should be retried.
	 */
	private void retryTask(HttpServletResponse resp) {
		resp.setStatus(500);
	}

	/**
	 * Admin action Indicates to App Engine that this task is done.
	 */
	private void taskDone(HttpServletResponse resp) {
		resp.setStatus(200);
	}

	/**
	 * Admin action. Test method
	 * 
	 * @param id
	 * @param response
	 */
	// @Deprecated
	// @RequestMapping(value = "/admin/gcmsendhelloworld/{id}", method =
	// RequestMethod.GET)
	// public void sendHelloWorld(@PathVariable String id,
	// HttpServletResponse response) {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering sendHelloWorld");
	// String phoneId = id;
	// // Get GCM id from phone id
	// String gcmId = deviceService.getGcmIdFromPhoneId(phoneId);
	// Map<String, String> values = new HashMap<String, String>();
	// values.put(PARAMETER_ACTION, GcmActions.SEND_NOTIFICATION.getType());
	// values.put(PARAMETER_MESSAGE, "Thank you for using Mavin");
	// sendSingleMessage(gcmId, values, response);
	//
	// }

}
