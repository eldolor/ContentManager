package com.cm.gcm;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.ContentHelper;
import com.cm.contentserver.ContentRequest;
import com.cm.gcm.transfer.GcmRegistrationRequest;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;
import com.cm.util.ValidationError;

@Controller
public class GcmController {

	private static final Logger LOGGER = Logger.getLogger(GcmController.class
			.getName());

	@Autowired
	private GcmService gcmService;
	@Autowired
	private GcmHelper gcmHelper;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContentHelper contentHelper;

	@RequestMapping(value = "/gcm/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> register(
			@RequestBody GcmRegistrationRequest gcmRegistrationRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering register");
			// validate registration request
			List<ValidationError> lErrors = validate(gcmRegistrationRequest);
			if (!lErrors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return lErrors;
			}

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
		} catch (DeviceHasMultipleRegistrations e) {
			// log it for now
			LOGGER.log(Level.WARNING,
					"Device has more than one registration. Canonical registration id is "
							+ e.getCanonicalRegistrationId());
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			List<ValidationError> errors = new ArrayList<ValidationError>();
			ValidationError error = new ValidationError();
			// The exception contains the GCM error code
			error.setCode(GcmService.DEVICE_HAS_MULTIPLE_REGISTRATIONS_ERROR_CODE);
			error.setDescription(e.getCanonicalRegistrationId());
			errors.add(error);
			LOGGER.log(Level.WARNING,
					"Device has multiple registrations with GCM Servers");
			return errors;
		} catch (DeviceNotRegisteredException e) {
			LOGGER.log(Level.WARNING, "Device not registered with GCM Servers");
			// ask the device to retry the GCM registration process
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			List<ValidationError> errors = new ArrayList<ValidationError>();
			ValidationError error = new ValidationError();
			// The exception contains the GCM error code
			error.setCode(GcmService.DEVICE_NOT_REGISTERED_ERROR_CODE);
			error.setDescription("Device not registered with GCM Servers");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Device not registered with GCM Servers");
			return errors;
		} catch (IOException e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, "Unable to connect with GCM servers.", e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting register");

		}
	}

	@RequestMapping(value = "/gcm/register/canonical", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> registerCanonical(
			@RequestBody GcmRegistrationRequest gcmRegistrationRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering registerCanonical");
			// validate registration request
			List<ValidationError> lErrors = validateCanonical(gcmRegistrationRequest);
			if (!lErrors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return lErrors;
			}
			com.cm.gcm.GcmRegistrationRequest lGcmReqistrationRequestDomainObject = convertToDomainObject(gcmRegistrationRequest);

			gcmService.register(lGcmReqistrationRequestDomainObject);
			// deprecate the use of the old gcm id
			gcmService.deprecate(gcmRegistrationRequest.getDeprecatedGcmId());
			response.setStatus(HttpServletResponse.SC_OK);
			return null;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting registerCanonical");

		}
	}

	@RequestMapping(value = "/tasks/gcm/sendcontentlistmessages/{trackingId}", method = RequestMethod.POST)
	public void sendContentListMessages(@PathVariable String trackingId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering sendContentListMessages");

			// send the new content list to the affected devices
			gcmHelper.sendContentListMessages(contentHelper
					.getGenericContentRequest(trackingId));

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to connect with GCM servers.", e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting sendContentListMessages");

		}
	}

	@RequestMapping(value = "/tasks/gcm/sendcontentlistmessage/{trackingId}/{gcmId}", method = RequestMethod.POST)
	public void sendContentListMessage(@PathVariable String trackingId,
			@PathVariable String gcmId, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering sendContentListMessage");

			// send the new content list to a single device
			gcmHelper.sendContentListMessage(gcmId,
					contentHelper.getGenericContentRequest(gcmId));

		} catch (DeviceHasMultipleRegistrations e) {
			// Just log it,
			LOGGER.log(Level.WARNING,
					"Device has more than one registration. Canonical registration id is "
							+ e.getCanonicalRegistrationId());
		} catch (DeviceNotRegisteredException e) {
			LOGGER.log(Level.WARNING, "Device not registered with GCM Servers");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to connect with GCM servers.", e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting sendContentListMessage");

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
			gcmService.unRegister(gcmId);

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
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

	private List<ValidationError> validate(
			GcmRegistrationRequest gcmRegistrationRequest) {
		try {
			List<ValidationError> errors = new ArrayList<ValidationError>();
			if (Utils.isEmpty(gcmRegistrationRequest.getTrackingId())) {
				ValidationError error = new ValidationError();
				// The exception contains the GCM error code
				error.setCode(GcmService.MISSING_TRACKING_ID_ERROR_CODE);
				error.setDescription("The tracking id is missing");
				errors.add(error);
				LOGGER.log(Level.WARNING, "The tracking id is missing");
			}
			if (Utils.isEmpty(gcmRegistrationRequest.getGcmId())) {
				ValidationError error = new ValidationError();
				// The exception contains the GCM error code
				error.setCode(GcmService.MISSING_GCM_ID_ERROR_CODE);
				error.setDescription("The gcm id is missing");
				errors.add(error);
				LOGGER.log(Level.WARNING, "The gcm id is missing");
			}
			return errors;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting validate");
		}
	}

	private List<ValidationError> validateCanonical(
			GcmRegistrationRequest gcmRegistrationRequest) {
		try {
			List<ValidationError> errors = new ArrayList<ValidationError>();
			if (Utils.isEmpty(gcmRegistrationRequest.getDeprecatedGcmId())) {
				ValidationError error = new ValidationError();
				// The exception contains the GCM error code
				error.setCode(GcmService.MISSING_DEPRECATED_GCM_ID_ERROR_CODE);
				error.setDescription("The deprecated gcm id is missing");
				errors.add(error);
				LOGGER.log(Level.WARNING, "The deprecated gcm id is missing");
			}
			// rest of the validations
			errors.addAll(validate(gcmRegistrationRequest));

			return errors;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting validate");
		}
	}

}
