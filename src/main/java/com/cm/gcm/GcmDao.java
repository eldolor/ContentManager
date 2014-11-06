package com.cm.gcm;

import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

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
				q.setFilter("trackingId == trackingIdParam && gcmDeviceHasMultipleRegistrations == gcmDeviceHasMultipleRegistrationsParam && gcmDeviceNotRegistered == gcmDeviceNotRegisteredParam && deleted == deletedParam && deprecated == deprecatedParam");
				q.declareParameters("String trackingIdParam, Boolean gcmDeviceHasMultipleRegistrationsParam, Boolean gcmDeviceNotRegisteredParam, Boolean deletedParam, Boolean deprecatedParam");
				Object[] _array = new Object[5];
				_array[0] = trackingId;
				_array[1] = Boolean.valueOf(false);
				_array[2] = Boolean.valueOf(false);
				_array[3] = Boolean.valueOf(false);
				_array[4] = Boolean.valueOf(false);
				return (List<GcmRegistrationRequest>) q.executeWithArray(_array);
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

	void updateDeviceNotRegisteredWithGcm(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateDeviceNotRegisteredWithGcm");
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
				if (lList != null && lList.size() > 0) {
					GcmRegistrationRequest lGcmRegistrationRequest = lList
							.get(0);
					lGcmRegistrationRequest.setGcmDeviceNotRegistered(true);

					lGcmRegistrationRequest.setTimeUpdatedMs(System
							.currentTimeMillis());
					lGcmRegistrationRequest
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getRawOffset());
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

	void deprecate(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deprecate");
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
				if (lList != null && lList.size() > 0) {
					GcmRegistrationRequest lGcmRegistrationRequest = lList
							.get(0);
					lGcmRegistrationRequest.setDeprecated(true);

					lGcmRegistrationRequest.setTimeUpdatedMs(System
							.currentTimeMillis());
					lGcmRegistrationRequest
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getRawOffset());
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
				LOGGER.info("Exiting deprecate");
		}
	}

	void unRegister(String gcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering unRegister");
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
				if (lList != null && lList.size() > 0) {
					GcmRegistrationRequest lGcmRegistrationRequest = lList
							.get(0);

					lGcmRegistrationRequest.setDeleted(true);

					lGcmRegistrationRequest.setTimeUpdatedMs(System
							.currentTimeMillis());
					lGcmRegistrationRequest
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getRawOffset());
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
				LOGGER.info("Exiting unRegister");
		}
	}

	void updateTimestamp(GcmRegistrationRequest gcmRegistrationRequest) {
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

	void updateWithCanonicalRegId(String gcmRegistrationId,
			String canonicalGcmRegistrationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateWithCanonicalRegId");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(GcmRegistrationRequest.class);
				q.setFilter("gcmId == gcmIdParam");
				q.declareParameters("String gcmIdParam");
				Object[] _array = new Object[1];
				_array[0] = gcmRegistrationId;
				List<GcmRegistrationRequest> lList = (List<GcmRegistrationRequest>) q
						.executeWithArray(_array);
				if (lList != null && lList.size() > 0) {
					GcmRegistrationRequest lGcmRegistrationRequest = lList
							.get(0);
					// update
					lGcmRegistrationRequest
							.setGcmDeviceHasMultipleRegistrations(true);
					lGcmRegistrationRequest
							.setCanonicalGcmId(canonicalGcmRegistrationId);

					lGcmRegistrationRequest.setTimeUpdatedMs(System
							.currentTimeMillis());
					lGcmRegistrationRequest
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getRawOffset());

				} else {
					LOGGER.log(Level.SEVERE, "Not found " + gcmRegistrationId);
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateWithCanonicalRegId");
		}

	}
}
