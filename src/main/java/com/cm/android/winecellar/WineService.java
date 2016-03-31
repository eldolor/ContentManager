package com.cm.android.winecellar;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.usermanagement.user.User;
import com.cm.util.Utils;

@Service
public class WineService {
	@Autowired
	private WineDao wineDao;

	private static final Logger LOGGER = Logger.getLogger(WineService.class
			.getName());

	public Wine get(Long rowId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return wineDao.get(rowId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public Wine get(String uri) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return wineDao.get(uri);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public Wine save(Wine wine) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");

			return wineDao.save(wine);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

	public void delete(Long rowId, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			wineDao.delete(rowId, timeUpdatedMs, timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deletedeleteWine");
		}

	}


	public Wine update(Wine wine) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			return wineDao.update(wine);
			
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting update");
		}
	}

}
