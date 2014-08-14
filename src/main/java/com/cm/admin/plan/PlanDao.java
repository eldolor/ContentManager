package com.cm.admin.plan;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
public class PlanDao {
	private static final Logger LOGGER = Logger.getLogger(PlanDao.class
			.getName());

	public void save(Plan plan) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(plan);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

}
