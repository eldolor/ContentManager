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

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Controller
public class GcmController {

	private static final String API_KEY = "AIzaSyCs_vD2_4a0WENmktfD2mKu0alj8dBk-Uo";
	// private static final int MULTICAST_SIZE = 1000;

	private static final Logger LOGGER = Logger.getLogger(GcmController.class
			.getName());
	private static final String HEADER_QUEUE_COUNT = "X-AppEngine-TaskRetryCount";
	private static final String HEADER_QUEUE_NAME = "X-AppEngine-QueueName";
	private static final int MAX_RETRY = 3;

	private static final String PARAMETER_DEVICE = "device";
	private static final String PARAMETER_ACTION = "action";
	private static final String PARAMETER_MESSAGE = "message";
	private static final String PARAMETER_MULTICAST = "multicastKey";

	@Autowired
	private GcmService gcmService;

	// @Autowired
	// private DeviceService deviceService;

	private Sender mSender;

	// /**
	// * User facing action. Used by devices to upload the campaign file
	// *
	// * @param ads
	// * @param response
	// */
	// @RequestMapping(value = "/gcm/campaignfile", method = RequestMethod.POST)
	// public void campaignFile(@RequestBody List<Ad> ads,
	// HttpServletResponse response) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering");
	// // TODO: save the list to repository
	//
	// // TODO: delete later
	// // for (Ad ad : ads) {
	// // if (LOGGER.isLoggable(Level.INFO))
	// // LOGGER.info("Id: " + ad.getId());
	// // }
	// response.setStatus(HttpServletResponse.SC_ACCEPTED);
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting");
	// }
	// }
	//
	// /**
	// * User facing action. Used by devices to upload the registration file
	// *
	// * @param registeredDevice
	// * @param response
	// */
	// @RequestMapping(value = "/gcm/deviceregistrationfile", method =
	// RequestMethod.POST)
	// public void deviceRegistrationFile(
	// @RequestBody RegisteredDevice registeredDevice,
	// HttpServletResponse response) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering");
	// // TODO: save the list to repository
	//
	// // TODO: delete later
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("deviceId: " + registeredDevice.getDeviceId());
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("accountName: " + registeredDevice.getAccountName());
	//
	// response.setStatus(HttpServletResponse.SC_ACCEPTED);
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting");
	// }
	// }
	//
	// /**
	// * User facing action. Used by devices to upload the mavin directory
	// listing
	// *
	// * @param fileNames
	// * @param response
	// */
	// @RequestMapping(value = "/gcm/rootdirectorylisting", method =
	// RequestMethod.POST)
	// public void rootDirectoryListing(@RequestBody List<String> fileNames,
	// HttpServletResponse response) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering");
	// // TODO: save the list to repository
	//
	// // TODO: delete later
	// for (String fileName : fileNames) {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info(fileName);
	// }
	//
	// response.setStatus(HttpServletResponse.SC_ACCEPTED);
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting");
	// }
	// }

	/**
	 * User facing action. Used by devices to register their GCM Id
	 * 
	 * @param gcmid
	 * @param userid
	 * @param deviceid
	 * @param response
	 */
	@Deprecated
	@RequestMapping(value = "/gcm/register", method = RequestMethod.POST)
	public void register(@RequestParam("gcmid") String gcmid,
			@RequestParam("userid") String userid,
			@RequestParam("deviceid") String deviceid,
			HttpServletResponse response) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering Registering GCM:" + gcmid + " USER:" + userid
					+ " DEVICE:" + deviceid);
		GcmRegistrationRequest gcmregreq = new GcmRegistrationRequest();
		gcmregreq.setGcmId(gcmid);
		gcmregreq.setUserId(userid);
		gcmregreq.setDeviceId(deviceid);
		if (gcmService.register(gcmregreq)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			// I picked 205 because:
			// (a) it's not 200, therefore will cause the client to register
			// this as an "error"
			// (b) it's not 4xx or 5xx, otherwise GAE will think that this call
			// error-ed
			// It's not ideal. Ideally, this call should return a boolean. But
			// that would require making more changes to the client.
			// For the future.
			response.setStatus(HttpServletResponse.SC_RESET_CONTENT);
		}
	}

	/**
	 * User facing action. Used by devices to unregister
	 * 
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/gcm/unregister", method = RequestMethod.POST)
	public static void unregister(@RequestParam("id") String id,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering unregister");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Unregistering " + id);
			GcmDataStore.unregister(id);
			response.setStatus(HttpServletResponse.SC_OK);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting register");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting unregister");
	}

	/**
	 * Admin action
	 * 
	 * @param response
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/admin/gcm/devices", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<String> getDevices(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getDevices");
			response.setStatus(HttpServletResponse.SC_OK);
			return GcmDataStore.getDevices();
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getDevices");
		}
	}

	/**
	 * Admin action
	 * 
	 * @param response
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/admin/gcm/totaldevices", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String getTotalDevices(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getTotalDevices");
			int totalDevices = GcmDataStore.getTotalDevices();
			response.setStatus(HttpServletResponse.SC_OK);
			return "{ \"totalDevices\" :" + totalDevices + "}";
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getTotalDevices");
		}
	}

	/**
	 * Admin action.To send messages to devices
	 * 
	 * @param response
	 */
	@Deprecated
	@RequestMapping(value = "/admin/gcm/sendmessage", method = RequestMethod.GET, produces = "application/json")
	public void sendMessage(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<String> devices = GcmDataStore.getDevices();
			// String status;
			if (devices.isEmpty()) {
				// status = "Message ignored as there is no device registered!";
			} else {
				Queue queue = QueueFactory.getQueue("gcm");
				// NOTE: check below is for demonstration purposes; a real
				// application
				// could always send a multicast, even for just one recipient
				if (devices.size() == 1) {
					// send a single message using plain post
					String device = devices.get(0);
					queue.add(withUrl("/send").param(PARAMETER_DEVICE, device));
					// status = "Single message queued for registration id " +
					// device;
				} else {
					// send a multicast message using JSON
					// must split in chunks of 1000 devices (GCM limit)
					int total = devices.size();
					List<String> partialDevices = new ArrayList<String>(total);
					int counter = 0;
					// int tasks = 0;
					for (String device : devices) {
						counter++;
						partialDevices.add(device);
						int partialSize = partialDevices.size();
						if (partialSize == GcmDataStore.MULTICAST_SIZE
								|| counter == total) {
							String multicastKey = GcmDataStore
									.createMulticast(partialDevices);
							LOGGER.fine("Queuing " + partialSize
									+ " devices on multicast " + multicastKey);
							TaskOptions taskOptions = TaskOptions.Builder
									.withUrl("/gcm/send")
									.param(PARAMETER_MULTICAST, multicastKey)
									.method(Method.POST);
							queue.add(taskOptions);
							partialDevices.clear();
							// tasks++;
						}
					}
					// status = "Queued tasks to send " + tasks +
					// " multicast messages to " +
					// total + " devices";
				}
			}
			response.setStatus(HttpServletResponse.SC_OK);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	// /**
	// * Admin action.To send messages to devices
	// *
	// * @param phoneId
	// * @param action
	// * @param message
	// * @param response
	// */
	// @RequestMapping(value = "/admin/gcm/action/{phoneId}/{action}/{message}",
	// method = RequestMethod.GET, produces = "application/json")
	// public void initiateAction(@PathVariable String phoneId,
	// @PathVariable String action, @PathVariable String message,
	// HttpServletResponse response) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering");
	// if (LOGGER.isLoggable(Level.INFO)) {
	// LOGGER.info("phoneId: " + phoneId);
	// LOGGER.info("action: " + action);
	// LOGGER.info("message: " + message);
	// }
	// // Get GCM id from phone id
	// String userId = deviceService.getUserIdGivenPhoneId(phoneId);
	// String gcmId = deviceService.getGcmIdGivenUserId(userId);
	//
	// Map<String, String> values = new HashMap<String, String>();
	// values.put(PARAMETER_ACTION, action);
	// values.put(PARAMETER_MESSAGE, message);
	// sendSingleMessage(gcmId, values, response);
	//
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting");
	// }
	// }
	//
	// /**
	// * Admin action.To send messages to devices
	// *
	// * @param phoneId
	// * @param action
	// * @param response
	// */
	// @RequestMapping(value = "/admin/gcm/action/{phoneId}/{action}", method =
	// RequestMethod.GET, produces = "application/json")
	// public void initiateAction(@PathVariable String phoneId,
	// @PathVariable String action, HttpServletResponse response) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering");
	// if (LOGGER.isLoggable(Level.INFO)) {
	// LOGGER.info("phoneId: " + phoneId);
	// LOGGER.info("action: " + action);
	// }
	//
	// // Get GCM id from phone id
	// String userId = deviceService.getUserIdGivenPhoneId(phoneId);
	// String gcmId = deviceService.getGcmIdGivenUserId(userId);
	//
	// if (LOGGER.isLoggable(Level.INFO)) {
	// LOGGER.info("userId: " + userId);
	// LOGGER.info("gcmId: " + gcmId);
	// }
	// Map<String, String> values = new HashMap<String, String>();
	// values.put(PARAMETER_ACTION, action);
	// sendSingleMessage(gcmId, values, response);
	//
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting");
	// }
	// }

	/**
	 * Admin action
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value = "/admin/gcm/send")
	public void send(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (req.getHeader(HEADER_QUEUE_NAME) == null) {
				throw new IOException("Missing header " + HEADER_QUEUE_NAME);
			}
			if (mSender == null) {
				mSender = new Sender(API_KEY);
			}
			String retryCountHeader = req.getHeader(HEADER_QUEUE_COUNT);
			LOGGER.fine("retry count: " + retryCountHeader);
			if (retryCountHeader != null) {
				int retryCount = Integer.parseInt(retryCountHeader);
				if (retryCount > MAX_RETRY) {
					LOGGER.severe("Too many retries, dropping task");
					taskDone(resp);
					return;
				}
			}
			String gcmId = req.getParameter(PARAMETER_DEVICE);
			String action = req.getParameter(PARAMETER_ACTION);
			String message = req.getParameter(PARAMETER_MESSAGE);
			if (gcmId != null) {
				Map<String, String> values = new HashMap<String, String>();
				values.put(PARAMETER_ACTION, action);
				values.put(PARAMETER_MESSAGE, message);
				sendSingleMessage(gcmId, values, resp);
				return;
			}
			String multicastKey = req.getParameter(PARAMETER_MULTICAST);
			if (multicastKey != null) {
				sendMulticastMessage(multicastKey, resp);
				return;
			}
			LOGGER.severe("Invalid request!");
			taskDone(resp);
			return;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
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
				result = mSender.sendNoRetry(message, gcmId);
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
	 * Admin action
	 * 
	 * @param multicastKey
	 * @param resp
	 */
	@Deprecated
	private void sendMulticastMessage(String multicastKey,
			HttpServletResponse resp) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// Recover registration ids from GcmDataStore
			List<String> regIds = GcmDataStore.getMulticast(multicastKey);
			Message message = new Message.Builder().build();
			MulticastResult multicastResult;
			try {
				multicastResult = mSender.sendNoRetry(message, regIds);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception posting " + message, e);
				multicastDone(resp, multicastKey);
				return;
			}
			boolean allDone = true;
			// check if any registration id must be updated
			if (multicastResult.getCanonicalIds() != 0) {
				List<Result> results = multicastResult.getResults();
				for (int i = 0; i < results.size(); i++) {
					String canonicalRegId = results.get(i)
							.getCanonicalRegistrationId();
					if (canonicalRegId != null) {
						String gcmId = regIds.get(i);
						GcmDataStore.updateRegistration(gcmId, canonicalRegId);
					}
				}
			}
			if (multicastResult.getFailure() != 0) {
				// there were failures, check if any could be retried
				List<Result> results = multicastResult.getResults();
				List<String> retriableRegIds = new ArrayList<String>();
				for (int i = 0; i < results.size(); i++) {
					String error = results.get(i).getErrorCodeName();
					if (error != null) {
						String gcmId = regIds.get(i);
						LOGGER.warning("Got error (" + error + ") for gcmId "
								+ gcmId);
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							// application has been removed from device -
							// unregister
							// it
							GcmDataStore.unregister(gcmId);
						}
						if (error.equals(Constants.ERROR_UNAVAILABLE)) {
							retriableRegIds.add(gcmId);
						}
					}
				}
				if (!retriableRegIds.isEmpty()) {
					// update task
					GcmDataStore.updateMulticast(multicastKey, retriableRegIds);
					allDone = false;
					retryTask(resp);
				}
			}
			if (allDone) {
				multicastDone(resp, multicastKey);
			} else {
				retryTask(resp);
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * Admin action
	 * 
	 * @param resp
	 * @param encodedKey
	 */
	private void multicastDone(HttpServletResponse resp, String encodedKey) {
		GcmDataStore.deleteMulticast(encodedKey);
		taskDone(resp);
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
