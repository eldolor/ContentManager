package com.cm.contentmanager.contentstat;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentStatService {
	@Autowired
	private ContentStatDao contentStatDao;

	private static final Logger LOGGER = Logger
			.getLogger(ContentStatService.class.getName());

	public void saveContentStat(ContentStat ContentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContentStat");

			contentStatDao.saveContentStat(ContentStat);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContent");
		}
	}

}
