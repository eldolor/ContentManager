package com.cm.contentmanager.application;

import java.util.List;
import java.util.TimeZone;
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

	List<Application> search(String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("deleted == deletedParam");
				q.declareParameters("Boolean deletedParam");
				q.setOrdering("timeUpdatedMs desc");
				return (List<Application>) q.execute(new Boolean(false));
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

	List<Application> searchByAccountId(Long accountId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, Boolean deletedParam");
				q.setOrdering("userId desc, deletedOnPlanDowngrade , timeUpdatedMs desc");
				return (List<Application>) q.execute(accountId, new Boolean(
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

	List<Application> searchByUserId(Long userId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("userId == userIdParam && deleted == deletedParam");
				q.declareParameters("Long userIdParam, Boolean deletedParam");
				q.setOrdering("deletedOnPlanDowngrade , timeUpdatedMs desc");
				return (List<Application>) q
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

	Application save(Application application) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
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
				LOGGER.info("Exiting save");
		}
	}

	Application get(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
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
				LOGGER.info("Exiting get");
		}
	}

	Application get(String trackingId, boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				if (includeDeleted) {
					q.setFilter("trackingId == trackingIdParam");
					q.declareParameters("String trackingIdParam");
					List<Application> lApplications = (List<Application>) q
							.execute(trackingId);
					if (lApplications != null && (!lApplications.isEmpty()))
						return lApplications.get(0);
					else
						return null;

				} else {
					q.setFilter("trackingId == trackingIdParam && deleted == deletedParam");
					q.declareParameters("String trackingIdParam, Boolean deletedParam");
					List<Application> lApplications = (List<Application>) q
							.execute(trackingId, new Boolean(false));
					if (lApplications != null && (!lApplications.isEmpty()))
						return lApplications.get(0);
					else
						return null;
				}

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
				LOGGER.info("Exiting get");
		}
	}

	List<Application> get() {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("deleted == deletedParam");
				q.declareParameters("Boolean deletedParam");
				q.setOrdering("timeUpdatedMs desc");
				return (List<Application>) q.execute(new Boolean(false));
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
				q.setOrdering("deletedOnPlanDowngrade , timeUpdatedMs desc");
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

	List<Application> getApplicationsByUserId(Long userId,
			boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByUserId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				if (includeDeleted) {
					q.setFilter("userId == userIdParam");
					q.declareParameters("Long userIdParam");
					q.setOrdering("timeUpdatedMs desc");
					return (List<Application>) q.execute(userId);

				} else {
					q.setFilter("userId == userIdParam && deleted == deletedParam");
					q.declareParameters("Long userIdParam, Boolean deletedParam");
					q.setOrdering("timeUpdatedMs desc");
					return (List<Application>) q.execute(userId,
							Boolean.valueOf(false));
				}
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
				q.setOrdering("userId desc, deletedOnPlanDowngrade , timeUpdatedMs desc");
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
					q.setOrdering("timeUpdatedMs desc");
					return (List<Application>) q.execute(accountId);

				} else {
					q.setFilter("accountId == accountIdParam && deleted == deletedParam");
					q.declareParameters("Long accountIdParam, Boolean deletedParam");
					q.setOrdering("timeUpdatedMs desc");
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

	List<Application> getDeletedApplicationsOnPlanDowngradeByUserId(Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("userId == userIdParam && deletedOnPlanDowngrade == deletedOnPlanDowngradeParam");
				q.declareParameters("Long userIdParam, Boolean deletedOnPlanDowngradeParam");
				q.setOrdering("timeUpdatedMs desc");
				return (List<Application>) q.execute(userId,
						Boolean.valueOf(true));
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

	List<Application> getDeletedApplicationsOnPlanDowngradeByAccountId(
			Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("accountId == accountIdParam && deletedOnPlanDowngrade == deletedOnPlanDowngradeParam");
				q.declareParameters("Long accountIdParam, Boolean deletedOnPlanDowngradeParam");
				q.setOrdering("timeUpdatedMs desc");
				return (List<Application>) q.execute(accountId,
						Boolean.valueOf(true));
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

	void delete(Long id, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
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
				LOGGER.info("Exiting delete");
		}
	}

	void deleteOnPlanDowngrade(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application lApplication = pm.getObjectById(Application.class,
						id);
				if (lApplication != null) {
					// Do not mark apps as deleted, The apps will be displayed
					// greyed out
					lApplication.setDeletedOnPlanDowngrade(true);
					lApplication.setTimeUpdatedMs(System.currentTimeMillis());
					lApplication.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
							.getDefault().getRawOffset());
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
				LOGGER.info("Exiting delete");
		}
	}

	void restoreOnPlanUpgrade(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application lApplication = pm.getObjectById(Application.class,
						id);
				if (lApplication != null) {
					lApplication.setDeleted(false);
					lApplication.setDeletedOnPlanDowngrade(false);
					lApplication.setTimeUpdatedMs(System.currentTimeMillis());
					lApplication.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
							.getDefault().getRawOffset());
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
				LOGGER.info("Exiting restore");
		}
	}

	void update(Application pApplication) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
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
				lApplication.setChangesStaged(pApplication.getChangesStaged());
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
				LOGGER.info("Exiting update");
		}
	}

	void updateChangesStaged(Long pApplicationId, boolean pChangesStaged) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateChangesStaged");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Application lApplication = pm.getObjectById(Application.class,
						pApplicationId);
				// do not update trackingId
				lApplication.setChangesStaged(pChangesStaged);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateChangesStaged");
		}
	}
}
