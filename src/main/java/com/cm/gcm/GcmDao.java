package com.cm.gcm;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
class GcmDao {
	private static final Logger LOGGER = Logger.getLogger(GcmDao.class
			.getName());

	List<GcmRegistrationRequest> getGcmRegistrationRequests(String trackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getGcmRegistrationRequests");

			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(GcmRegistrationRequest.class);
				q.setFilter("trackingId == trackingIdParam");
				q.declareParameters("String trackingIdParam");
				return (List<GcmRegistrationRequest>) q.execute(trackingId);
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getGcmRegistrationRequests");
		}
	}

	void save(GcmRegistrationRequest gcmRegistrationRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(gcmRegistrationRequest);

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

	GcmRegistrationRequest getGcmRegistrationRequest(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getGcmRegistrationRequest");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(GcmRegistrationRequest.class);
				q.setFilter("gcmId == gcmIdParam");
				q.declareParameters("String gcmIdParam");
				Object[] _array = new Object[1];
				_array[0] = gcmId;
				List<GcmRegistrationRequest> lList = (List<GcmRegistrationRequest>) q
						.executeWithArray(_array);
				if (lList != null && lList.size() > 0)
					return lList.get(0);
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
				LOGGER.info("Exiting getGcmRegistrationRequest");
		}
	}

	List<GcmRegistrationRequest> getGcmRegistrationRequestByApplicationId(
			String applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getGcmRegistrationRequestByApplicationId");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(GcmRegistrationRequest.class);
				q.setFilter("applicationId == applicationIdParam && deleted == deletedParam");
				q.declareParameters("String trackingIdParam, Boolean deletedParam");
				return (List<GcmRegistrationRequest>) q.execute(applicationId,
						new Boolean(false));

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
				LOGGER.info("Exiting getGcmRegistrationRequestByApplicationId");
		}
	}

	void deleteGcmRegistrationRequest(String gcmId, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteGcmRegistrationRequest");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				GcmRegistrationRequest lRequest = pm.getObjectById(
						GcmRegistrationRequest.class, gcmId);
				if (lRequest != null) {
					lRequest.setDeleted(true);
					lRequest.setTimeUpdatedMs(timeUpdatedMs);
					lRequest.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(gcmId + " application marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, gcmId
							+ "  GcmRegistrationRequest NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteGcmRegistrationRequest");
		}
	}

	void updateApplication(GcmRegistrationRequest gcmRegistrationRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateApplication");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				GcmRegistrationRequest lGcmRegistrationRequest = pm
						.getObjectById(GcmRegistrationRequest.class,
								gcmRegistrationRequest.getId());
				// just update the timestamps
				lGcmRegistrationRequest.setTimeUpdatedMs(gcmRegistrationRequest
						.getTimeUpdatedMs());
				lGcmRegistrationRequest
						.setTimeUpdatedTimeZoneOffsetMs(gcmRegistrationRequest
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
