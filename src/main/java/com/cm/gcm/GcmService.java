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

			gcmDao.save(gcmRegistrationRequest);
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

			return gcmDao.getGcmRegistrationRequests(trackingId);
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
