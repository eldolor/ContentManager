package com.cm.contentmanager.contentstat;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;
import com.cm.util.Utils;

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

	void saveContentDownloadStat(ContentDownloadStat contentDownloadStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(contentDownloadStat);

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

	void saveUnmanagedContentStat(UnmanagedContentStat contentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
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
				LOGGER.info("Exiting");
		}
	}

	List<ContentStatByApplicationSummary> getSummaryByApplication(
			Long applicationId, Long eventStartTimeMs, Long eventEndTimeMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentStatByApplicationSummary.class);
				q.setFilter("applicationId == applicationIdParam && eventStartTimeMs == eventStartTimeMsParam && eventEndTimeMs == eventEndTimeMsParam");
				q.declareParameters("Long applicationIdParam, Long eventStartTimeMsParam, Long eventEndTimeMsParam");
				return (List<ContentStatByApplicationSummary>) q.execute(
						applicationId, eventStartTimeMs, eventEndTimeMs);
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

	List<ContentStatByApplicationSummary> getSummaryByApplication(
			Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentStatByApplicationSummary.class);
				q.setFilter("applicationId == applicationIdParam");
				q.declareParameters("Long applicationIdParam");
				q.setOrdering("eventEndTimeMs desc");
				return (List<ContentStatByApplicationSummary>) q
						.execute(applicationId);
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

	List<ContentStatByContentGroupSummary> getSummaryByContentGroup(
			Long contentGroupId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentStatByContentGroupSummary.class);
				q.setFilter("contentGroupId == contentGroupIdParam");
				q.declareParameters("Long contentGroupIdParam");
				q.setOrdering("eventEndTimeMs desc");
				return (List<ContentStatByContentGroupSummary>) q
						.execute(contentGroupId);
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

	List<ContentStatByContentSummary> getSummaryByContent(Long contentId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentStatByContentSummary.class);
				q.setFilter("contentId == contentIdParam");
				q.declareParameters("Long contentIdParam");
				q.setOrdering("eventEndTimeMs desc");
				return (List<ContentStatByContentSummary>) q.execute(contentId);
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

	void rollupSummary(Long applicationId, Long eventStartTimeMs,
			Long eventEndTimeMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Looking up application id: " + applicationId
						+ " start time: "
						+ new Date(eventStartTimeMs).toGMTString()
						+ " end time: "
						+ new Date(eventEndTimeMs).toGMTString());
			PersistenceManager pm = null;
			long lApplicationIdCount = 0L;
			Map<Long, Long> lContentGroupCounts = new HashMap<Long, Long>();
			Map<Long, Long> lContentCounts = new HashMap<Long, Long>();

			try {
				{
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(ContentStat.class);
					q.setFilter("applicationId == applicationIdParam && eventTimeMs >= eventTimeMsParam");
					q.declareParameters("Long applicationIdParam, Long eventTimeMsParam");
					q.setOrdering("eventTimeMs desc");
					q.getFetchPlan().setFetchSize(500);
					List<ContentStat> lList = (List<ContentStat>) q.execute(
							applicationId, eventStartTimeMs);

					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Retrieved "
								+ ((lList != null) ? lList.size() : 0)
								+ " content stats");
					for (ContentStat contentStat : lList) {
						if (contentStat.getEventTimeMs() <= eventEndTimeMs) {
							lApplicationIdCount++;
							if (lContentGroupCounts.containsKey(contentStat
									.getContentGroupId())) {
								Long lCount = lContentGroupCounts
										.get(contentStat.getContentGroupId());
								lContentGroupCounts.put(
										contentStat.getContentGroupId(),
										++lCount);
							} else {
								lContentGroupCounts.put(
										contentStat.getContentGroupId(), 1L);
							}
							if (lContentCounts.containsKey(contentStat
									.getContentId())) {
								Long lCount = lContentCounts.get(contentStat
										.getContentId());
								lContentCounts.put(contentStat.getContentId(),
										++lCount);
							} else {
								lContentCounts.put(contentStat.getContentId(),
										1L);
							}

						}
					}
				}
				if (lApplicationIdCount != 0L) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Processing application id: "
								+ applicationId);
					// By application Id
					Query q = pm
							.newQuery(ContentStatByApplicationSummary.class);
					q.setFilter("applicationId == applicationIdParam && eventEndTimeMs == eventEndTimeMsParam");
					q.declareParameters("Long applicationIdParam, Long eventEndTimeMsParam");
					List<ContentStatByApplicationSummary> lList = (List<ContentStatByApplicationSummary>) q
							.execute(applicationId, eventEndTimeMs);
					if (lList != null && (!lList.isEmpty())) {
						// update
						ContentStatByApplicationSummary lSummary = lList.get(0);
						lSummary.setCount(lApplicationIdCount);

					} else {
						// create new
						ContentStatByApplicationSummary lSummary = new ContentStatByApplicationSummary();
						lSummary.setApplicationId(applicationId);
						lSummary.setCount(lApplicationIdCount);
						// timestamp
						lSummary.setEventStartTimeMs(eventStartTimeMs);
						lSummary.setEventEndTimeMs(eventEndTimeMs);
						lSummary.setEventTimeZoneOffsetMs((long) TimeZone
								.getDefault().getRawOffset());
						pm.makePersistent(lSummary);
					}

				} else {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Skipping application id: " + applicationId);
				}
				{
					// By content group Id
					for (Map.Entry<Long, Long> entry : lContentGroupCounts
							.entrySet()) {
						if (entry.getValue() != 0L) {
							if (LOGGER.isLoggable(Level.INFO))
								LOGGER.info("Processing content group id: "
										+ entry.getKey());
							Query q = pm
									.newQuery(ContentStatByContentGroupSummary.class);
							q.setFilter("contentGroupId == contentGroupIdParam && eventEndTimeMs == eventEndTimeMsParam");
							q.declareParameters("Long contentGroupIdParam, Long eventEndTimeMsParam");
							List<ContentStatByContentGroupSummary> lList = (List<ContentStatByContentGroupSummary>) q
									.execute(entry.getKey(), eventEndTimeMs);
							if (lList != null && (!lList.isEmpty())) {
								// update
								ContentStatByContentGroupSummary lSummary = lList
										.get(0);
								lSummary.setCount(entry.getValue());

							} else {
								// create new
								ContentStatByContentGroupSummary lSummary = new ContentStatByContentGroupSummary();
								lSummary.setContentGroupId(entry.getKey());
								lSummary.setCount(entry.getValue());
								lSummary.setEventStartTimeMs(eventStartTimeMs);
								lSummary.setEventEndTimeMs(eventEndTimeMs);
								lSummary.setEventTimeZoneOffsetMs((long) TimeZone
										.getDefault().getRawOffset());
								pm.makePersistent(lSummary);
							}
						} else {
							if (LOGGER.isLoggable(Level.INFO))
								LOGGER.info("Skipping content group id: "
										+ entry.getKey());
						}
					}

				}
				{
					// By content Id
					for (Map.Entry<Long, Long> entry : lContentCounts
							.entrySet()) {
						if (entry.getValue() != 0L) {
							if (LOGGER.isLoggable(Level.INFO))
								LOGGER.info("Processing content id: "
										+ entry.getKey());
							Query q = pm
									.newQuery(ContentStatByContentSummary.class);
							q.setFilter("contentId == contentIdParam && eventEndTimeMs == eventEndTimeMsParam");
							q.declareParameters("Long contentIdParam, Long eventEndTimeMsParam");
							List<ContentStatByContentSummary> lList = (List<ContentStatByContentSummary>) q
									.execute(entry.getKey(), eventEndTimeMs);
							if (lList != null && (!lList.isEmpty())) {
								// update
								ContentStatByContentSummary lSummary = lList
										.get(0);
								lSummary.setCount(entry.getValue());

							} else {
								// create new
								ContentStatByContentSummary lSummary = new ContentStatByContentSummary();
								lSummary.setContentId(entry.getKey());
								lSummary.setCount(entry.getValue());
								lSummary.setEventStartTimeMs(eventStartTimeMs);
								lSummary.setEventEndTimeMs(eventEndTimeMs);
								lSummary.setEventTimeZoneOffsetMs((long) TimeZone
										.getDefault().getRawOffset());
								pm.makePersistent(lSummary);
							}
						} else {
							if (LOGGER.isLoggable(Level.INFO))
								LOGGER.info("Skipping content  id: "
										+ entry.getKey());
						}
					}

				}

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

	/**
	 * 
	 * @param pContentStats
	 */
	void rollupSummaryRealTime(List<ContentStat> pContentStats) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;
			Map<Integer, Long> lApplicationCountsByDay = new HashMap<Integer, Long>();
			Map<Integer, HashMap<Long, Long>> lContentGroupCountsByDay = new HashMap<Integer, HashMap<Long, Long>>();
			Map<Integer, HashMap<Long, Long>> lContentCountsByDay = new HashMap<Integer, HashMap<Long, Long>>();
			Long lApplicationId = null;

			try {

				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Received "
							+ ((pContentStats != null) ? pContentStats.size()
									: 0) + " content stats");
				for (ContentStat contentStat : pContentStats) {
					// all content stats are for the same application
					if (lApplicationId == null) {
						lApplicationId = contentStat.getApplicationId();
					}
					int lDayBucket = Utils
							.getDaysBetweenTodayAndTimestamp(
									contentStat.getEventTimeMs(),
									TimeZone.getTimeZone("UTC"));
					// if it does not contain; initialize
					if (!lApplicationCountsByDay.containsKey(lDayBucket)) {
						lApplicationCountsByDay.put(
								Integer.valueOf(lDayBucket), 0L);
					}
					if (!lContentGroupCountsByDay.containsKey(lDayBucket)) {
						lContentGroupCountsByDay.put(
								Integer.valueOf(lDayBucket),
								new HashMap<Long, Long>());
					}
					if (!lContentCountsByDay.containsKey(lDayBucket)) {
						lContentCountsByDay.put(Integer.valueOf(lDayBucket),
								new HashMap<Long, Long>());
					}

					// get
					Long lApplicationCount = lApplicationCountsByDay
							.get(lDayBucket);
					lApplicationCountsByDay
							.put(lDayBucket, ++lApplicationCount);

					// get the right Map
					Map<Long, Long> lContentGroupCounts = lContentGroupCountsByDay
							.get(lDayBucket);
					// check to see if it exists
					if (lContentGroupCounts.containsKey(contentStat
							.getContentGroupId())) {
						Long lCount = lContentGroupCounts.get(contentStat
								.getContentGroupId());
						lContentGroupCounts.put(
								contentStat.getContentGroupId(), ++lCount);
					} else {
						lContentGroupCounts.put(
								contentStat.getContentGroupId(), 1L);
					}
					// get the right Map
					Map<Long, Long> lContentCounts = lContentCountsByDay
							.get(lDayBucket);
					if (lContentCounts.containsKey(contentStat.getContentId())) {
						Long lCount = lContentCounts.get(contentStat
								.getContentId());
						lContentCounts
								.put(contentStat.getContentId(), ++lCount);
					} else {
						lContentCounts.put(contentStat.getContentId(), 1L);
					}

				}

				// update the datastore
				pm = PMF.get().getPersistenceManager();

				for (Map.Entry<Integer, Long> entry : lApplicationCountsByDay
						.entrySet()) {
					int lDayNumber = entry.getKey();
					long lApplicationIdCount = lApplicationCountsByDay
							.get(lDayNumber);

					// get
					long lEndOfDayMs = Utils.getEndOfDayMinusDays(lDayNumber,
							TimeZone.getTimeZone("UTC"));

					long lStartOfDayMs = Utils.getStartOfDayMinusDays(lDayNumber,
							TimeZone.getTimeZone("UTC"));

					Query q = pm
							.newQuery(ContentStatByApplicationSummary.class);
					q.setFilter("applicationId == applicationIdParam && eventEndTimeMs == eventEndTimeMsParam");
					q.declareParameters("Long applicationIdParam, Long eventEndTimeMsParam");
					List<ContentStatByApplicationSummary> lList = (List<ContentStatByApplicationSummary>) q
							.execute(lApplicationId, lEndOfDayMs);

					if (lList != null && (!lList.isEmpty())) {
						// update
						ContentStatByApplicationSummary lSummary = lList.get(0);
						lSummary.setCount(lApplicationIdCount);

					} else {
						// create new
						ContentStatByApplicationSummary lSummary = new ContentStatByApplicationSummary();
						lSummary.setApplicationId(lApplicationId);
						lSummary.setCount(lApplicationIdCount);
						// timestamp
						lSummary.setEventStartTimeMs(lStartOfDayMs);
						lSummary.setEventEndTimeMs(lEndOfDayMs);
						lSummary.setEventTimeZoneOffsetMs((long) TimeZone
								.getDefault().getRawOffset());
						pm.makePersistent(lSummary);
					}
				}

				{
					// By content group Id
					for (Map.Entry<Integer, HashMap<Long, Long>> entry : lContentGroupCountsByDay
							.entrySet()) {
						HashMap<Long, Long> lContentGroupCountsForADay = entry
								.getValue();

						int lDayNumber = entry.getKey();
						// get
						long lEndOfDayMs = Utils.getEndOfDayMinusDays(lDayNumber,
								TimeZone.getTimeZone("UTC"));

						long lStartOfDayMs = Utils.getStartOfDayMinusDays(
								lDayNumber, TimeZone.getTimeZone("UTC"));

						for (Map.Entry<Long, Long> entry2 : lContentGroupCountsForADay
								.entrySet()) {

							if (entry2.getValue() != 0L) {
								if (LOGGER.isLoggable(Level.INFO))
									LOGGER.info("Processing content group id: "
											+ entry.getKey());
								long lApplicationIdCount = lApplicationCountsByDay
										.get(lDayNumber);

								Query q = pm
										.newQuery(ContentStatByContentGroupSummary.class);
								q.setFilter("contentGroupId == contentGroupIdParam && eventEndTimeMs == eventEndTimeMsParam");
								q.declareParameters("Long contentGroupIdParam, Long eventEndTimeMsParam");
								List<ContentStatByContentGroupSummary> lList = (List<ContentStatByContentGroupSummary>) q
										.execute(entry.getKey(), lEndOfDayMs);
								if (lList != null && (!lList.isEmpty())) {
									// update
									ContentStatByContentGroupSummary lSummary = lList
											.get(0);
									lSummary.setCount(entry2.getValue());

								} else {
									// create new
									ContentStatByContentGroupSummary lSummary = new ContentStatByContentGroupSummary();
									lSummary.setContentGroupId(entry2.getKey());
									lSummary.setCount(entry2.getValue());
									lSummary.setEventStartTimeMs(lStartOfDayMs);
									lSummary.setEventEndTimeMs(lEndOfDayMs);
									lSummary.setEventTimeZoneOffsetMs((long) TimeZone
											.getDefault().getRawOffset());
									pm.makePersistent(lSummary);
								}
							} else {
								if (LOGGER.isLoggable(Level.INFO))
									LOGGER.info("Skipping content group id: "
											+ entry.getKey());
							}
						}
					}

				}
				{
					// By content Id
					for (Map.Entry<Integer, HashMap<Long, Long>> entry : lContentCountsByDay
							.entrySet()) {
						HashMap<Long, Long> lContentCountsForADay = entry
								.getValue();

						int lDayNumber = entry.getKey();
						// get
						long lEndOfDayMs = Utils.getEndOfDayMinusDays(lDayNumber,
								TimeZone.getTimeZone("UTC"));

						long lStartOfDayMs = Utils.getStartOfDayMinusDays(
								lDayNumber, TimeZone.getTimeZone("UTC"));

						for (Map.Entry<Long, Long> entry2 : lContentCountsForADay
								.entrySet()) {
							if (entry2.getValue() != 0L) {
								if (LOGGER.isLoggable(Level.INFO))
									LOGGER.info("Processing content id: "
											+ entry.getKey());
								Query q = pm
										.newQuery(ContentStatByContentSummary.class);
								q.setFilter("contentId == contentIdParam && eventEndTimeMs == eventEndTimeMsParam");
								q.declareParameters("Long contentIdParam, Long eventEndTimeMsParam");
								List<ContentStatByContentSummary> lList = (List<ContentStatByContentSummary>) q
										.execute(entry.getKey(), lEndOfDayMs);
								if (lList != null && (!lList.isEmpty())) {
									// update
									ContentStatByContentSummary lSummary = lList
											.get(0);
									lSummary.setCount(entry2.getValue());

								} else {
									// create new
									ContentStatByContentSummary lSummary = new ContentStatByContentSummary();
									lSummary.setContentId(entry2.getKey());
									lSummary.setCount(entry2.getValue());
									lSummary.setEventStartTimeMs(lStartOfDayMs);
									lSummary.setEventEndTimeMs(lEndOfDayMs);
									lSummary.setEventTimeZoneOffsetMs((long) TimeZone
											.getDefault().getRawOffset());
									pm.makePersistent(lSummary);
								}
							} else {
								if (LOGGER.isLoggable(Level.INFO))
									LOGGER.info("Skipping content  id: "
											+ entry.getKey());
							}
						}

					}
				}

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
