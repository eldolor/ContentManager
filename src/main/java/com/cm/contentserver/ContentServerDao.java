package com.cm.contentserver;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Component;

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

}
