package com.cm.gcm;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class GcmService {

	// @Autowired
	// private DeviceService deviceService;
	// @Autowired
	// private DeviceDao deviceDao;

	private static final Logger LOGGER = Logger.getLogger(GcmService.class
			.getName());

	// @Deprecated
	public boolean register(GcmRegistrationRequest gcmregreq) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering GCM Register");

		// // get telephony record
		// DeviceProfileTelephony dpt = deviceDao.getDeviceProfileTelephony(
		// gcmregreq.getUserId(), gcmregreq.getDeviceId());
		// // update telephony record
		// if (dpt != null) {
		// if ((dpt.getGcmId() == null)
		// || !dpt.getGcmId().equalsIgnoreCase(gcmregreq.getGcmId())) {
		// dpt.setGcmId(gcmregreq.getGcmId());
		// deviceService.updateDeviceProfileTelephony(dpt);
		// if (LOGGER.isLoggable(Level.INFO))
		// LOGGER.info("GCM Id updated: " + gcmregreq.getGcmId());
		// } else {
		// if (LOGGER.isLoggable(Level.INFO))
		// LOGGER.info("GCM Id already exists");
		// }
		// return true;
		// } else {
		// return false;
		// }
		return true;
	}

}
