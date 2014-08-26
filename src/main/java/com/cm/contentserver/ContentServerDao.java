package com.cm.contentserver;

import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.util.PMF;

@Component
public class ContentServerDao {
	private static final Logger LOGGER = Logger
			.getLogger(ContentServerDao.class.getName());

	void saveContentRequest(ContentRequest pContentRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContentRequest");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(pContentRequest);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContentRequest");
		}
	}

	void upsertHandshake(Handshake pHandshake) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering upsertHandshake");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Handshake.class);
				// look up by registration id
				q.setFilter("gcmRegistrationId == gcmRegistrationIdParam");
				q.declareParameters("long gcmRegistrationIdParam");
				List<Handshake> lList = (List<Handshake>) q.execute(pHandshake
						.getGcmRegistrationId());
				if (lList != null && (!lList.isEmpty())) {
					// if exists then update
					Handshake lHandshake = lList.get(0);
					// update last location
					lHandshake.setAccuracy(pHandshake.getAccuracy());
					lHandshake.setAltitude(pHandshake.getAltitude());
					lHandshake.setBearing(pHandshake.getBearing());
					lHandshake.setLatitude(pHandshake.getLatitude());
					lHandshake.setLongitude(pHandshake.getLongitude());
					lHandshake.setProvider(pHandshake.getProvider());
					lHandshake.setSpeed(pHandshake.getSpeed());

					// update
					lHandshake.setLastKnownTimestamp(pHandshake
							.getLastKnownTimestamp());

					// use the timecreated values provided by the handset for
					// time updated
					lHandshake.setTimeUpdatedMs(lHandshake.getTimeCreatedMs());
					lHandshake.setTimeUpdatedTimeZoneOffsetMs(lHandshake
							.getTimeCreatedTimeZoneOffsetMs());
				} else {
					// save
					pm.makePersistent(pHandshake);
				}

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting upsertHandshake");
		}
	}

}
