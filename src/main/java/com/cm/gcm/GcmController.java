package com.cm.gcm;

import java.util.ArrayList;
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

import com.cm.contentserver.ContentRequest;
import com.cm.gcm.transfer.GcmRegistrationRequest;
import com.cm.util.ValidationError;

@Controller
public class GcmController {

	private static final Logger LOGGER = Logger.getLogger(GcmController.class
			.getName());

	@Autowired
	private GcmService gcmService;
	@Autowired
	private GcmHelper gcmHelper;

	@RequestMapping(value = "/gcm/register", method = RequestMethod.POST)
	public List<ValidationError> register(
			@RequestBody GcmRegistrationRequest gcmRegistrationRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering register");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering Registering GCM:"
						+ gcmRegistrationRequest.getGcmId());
			com.cm.gcm.GcmRegistrationRequest lGcmReqistrationRequestDomainObject = convertToDomainObject(gcmRegistrationRequest);
			gcmService.register(lGcmReqistrationRequestDomainObject);

			// send the content list to the device
			gcmHelper
					.sendContentListMessage(
							lGcmReqistrationRequestDomainObject.getGcmId(),
							convertToContentRequest(lGcmReqistrationRequestDomainObject));

			response.setStatus(HttpServletResponse.SC_OK);
			return null;
		} catch (DeviceHasMoreThanOneRegistration e) {
			// The exception contains the canonical registration id
			String lCanonicalRegistrationId = e.getMessage();
			// log it for now
			LOGGER.log(Level.WARNING,
					"Device has more than one registration. Canonical registration id is "
							+ lCanonicalRegistrationId);
			// status OK
			response.setStatus(HttpServletResponse.SC_OK);
			return null;
		} catch (DeviceNotRegisteredException e) {
			LOGGER.log(Level.WARNING, "Device not registered with GCM Servers");
			// ask the device to retry the GCM registration process
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			List<ValidationError> errors = new ArrayList<ValidationError>();
			ValidationError error = new ValidationError();
			// The exception contains the GCM error code
			error.setCode(e.getMessage());
			error.setDescription("Device not registered with GCM Servers");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Name cannot be blank");
			return errors;
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
			lContentRequest.setTrackingId(pGcmRegistrationRequest
					.getTrackingId());
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
			lGcmRegistrationRequest.setTrackingId(pGcmRegistrationRequest
					.getTrackingId());
			lGcmRegistrationRequest.setDeviceId(pGcmRegistrationRequest
					.getDeviceId());
			lGcmRegistrationRequest.setAccuracy(pGcmRegistrationRequest
					.getAccuracy());
			lGcmRegistrationRequest.setAltitude(pGcmRegistrationRequest
					.getAltitude());
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

}
