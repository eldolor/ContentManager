package com.cm.gcm;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.util.ValidationError;

@Service
public class GcmService {

	@Autowired
	private GcmDao gcmDao;

	private static final Logger LOGGER = Logger.getLogger(GcmService.class
			.getName());
	public static final String DEVICE_REGISTRATION_ID_DEPRECATED_ERROR_CODE = "DeviceRegistrationIdDeprecated";
	public static final String DEVICE_REGISTRATION_ID_DELETED_ERROR_CODE = "DeviceRegistrationIdDeleted";
	public static final String DEVICE_HAS_MULTIPLE_REGISTRATIONS_ERROR_CODE = "DeviceHasMultipleRegistrations";
	public static final String DEVICE_NOT_REGISTERED_ERROR_CODE = "DeviceNotRegistered";

	public void register(GcmRegistrationRequest gcmRegistrationRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering register");
			GcmRegistrationRequest lGcmRegistrationRequest = gcmDao
					.getGcmRegistrationRequest(gcmRegistrationRequest
							.getGcmId());
			// does not exist
			if (lGcmRegistrationRequest == null) {
				gcmDao.save(gcmRegistrationRequest);
			} else {
				// update using the same timestamps
				lGcmRegistrationRequest.setTimeUpdatedMs(gcmRegistrationRequest
						.getTimeUpdatedMs());
				lGcmRegistrationRequest
						.setTimeUpdatedTimeZoneOffsetMs(gcmRegistrationRequest
								.getTimeUpdatedTimeZoneOffsetMs());
				gcmDao.updateTimestamp(lGcmRegistrationRequest);
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting register");
		}
	}

	public void unRegister(String gcmRegistrationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering unRegister");
			gcmDao.unRegister(gcmRegistrationId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting unRegister");
		}
	}

	public void updateWithCanonicalRegId(String gcmRegistrationId,
			String canonicalGcmRegistrationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			gcmDao.updateWithCanonicalRegId(gcmRegistrationId,
					canonicalGcmRegistrationId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting update");
		}

	}

	public List<GcmRegistrationRequest> getGcmRegistrationRequests(
			String trackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getGcmRegistrationRequests");

			List<GcmRegistrationRequest> lGcmRegistrationRequests = gcmDao
					.getGcmRegistrationRequests(trackingId);
			int lSize = (lGcmRegistrationRequests != null) ? lGcmRegistrationRequests
					.size() : 0;
			if (lSize == 0)
				LOGGER.warning("Returning " + lSize
						+ " GCM registration requests");
			else
				LOGGER.info("Returning " + lSize + " GCM registration requests");

			return lGcmRegistrationRequests;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getGcmRegistrationRequests");
		}
	}

	public GcmRegistrationRequest getGcmRegistrationRequest(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getGcmRegistrationRequest");

			return gcmDao.getGcmRegistrationRequest(gcmId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getGcmRegistrationRequest");
		}
	}

	public void updateDeviceNotRegisteredWithGcm(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateDeviceNotRegisteredWithGcm");

			gcmDao.updateDeviceNotRegisteredWithGcm(gcmId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateDeviceNotRegisteredWithGcm");
		}
	}

	public void deprecate(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deprecate");

			gcmDao.deprecate(gcmId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deprecate");
		}
	}

	public List<ValidationError> evaluateGcmRegistrationStatus(
			GcmRegistrationRequest pGcmRegistrationRequest) {
		List<ValidationError> lErrors = new ArrayList<ValidationError>();
		// evaluate if
		if (pGcmRegistrationRequest.isDeprecated()) {
			LOGGER.log(Level.WARNING, "Registration id is Deprecated");
			ValidationError lError = new ValidationError();
			// The exception contains the GCM error code
			lError.setCode(GcmService.DEVICE_REGISTRATION_ID_DEPRECATED_ERROR_CODE);
			lError.setDescription("Registration id is Deprecated");
			lErrors.add(lError);
		}
		if (pGcmRegistrationRequest.isDeleted()) {
			LOGGER.log(Level.WARNING, "Registration id is Deleted");
			ValidationError lError = new ValidationError();
			// The exception contains the GCM error code
			lError.setCode(GcmService.DEVICE_REGISTRATION_ID_DELETED_ERROR_CODE);
			lError.setDescription("Registration id is Deleted");
			lErrors.add(lError);
		}
		if (pGcmRegistrationRequest.getGcmDeviceHasMultipleRegistrations()) {
			// log it for now
			LOGGER.log(Level.WARNING,
					"Device has more than one registration. Canonical registration id is "
							+ pGcmRegistrationRequest.getCanonicalGcmId());
			ValidationError lError = new ValidationError();
			// The exception contains the GCM error code
			lError.setCode(GcmService.DEVICE_HAS_MULTIPLE_REGISTRATIONS_ERROR_CODE);
			lError.setDescription(pGcmRegistrationRequest.getCanonicalGcmId());
			lErrors.add(lError);
		}
		if (pGcmRegistrationRequest.getGcmDeviceNotRegistered()) {
			LOGGER.log(Level.WARNING, "Device not registered with GCM Servers");
			// ask the device to retry the GCM registration process
			ValidationError lError = new ValidationError();
			// The exception contains the GCM error code
			lError.setCode(GcmService.DEVICE_NOT_REGISTERED_ERROR_CODE);
			lError.setDescription("Device not registered with GCM Servers");
			lErrors.add(lError);
		}
		return lErrors;
	}

}
