package com.cm.contentmanager.content;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
public class ContentDao {
	private static final Logger LOGGER = Logger.getLogger(ContentDao.class
			.getName());

	public void saveContent(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContent");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(content);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContent");
		}
	}

	public Content getContent(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Content _content = pm.detachCopy(pm.getObjectById(
						Content.class, id));
				return _content;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	public List<Content> getAllContent(Long contentGroupId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("contentGroupId == contentGroupIdParam && deleted == deletedParam");
				q.declareParameters("Long contentGroupIdParam, Boolean deletedParam");
				return (List<Content>) q.execute(contentGroupId, new Boolean(
						false));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public List<Content> getAllContent(Long contentGroupId, String type) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("contentGroupId: " + contentGroupId + " Type: "
						+ type);
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("contentGroupId == contentGroupIdParam && type == typeParam && deleted == deletedParam && enabled == enabledParam");
				q.declareParameters("Long contentGroupIdParam, String typeParam, Boolean deletedParam, Boolean enabledParam");
				q.setOrdering("enabled desc");
				Object[] _array = new Object[4];
				_array[0] = contentGroupId;
				_array[1] = type;
				_array[2] = new Boolean(false);
				_array[3] = new Boolean(true);
				return (List<Content>) q.executeWithArray(_array);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public List<Content> getAllContent() {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("deleted == deletedParam && enabled == enabledParam");
				q.declareParameters("Boolean deletedParam, Boolean enabledParam");
				q.setOrdering("enabled desc");
				Object[] _array = new Object[2];
				_array[0] = new Boolean(false);
				_array[1] = new Boolean(true);
				return (List<Content>) q.executeWithArray(_array);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public void deleteContent(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Content _content = pm.getObjectById(Content.class, id);
				if (_content != null) {
					_content.setDeleted(true);
					_content.setTimeUpdatedMs(timeUpdatedMs);
					_content.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " content marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, id + " AD NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContent");
		}
	}

	public void restoreContent(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Content _content = pm.getObjectById(Content.class, id);
				if (_content != null) {
					_content.setDeleted(false);
					_content.setTimeUpdatedMs(timeUpdatedMs);
					_content.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " content restored");
				} else {
					LOGGER.log(Level.WARNING, id + " AD NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restoreContent");
		}
	}

	public void deleteAllContent(Long contentGroupId, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteAllContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("contentGroupId == contentGroupIdParam && deleted == deletedParam");
				q.declareParameters("Long contentGroupIdParam, Boolean deletedParam");
				pm.deletePersistentAll();
				List<Content> contents = (List<Content>) q.execute(
						contentGroupId, new Boolean(false));
				for (Content content : contents) {
					content.setDeleted(true);
					content.setTimeUpdatedMs(timeUpdatedMs);
					content.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(content.getId() + " marked for deletion");
				}

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteAllContent");
		}
	}

	public void updateContent(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateContent");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Content _content = pm.getObjectById(Content.class,
						content.getId());
				_content.setContentGroupId(content.getContentGroupId());
				_content.setStartDateIso8601(content.getStartDateIso8601());
				_content.setEndDateIso8601(content.getEndDateIso8601());
				_content.setStartDateMs(content.getStartDateMs());
				_content.setEndDateMs(content.getEndDateMs());
				_content.setDeleted(content.isDeleted());
				_content.setEnabled(content.isEnabled());

				_content.setName(content.getName());
				_content.setDescription(content.getDescription());

				// for existing contents
				if (_content.getTimeCreatedMs() == null) {
					// default it to the start date
					_content.setTimeCreatedMs(content.getStartDateMs());
					_content.setTimeCreatedTimeZoneOffsetMs(content
							.getTimeUpdatedTimeZoneOffsetMs());
				}
				_content.setTimeUpdatedMs(content.getTimeUpdatedMs());
				_content.setTimeUpdatedTimeZoneOffsetMs(content
						.getTimeUpdatedTimeZoneOffsetMs());

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateContent");
		}
	}

}
