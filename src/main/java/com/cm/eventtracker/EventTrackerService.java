package com.cm.eventtracker;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTrackerService {
	@Autowired
	private EventTrackerDao eventTrackerDao;

	private static final Logger LOGGER = Logger
			.getLogger(EventTrackerService.class.getName());

	public void saveTrackingEvents(List<TrackingEvent> pTrackingEvents) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveTrackingEvents");
			eventTrackerDao.saveTrackingEvents(pTrackingEvents);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveTrackingEvents");
		}
	}

}
