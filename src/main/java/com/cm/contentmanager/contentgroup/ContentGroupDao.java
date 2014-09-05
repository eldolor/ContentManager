package com.cm.contentmanager.contentgroup;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.content.Content;
import com.cm.util.PMF;

@Component
public class ContentGroupDao {
	private static final Logger LOGGER = Logger.getLogger(ContentGroupDao.class
			.getName());

	List<ContentGroup> search(String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);
				q.setFilter("deleted == deletedParam");
				q.declareParameters("Boolean deletedParam");
				q.setOrdering("timeUpdatedMs desc");
				return (List<ContentGroup>) q.execute(new Boolean(false));
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

	List<ContentGroup> searchByAccountId(Long accountId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, Boolean deletedParam");
				q.setOrdering("userId desc, timeUpdatedMs desc");
				return (List<ContentGroup>) q.execute(accountId, new Boolean(
						false));
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

	List<ContentGroup> searchByUserId(Long userId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);
				q.setFilter("userId == userIdParam && deleted == deletedParam");
				q.declareParameters("Long userIdParam, Boolean deletedParam");
				q.setOrdering("timeUpdatedMs desc");
				return (List<ContentGroup>) q
						.execute(userId, new Boolean(false));
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
	public ContentGroup save(ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
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
				LOGGER.info("Exiting save");
		}
	}

	ContentGroup get(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
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

	List<ContentGroup> get(Long applicationId, boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(ContentGroup.class);

				if (includeDeleted) {
					q.setFilter("applicationId == applicationIdParam");
					q.declareParameters("long applicationIdParam");
					q.setOrdering("timeUpdatedMs desc");
					return (List<ContentGroup>) q.execute(applicationId);

				} else {
					q.setFilter("applicationId == applicationIdParam && deleted == deletedParam");
					q.declareParameters("Long applicationIdParam, Boolean deletedParam");
					q.setOrdering("timeUpdatedMs desc");
					return (List<ContentGroup>) q.execute(applicationId,
							Boolean.valueOf(false));
				}

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	void delete(Long id, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
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
				LOGGER.info("Exiting delete");
		}
	}

	void restore(Long id, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restore");
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
				LOGGER.info("Exiting restore");
		}
	}

	void update(ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
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
				LOGGER.info("Exiting update");
		}
	}
}
