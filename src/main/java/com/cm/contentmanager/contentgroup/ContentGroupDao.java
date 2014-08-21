package com.cm.contentmanager.contentgroup;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
public class ContentGroupDao {
	private static final Logger LOGGER = Logger.getLogger(ContentGroupDao.class
			.getName());

	public ContentGroup saveContentGroup(ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContentGroup");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				return pm.makePersistent(contentGroup);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContentGroup");
		}
	}

	ContentGroup getContentGroup(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroup");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				ContentGroup _contentGroup = pm.getObjectById(
						ContentGroup.class, id);
				return _contentGroup;
			} catch (JDOObjectNotFoundException e) {
				LOGGER.log(Level.WARNING, e.getMessage());
				return null;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroup");
		}
	}

	List<ContentGroup> getContentGroupsByApplicationId(Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContentGroups");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);
				q.setFilter("applicationId == applicationIdParam && deleted == deletedParam && enabled == enabledParam");
				q.declareParameters("Long applicationIdParam, Boolean deletedParam, Boolean enabledParam");
				return (List<ContentGroup>) q.execute(applicationId,
						Boolean.valueOf(false), Boolean.valueOf(true));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContentGroups");
		}
	}

	List<ContentGroup> getContentGroupsByUserId(Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroupsByUserId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);
				q.setFilter("userId == userIdParam && deleted == deletedParam");
				q.declareParameters("Long userIdParam, Boolean deletedParam");
				return (List<ContentGroup>) q.execute(userId,
						new Boolean(false));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroupsByUserId");
		}
	}

	List<ContentGroup> getContentGroupsByAccountId(Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroupsByAccountId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, Boolean deletedParam");
				return (List<ContentGroup>) q.execute(userId,
						new Boolean(false));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroupsByAccountId");
		}
	}

	void deleteContentGroup(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContentGroup");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				ContentGroup _contentGroup = pm.getObjectById(
						ContentGroup.class, id);
				if (_contentGroup != null) {
					_contentGroup.setDeleted(true);
					_contentGroup.setTimeUpdatedMs(timeUpdatedMs);
					_contentGroup
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " content group marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, id + "  AD GROUP NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContentGroup");
		}
	}

	void restoreContentGroup(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreContentGroup");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				ContentGroup _contentGroup = pm.getObjectById(
						ContentGroup.class, id);
				if (_contentGroup != null) {
					_contentGroup.setDeleted(false);
					_contentGroup.setTimeUpdatedMs(timeUpdatedMs);
					_contentGroup
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " content group restored");
				} else {
					LOGGER.log(Level.WARNING, id + "  AD GROUP NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restoreContentGroup");
		}
	}

	void updateContentGroup(ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateContentGroup");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				ContentGroup _contentGroup = pm.getObjectById(
						ContentGroup.class, contentGroup.getId());
				// do not update applicationId
				_contentGroup.setDescription(contentGroup.getDescription());
				_contentGroup.setName(contentGroup.getName());
				_contentGroup.setStartDateIso8601(contentGroup
						.getStartDateIso8601());
				_contentGroup.setEndDateIso8601(contentGroup
						.getEndDateIso8601());
				_contentGroup.setStartDateMs(contentGroup.getStartDateMs());
				_contentGroup.setEndDateMs(contentGroup.getEndDateMs());
				_contentGroup.setEnabled(contentGroup.isEnabled());
				_contentGroup.setDeleted(contentGroup.isDeleted());
				// for existing contents
				if (_contentGroup.getTimeCreatedMs() == null) {
					// default it to the update date
					_contentGroup.setTimeCreatedMs(contentGroup
							.getTimeUpdatedMs());
					_contentGroup.setTimeCreatedTimeZoneOffsetMs(contentGroup
							.getTimeUpdatedTimeZoneOffsetMs());
				}
				_contentGroup.setTimeUpdatedMs(contentGroup.getTimeUpdatedMs());
				_contentGroup.setTimeUpdatedTimeZoneOffsetMs(contentGroup
						.getTimeUpdatedTimeZoneOffsetMs());

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateContentGroup");
		}
	}
}
