package com.cm.contentmanager.contentstat;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
class ContentStatDao {
	private static final Logger LOGGER = Logger.getLogger(ContentStatDao.class
			.getName());

	void saveContentStat(ContentStat contentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContentStat");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(contentStat);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContentStat");
		}
	}

}
