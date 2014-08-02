package com.cm.gcm;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcmService {

	@Autowired
	private GcmDao gcmDao;

	private static final Logger LOGGER = Logger.getLogger(GcmService.class
			.getName());

	public void register(GcmRegistrationRequest gcmRegistrationRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
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
				gcmDao.updateApplication(lGcmRegistrationRequest);
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

	public List<GcmRegistrationRequest> getGcmRegistrationRequests(
			String trackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getGcmRegistrationRequests");

			List<GcmRegistrationRequest> lGcmRegistrationRequests = gcmDao
					.getGcmRegistrationRequests(trackingId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning "
						+ ((lGcmRegistrationRequests != null) ? lGcmRegistrationRequests
								.size() : 0) + " GCM registration requests");
			return lGcmRegistrationRequests;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getGcmRegistrationRequests");
		}
	}

	public void unRegister(String gcmId, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");

			gcmDao.deleteGcmRegistrationRequest(gcmId, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

}
