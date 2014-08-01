package com.cm.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cm.config.Configuration;
import com.cm.contentmanager.content.ContentHelper;
import com.cm.contentserver.ContentRequest;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Component
public class GcmHelper {
	public Sender mSender;
	private static final String GOOGLE_API_KEY = Configuration.GOOGLE_API_KEY
			.getValue();
	private static final int GCM_MESSAGE_SIZE_LIMIT_BYTES = Integer
			.valueOf(Configuration.GCM_MESSAGE_SIZE_LIMIT_BYTES.getValue());

	private static final Logger LOGGER = Logger.getLogger(GcmHelper.class
			.getName());
	@Autowired
	private ContentHelper contentHelper;
	@Autowired
	private GcmService gcmService;

	/**
	 * Sends a MESSAGE_TYPE_CONTENT_LIST, or MESSAGE_TYPE_SEND_TO_SYNC if the
	 * payload size is > 4Kb
	 * 
	 * @param pGcmId
	 * @param pContentRequest
	 * @return
	 * @throws DeviceNotRegisteredException
	 * @throws DeviceHasMoreThanOneRegistration
	 */
	public boolean sendContentListMessage(String pGcmId,
			ContentRequest pContentRequest)
			throws DeviceNotRegisteredException,
			DeviceHasMoreThanOneRegistration {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			String lJsonArrayString = contentHelper.getContent(pContentRequest);
			if (lJsonArrayString != null) {

				if (lJsonArrayString.getBytes().length < GCM_MESSAGE_SIZE_LIMIT_BYTES) {
					if (LOGGER.isLoggable(Level.INFO))
						// send the gcm message to the device
						try {
							LOGGER.info("Begin sending "
									+ Configuration.MESSAGE_TYPE_CONTENT_LIST
											.getValue() + " message to device");
							HashMap<String, String> values = new HashMap<String, String>();
							values.put(Configuration.MESSAGE_TYPE.getValue(),
									Configuration.MESSAGE_TYPE_CONTENT_LIST
											.getValue());
							values.put(Configuration.MESSAGE_TYPE_CONTENT_LIST
									.getValue(), lJsonArrayString);
							this.sendMessage(pGcmId, values);
						} finally {
							LOGGER.info("End sending "
									+ Configuration.MESSAGE_TYPE_CONTENT_LIST
											.getValue() + " message to device");
						}
				} else {
					// Payload size is greater than the GCM limit; send an
					// SEND_TO_SYNC message to the device
					HashMap<String, String> values = new HashMap<String, String>();
					values.put(Configuration.MESSAGE_TYPE.getValue(),
							Configuration.MESSAGE_TYPE_SEND_TO_SYNC.getValue());
					try {
						LOGGER.info("Begin sending "
								+ Configuration.MESSAGE_TYPE_SEND_TO_SYNC
										.getValue() + " message to device");
						this.sendMessage(pGcmId, values);
					} finally {
						LOGGER.info("End sending "
								+ Configuration.MESSAGE_TYPE_SEND_TO_SYNC
										.getValue() + " message to device");
					}
				}

			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			return true;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void sendContentListMessages(ContentRequest pContentRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// TODO: send multicast
			// GCMReqistrationRequest ties the applicationId to all GCM Ids. The
			// issue is how to retrieve a list of active GcmIds. There are no
			// additional attributes that make a GcmRegistrationRequest unique
			// per device. There might be a way of leveraging
			// DeviceAlreadyRegisteredException to use the Canonical
			// registration id, as unique. Maybe we could delete the
			// registration id that's failed, from the database, and insert the
			// canonical registration id

			List<GcmRegistrationRequest> lGcmRegistrationRequests = gcmService
					.getGcmRegistrationRequests(pContentRequest
							.getTrackingId());
			List<String> lGcmRegIds = new ArrayList<String>();
			for (GcmRegistrationRequest lGcmRegistrationRequest : lGcmRegistrationRequests) {
				lGcmRegIds.add(lGcmRegistrationRequest.getGcmId());
			}
			this.sendMulticastMessage(lGcmRegIds);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private void sendMulticastMessage(List<String> pGcmRegistrationIds) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Message message = new Message.Builder().build();
			MulticastResult multicastResult;
			try {
				multicastResult = mSender.send(message, pGcmRegistrationIds,
						Integer.valueOf(Configuration.GCM_MAX_ATTEMPTS
								.getValue()));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception posting " + message, e);
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
						// String gcmId = regIds.get(i);
						// GcmDataStore.updateRegistration(gcmId,
						// canonicalRegId);
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
						// String gcmId = regIds.get(i);
						// LOGGER.warning("Got error (" + error + ") for gcmId "
						// + gcmId);
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							// application has been removed from device -
							// unregister
							// it
							// GcmDataStore.unregister(gcmId);
						}
						if (error.equals(Constants.ERROR_UNAVAILABLE)) {
							// retriableRegIds.add(gcmId);
						}
					}
				}
				if (!retriableRegIds.isEmpty()) {
					// update task
					// GcmDataStore.updateMulticast(multicastKey,
					// retriableRegIds);
					allDone = false;
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
	 * @param pValues
	 * @return
	 * @throws DeviceNotRegisteredException
	 * @throws DeviceHasMoreThanOneRegistration
	 */
	public boolean sendMessage(String pGcmId, Map<String, String> pValues)
			throws DeviceNotRegisteredException,
			DeviceHasMoreThanOneRegistration {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			return this.sendSingleMessage(pGcmId, pValues);
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
	 * @return
	 * @throws DeviceNotRegisteredException
	 * @throws DeviceHasMoreThanOneRegistration
	 */
	public boolean sendMessage(String pGcmId, String pMessageType,
			String pMessage) throws DeviceNotRegisteredException,
			DeviceHasMoreThanOneRegistration {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return this.sendSingleMessage(pGcmId, pMessageType, pMessage);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private boolean sendSingleMessage(String pGcmId, Map<String, String> pValues)
			throws DeviceNotRegisteredException,
			DeviceHasMoreThanOneRegistration {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			Builder lBuilder = new Message.Builder();
			for (Iterator<String> iterator = pValues.keySet().iterator(); iterator
					.hasNext();) {
				String lKey = iterator.next();
				lBuilder.addData(lKey, pValues.get(lKey));
			}
			Message lMessage = lBuilder.build();
			return this.sendSingleMessage(pGcmId, lMessage);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private boolean sendSingleMessage(String pGcmId, String pMessageType,
			String pMessage) throws DeviceNotRegisteredException,
			DeviceHasMoreThanOneRegistration {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			Builder lBuilder = new Message.Builder();
			lBuilder.addData(pMessageType, pMessage);
			Message lMessage = lBuilder.build();

			return this.sendSingleMessage(pGcmId, lMessage);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private boolean sendSingleMessage(String pGcmId, Message mMessage)
			throws DeviceNotRegisteredException,
			DeviceHasMoreThanOneRegistration {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Sending message to device " + pGcmId);

			if (mSender == null) {
				mSender = new Sender(GOOGLE_API_KEY);
			}

			Result result;
			try {
				result = mSender.send(mMessage, pGcmId, Integer
						.valueOf(Configuration.GCM_MAX_ATTEMPTS.getValue()));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception posting " + mMessage, e);
				return false;
			}
			if (result == null) {
				return false;
			}
			if (result.getMessageId() != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Succesfully sent message to device " + pGcmId);
				String canonicalRegId = result.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					// same device has more than on registration id: update it
					LOGGER.finest("canonicalRegId " + canonicalRegId);
					// GcmDataStore.updateRegistration(gcmId, canonicalRegId);
					throw new DeviceHasMoreThanOneRegistration(canonicalRegId);
				}
				return true;
			} else {
				String error = result.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister it
					// GcmDataStore.unregister(gcmId);
					throw new DeviceNotRegisteredException(
							result.getErrorCodeName());
				} else {
					LOGGER.severe("Error sending message to device " + pGcmId
							+ ": " + error);
				}
				return false;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
}