package com.cm.contentmanager.application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
class ApplicationDao {
	private static final Logger LOGGER = Logger.getLogger(ApplicationDao.class
			.getName());

	Application saveApplication(Application application) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveApplication");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				return pm.makePersistent(application);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveApplication");
		}
	}

	Application getApplication(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplication");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application _application = pm.getObjectById(Application.class,
						id);
				return _application;
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
				LOGGER.info("Exiting getApplication");
		}
	}

	Application getApplicationByTrackingId(String trackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationByTrackingId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("trackingId == trackingIdParam && deleted == deletedParam");
				q.declareParameters("String trackingIdParam, Boolean deletedParam");
				List<Application> lApplications = (List<Application>) q
						.execute(trackingId, new Boolean(false));
				if (lApplications != null && (!lApplications.isEmpty()))
					return lApplications.get(0);
				else
					return null;

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
				LOGGER.info("Exiting getApplicationByTrackingId");
		}
	}

	List<Application> getAllApplications() {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllApplications");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("deleted == deletedParam");
				q.declareParameters("Boolean deletedParam");
				return (List<Application>) q.execute(new Boolean(false));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllApplications");
		}
	}

	List<Application> getApplicationsByUserId(Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByUserId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("userId == userIdParam && deleted == deletedParam");
				q.declareParameters("Long userIdParam, Boolean deletedParam");
				return (List<Application>) q
						.execute(userId, new Boolean(false));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByUserId");
		}
	}

	List<Application> getApplicationsByAccountId(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByAccountId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, Boolean deletedParam");
				return (List<Application>) q.execute(accountId, new Boolean(
						false));
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByAccountId");
		}
	}

	List<Application> getApplicationsByAccountId(Long accountId,
			boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByAccountId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				if (includeDeleted) {
					q.setFilter("accountId == accountIdParam");
					q.declareParameters("Long accountIdParam");
					return (List<Application>) q.execute(accountId);

				} else {
					q.setFilter("accountId == accountIdParam && deleted == deletedParam");
					q.declareParameters("Long accountIdParam, Boolean deletedParam");
					return (List<Application>) q.execute(accountId,
							Boolean.valueOf(false));
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByAccountId");
		}
	}

	void deleteApplication(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteApplication");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application lApplication = pm.getObjectById(Application.class,
						id);
				if (lApplication != null) {
					lApplication.setDeleted(true);
					lApplication.setTimeUpdatedMs(timeUpdatedMs);
					lApplication
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " application marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, id + "  APPLICATION NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteApplication");
		}
	}

	void restoreApplication(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreApplication");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application lContentGroup = pm.getObjectById(Application.class,
						id);
				if (lContentGroup != null) {
					lContentGroup.setDeleted(false);
					lContentGroup.setTimeUpdatedMs(timeUpdatedMs);
					lContentGroup
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " application restored");
				} else {
					LOGGER.log(Level.WARNING, id + "  APPLICATION NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restoreApplication");
		}
	}

	void updateApplication(Application pApplication) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateApplication");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application lApplication = pm.getObjectById(Application.class,
						pApplication.getId());
				// do not update trackingId

				lApplication.setDescription(pApplication.getDescription());
				lApplication.setName(pApplication.getName());
				lApplication.setUpdateOverWifiOnly(pApplication
						.isUpdateOverWifiOnly());
				lApplication.setEnabled(pApplication.isEnabled());
				lApplication.setDeleted(pApplication.isDeleted());
				// for existing contents
				if (lApplication.getTimeCreatedMs() == null) {
					// default it to the update date
					lApplication.setTimeCreatedMs(pApplication
							.getTimeUpdatedMs());
					lApplication.setTimeCreatedTimeZoneOffsetMs(pApplication
							.getTimeUpdatedTimeZoneOffsetMs());
				}
				lApplication.setTimeUpdatedMs(pApplication.getTimeUpdatedMs());
				lApplication.setTimeUpdatedTimeZoneOffsetMs(pApplication
						.getTimeUpdatedTimeZoneOffsetMs());

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateApplication");
		}
	}
}
