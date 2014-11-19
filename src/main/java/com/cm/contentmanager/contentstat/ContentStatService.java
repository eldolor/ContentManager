package com.cm.contentmanager.contentstat;

import java.util.List;
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

	public void saveContentStat(ContentStat contentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContentStat");

			contentStatDao.saveContentStat(contentStat);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContent");
		}
	}
	public void saveContentDownloadStat(ContentDownloadStat contentDownloadStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			contentStatDao.saveContentDownloadStat(contentDownloadStat);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting ");
		}
	}
	public void saveUnmanagedContentStat(UnmanagedContentStat contentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			contentStatDao.saveUnmanagedContentStat(contentStat);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
	public List<ContentStatByApplicationSummary> getSummaryByApplication(Long applicationId, Long eventStartTimeMs, Long eventEndTimeMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			return contentStatDao.getSummaryByApplication(applicationId, eventStartTimeMs, eventEndTimeMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
	public List<ContentStatByApplicationSummary> getSummaryByApplication(Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			return contentStatDao.getSummaryByApplication(applicationId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
	public List<ContentStatByContentGroupSummary> getSummaryByContentGroup(Long contentGroupId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			return contentStatDao.getSummaryByContentGroup(contentGroupId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
	public List<ContentStatByContentSummary> getSummaryByContent(Long contentId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			return contentStatDao.getSummaryByContent(contentId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
		
	public void rollupSummary(Long applicationId, Long eventStartTimeMs, Long eventEndTimeMs){
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			contentStatDao.rollupSummary(applicationId, eventStartTimeMs, eventEndTimeMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
		
	}
}
