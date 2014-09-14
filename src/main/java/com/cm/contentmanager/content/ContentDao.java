package com.cm.contentmanager.content;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.Anglicizer;
import com.cm.util.PMF;

@Component
class ContentDao {
	private static final Logger LOGGER = Logger.getLogger(ContentDao.class
			.getName());

	List<Content> search(String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("nameIdx >= nameParam1 && nameIdx < nameParam2 && deleted == deletedParam");
				q.declareParameters("String nameParam1, String nameParam2, Boolean deletedParam");
				q.setOrdering("nameIdx, timeUpdatedMs desc");
				String lNameParam1 = Anglicizer.anglicize(searchTerm.trim());
				Object[] _array = new Object[3];
				_array[0] = lNameParam1;
				_array[1] = lNameParam1 + "\ufffd";
				_array[2] = Boolean.valueOf(false);
				return (List<Content>) q.executeWithArray(_array);
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

	List<Content> searchByAccountId(Long accountId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("accountId == accountIdParam && nameIdx >= nameParam1 && nameIdx < nameParam2 && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, String nameParam1, String nameParam2, Boolean deletedParam");
				q.setOrdering("nameIdx, timeUpdatedMs desc");
				String lNameParam1 = Anglicizer.anglicize(searchTerm.trim());
				Object[] _array = new Object[4];
				_array[0] = accountId;
				_array[1] = lNameParam1;
				_array[2] = lNameParam1 + "\ufffd";
				_array[3] = Boolean.valueOf(false);
				return (List<Content>) q.executeWithArray(_array);
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

	List<Content> searchByUserId(Long userId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("userId == userIdParam && nameIdx >= nameParam1 && nameIdx < nameParam2 && deleted == deletedParam");
				q.declareParameters("Long userIdParam, String nameParam1, String nameParam2, Boolean deletedParam");
				q.setOrdering("nameIdx, timeUpdatedMs desc");
				String lNameParam1 = Anglicizer.anglicize(searchTerm.trim());
				Object[] _array = new Object[4];
				_array[0] = userId;
				_array[1] = lNameParam1;
				_array[2] = lNameParam1 + "\ufffd";
				_array[3] = Boolean.valueOf(false);
				return (List<Content>) q.executeWithArray(_array);
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

	Content save(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				content.setNameIdx(content.getName().toLowerCase());
				return pm.makePersistent(content);

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

	Content get(String uri) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("uri == uriParam");
				q.declareParameters("Long uriParam");
				Object[] _array = new Object[1];
				_array[0] = uri;
				List<Content> lList = (List<Content>) q
						.executeWithArray(_array);
				if (lList != null && (!lList.isEmpty()))
					return lList.get(0);
				else
					return null;

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

	Content get(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
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
				LOGGER.info("Exiting get");
		}
	}

	List<Content> get(Long applicationId, Long contentGroupId, boolean deleted,
			boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("applicationId == applicationIdParam && contentGroupId == contentGroupIdParam && deleted == deletedParam && enabled == enabledParam");
				q.declareParameters("Long applicationIdParam, Long contentGroupIdParam, Boolean deletedParam, Boolean enabledParam");
				q.setOrdering("timeUpdatedMs desc");
				Object[] _array = new Object[4];
				_array[0] = applicationId;
				_array[1] = contentGroupId;
				_array[2] = Boolean.valueOf(deleted);
				_array[3] = Boolean.valueOf(enabled);
				return (List<Content>) q.executeWithArray(_array);
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

	List<Content> get(Long applicationId, Long contentGroupId, boolean deleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("applicationId == applicationIdParam && contentGroupId == contentGroupIdParam && deleted == deletedParam");
				q.declareParameters("Long applicationIdParam, Long contentGroupIdParam, Boolean deletedParam");
				q.setOrdering("timeUpdatedMs desc");
				Object[] _array = new Object[3];
				_array[0] = applicationId;
				_array[1] = contentGroupId;
				_array[2] = Boolean.valueOf(deleted);
				return (List<Content>) q.executeWithArray(_array);
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

	List<Content> get(Long applicationId, Long contentGroupId, String type,
			boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("contentGroupId: " + contentGroupId + " Type: "
						+ type);
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("applicationId == applicationIdParam && contentGroupId == contentGroupIdParam && type == typeParam && deleted == deletedParam && enabled == enabledParam");
				q.declareParameters("Long applicationIdParam, Long contentGroupIdParam, String typeParam, Boolean deletedParam, Boolean enabledParam");
				q.setOrdering("enabled desc");
				Object[] _array = new Object[4];
				_array[0] = applicationId;
				_array[1] = contentGroupId;
				_array[2] = type;
				_array[3] = Boolean.valueOf(deleted);
				_array[4] = Boolean.valueOf(enabled);
				return (List<Content>) q.executeWithArray(_array);

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

	List<Content> get(boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("deleted == deletedParam && enabled == enabledParam");
				q.declareParameters("Boolean deletedParam, Boolean enabledParam");
				q.setOrdering("enabled desc");
				Object[] _array = new Object[2];
				_array[0] = Boolean.valueOf(deleted);
				_array[1] = Boolean.valueOf(enabled);
				return (List<Content>) q.executeWithArray(_array);

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

	List<Content> get(Long applicationId, boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				if (includeDeleted) {
					q.setFilter("applicationId == applicationIdParam");
					q.declareParameters("Long applicationIdParam");
					q.setOrdering("timeUpdatedMs desc");
					return (List<Content>) q.execute(applicationId);

				} else {
					q.setFilter("applicationId == applicationIdParam && deleted == deletedParam");
					q.declareParameters("Long applicationIdParam, Boolean deletedParam");
					q.setOrdering("timeUpdatedMs desc");
					return (List<Content>) q.execute(applicationId,
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
				LOGGER.info("Exiting restore");
		}
	}

	void delete(Long applicationId, Long contentGroupId, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Content.class);
				q.setFilter("applicationId == applicationIdParam && contentGroupId == contentGroupIdParam && deleted == deletedParam");
				q.declareParameters("Long applicationIdParam, Long contentGroupIdParam, Boolean deletedParam");
				pm.deletePersistentAll();
				List<Content> contents = (List<Content>) q.execute(
						applicationId, contentGroupId, new Boolean(false));
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
				LOGGER.info("Exiting delete");
		}
	}

	void update(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Content _content = pm.getObjectById(Content.class,
						content.getId());
				// do not set applicationId or contentGroupId on update

				_content.setStartDateIso8601(content.getStartDateIso8601());
				_content.setEndDateIso8601(content.getEndDateIso8601());
				_content.setStartDateMs(content.getStartDateMs());
				_content.setEndDateMs(content.getEndDateMs());
				_content.setDeleted(content.isDeleted());
				_content.setEnabled(content.isEnabled());

				_content.setName(content.getName());
				_content.setNameIdx(content.getName().toLowerCase());
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

				_content.setSizeInBytes(content.getSizeInBytes());
				_content.setUri(content.getUri());
				_content.setType(content.getType());
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

	void updateContentSize(Long id, Long size) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateContentSize");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Content _content = pm.getObjectById(Content.class, id);
				// only the size
				_content.setSizeInBytes(size);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Updating content size to " + size);
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateContentSize");
		}
	}

}
