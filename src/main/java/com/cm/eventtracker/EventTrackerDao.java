package com.cm.eventtracker;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
 class EventTrackerDao {
	private static final Logger LOGGER = Logger.getLogger(EventTrackerDao.class
			.getName());

	 void saveTrackingEvents(List<TrackingEvent> pTrackingEvents) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveTrackingEvents");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				for (TrackingEvent lTrackingEvent : pTrackingEvents) {
					pm.makePersistent(lTrackingEvent);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Saving tracking event:: "
								+ lTrackingEvent.getId());
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveTrackingEvents");
		}
	}

}
