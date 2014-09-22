package com.cm.contentmanager.contentstat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.contentmanager.application.Application;
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

	List<ContentStatDailySummary> getDailySummary(Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentStatDailySummary.class);
				q.setFilter("applicationId == applicationIdParam");
				q.declareParameters("Long applicationIdParam");
				q.setOrdering("eventTimeMs desc");
				return (List<ContentStatDailySummary>) q.execute(applicationId);
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void rollupDailySummary(Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentStat.class);
				q.setFilter("applicationId == applicationIdParam");
				q.declareParameters("Long applicationIdParam");
				q.setOrdering("applicationId, contentGroupId, contentId, eventType");
				List<ContentStat> lList = (List<ContentStat>) q
						.execute(applicationId);

				long lContentIdCount = 0L, lApplicationIdCount = 0L, lContentGroupIdCount = 0L, lEventTypeCount = 0L;
				long lPrevApplicationId = 0L, lPrevContentGroupId = 0L, lPrevContentId = 0L;
				String lPrevEventType = "";

				for (ContentStat contentStat : lList) {
					if (contentStat.getApplicationId() != lPrevApplicationId) {
						lPrevApplicationId = contentStat.getApplicationId();
					} else {
						lApplicationIdCount++;
					}
					if (contentStat.getContentGroupId() != lPrevContentGroupId) {
						lPrevContentGroupId = contentStat.getContentGroupId();
					} else {
						lContentGroupIdCount++;
					}
					if (contentStat.getContentId() != lPrevContentId) {
						lPrevContentId = contentStat.getContentGroupId();
					} else {
						lContentIdCount++;
					}
					if (!contentStat.getEventType().equals(lPrevEventType)) {
						lPrevEventType = contentStat.getEventType();
					} else {
						lEventTypeCount++;
					}
				}
				// create the daily summary
				ContentStatDailySummary lSummary = new ContentStatDailySummary();
				lSummary.setApplicationId(applicationId);
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
