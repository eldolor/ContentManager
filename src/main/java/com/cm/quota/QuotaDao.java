package com.cm.quota;

import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.config.CanonicalPlan;
import com.cm.contentmanager.application.Application;
import com.cm.util.PMF;
import com.cm.util.Utils;

@Component
class QuotaDao {
	private static final Logger LOGGER = Logger.getLogger(QuotaDao.class
			.getName());

	Quota get(Long accountId) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Quota.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("Long accountIdParam");
			List<Quota> lQuotas = (List<Quota>) q.execute(accountId);
			Quota lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
			}
			return lQuota;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	ApplicationQuotaUsed getApplicationQuotaUsed(Long accountId) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(ApplicationQuotaUsed.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("Long accountIdParam");
			List<ApplicationQuotaUsed> lQuotas = (List<ApplicationQuotaUsed>) q
					.execute(accountId);
			ApplicationQuotaUsed lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
			}
			return lQuota;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	BandwidthQuotaUsed getBandwidthQuotaUsed(Long accountId) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(BandwidthQuotaUsed.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("Long accountIdParam");
			List<BandwidthQuotaUsed> lQuotas = (List<BandwidthQuotaUsed>) q
					.execute(accountId);
			BandwidthQuotaUsed lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
			}
			return lQuota;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	StorageQuotaUsed getStorageQuotaUsed(Long accountId, Long applicationId) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(StorageQuotaUsed.class);
			q.setFilter("accountId == accountIdParam && applicationId == applicationIdParam");
			q.declareParameters("Long accountIdParam, Long applicationIdParam");
			List<StorageQuotaUsed> lQuotas = (List<StorageQuotaUsed>) q
					.execute(accountId, applicationId);
			StorageQuotaUsed lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
			}
			return lQuota;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	List<StorageQuotaUsed> getStorageQuotaUsed(Long accountId) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(StorageQuotaUsed.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("Long accountIdParam");
			return (List<StorageQuotaUsed>) q.execute(accountId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	void create(Quota quota) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(quota);

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void update(Quota pQuota) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Quota lQuota = pm.getObjectById(Quota.class, pQuota.getId());
			lQuota.setBandwidthLimitInBytes(pQuota.getBandwidthLimitInBytes());
			lQuota.setStorageLimitInBytes(pQuota.getStorageLimitInBytes());
			lQuota.setTimeUpdatedMs(pQuota.getTimeUpdatedMs());
			lQuota.setTimeUpdatedTimeZoneOffsetMs(pQuota
					.getTimeUpdatedTimeZoneOffsetMs());

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void create(ApplicationQuotaUsed quota) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(quota);

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void create(StorageQuotaUsed quota) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(quota);

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void updatePlan(Long accountId, CanonicalPlan canonicalPlan) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Quota.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("Long accountIdParam");
			List<Quota> lQuotas = (List<Quota>) q.execute(accountId);
			Quota lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
				lQuota.setCanonicalPlanId(canonicalPlan.getId());
				lQuota.setBandwidthLimitInBytes(canonicalPlan
						.getBandwidthQuota());
				lQuota.setStorageLimitInBytes(canonicalPlan.getStorageQuota());
				lQuota.setApplicationLimit(canonicalPlan.getApplicationQuota());
				lQuota.setTimeUpdatedMs(System.currentTimeMillis());
				lQuota.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
			}

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void upsertBandwidthUtilization(Application pApplication,
			Long bandwidthUsedInBytes) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(BandwidthQuotaUsed.class);
			q.setFilter("accountId == accountIdParam && applicationId == applicationIdParam && subscriptionPeriodEndMs >= subscriptionPeriodEndMsParam");
			q.declareParameters("Long accountIdParam, Long applicationIdParam, Long subscriptionPeriodEndMsParam");
			q.setOrdering("subscriptionPeriodEndMs");

			long lTimeNow = System.currentTimeMillis();

			Object[] _array = new Object[3];
			_array[0] = pApplication.getAccountId();
			_array[1] = pApplication.getId();
			_array[2] = lTimeNow;

			List<BandwidthQuotaUsed> lList = (List<BandwidthQuotaUsed>) q
					.executeWithArray(_array);

			boolean lFound = false;
			for (BandwidthQuotaUsed bandwidthQuotaUsed : lList) {
				if (lTimeNow >= bandwidthQuotaUsed
						.getSubscriptionPeriodStartMs()
						&& lTimeNow <= bandwidthQuotaUsed
								.getSubscriptionPeriodEndMs()) {

					bandwidthQuotaUsed.setTrackingId(pApplication
							.getTrackingId());
					long lExistingBandwidthUsed = bandwidthQuotaUsed
							.getBandwidthUsedInBytes();
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Updating bandwidth used to "
								+ (lExistingBandwidthUsed + bandwidthUsedInBytes));
					bandwidthQuotaUsed
							.setBandwidthUsedInBytes(lExistingBandwidthUsed
									+ bandwidthUsedInBytes);
					bandwidthQuotaUsed.setTimeUpdatedMs(System
							.currentTimeMillis());
					bandwidthQuotaUsed
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getRawOffset());
					lFound = true;
					break;
				}
			}
			if (!lFound) {
				// create new
				BandwidthQuotaUsed lQuotaUsed = new BandwidthQuotaUsed();
				lQuotaUsed.setAccountId(pApplication.getAccountId());
				lQuotaUsed.setApplicationId(pApplication.getId());
				lQuotaUsed.setTrackingId(pApplication.getTrackingId());
				lQuotaUsed.setBandwidthUsedInBytes(bandwidthUsedInBytes);

				TimeZone timeZone = TimeZone.getTimeZone("UTC");

				lQuotaUsed.setSubscriptionPeriodStartMs(Utils
						.getStartOfDayToday(timeZone).getTimeInMillis());
				lQuotaUsed.setSubscriptionPeriodEndMs(Utils
						.getOneMonthFromToday(timeZone).getTimeInMillis());

				lQuotaUsed.setTimeCreatedMs(System.currentTimeMillis());
				lQuotaUsed.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				pm.makePersistent(lQuotaUsed);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Adding bandwidth used to "
							+ bandwidthUsedInBytes);
			}

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void upsertStorageUtilization(Application pApplication,
			Long storageUsedInBytes) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(StorageQuotaUsed.class);
			q.setFilter("accountId == accountIdParam && applicationId == applicationIdParam");
			q.declareParameters("Long accountIdParam, Long applicationIdParam");
			List<StorageQuotaUsed> lList = (List<StorageQuotaUsed>) q.execute(
					pApplication.getAccountId(), pApplication.getId());
			StorageQuotaUsed lQuotaUsed = null;
			if (lList != null && (lList.size() > 0)) {
				lQuotaUsed = lList.get(0);
				// TODO: delete later
				lQuotaUsed.setTrackingId(pApplication.getTrackingId());
				lQuotaUsed.setStorageUsedInBytes(storageUsedInBytes);
				lQuotaUsed.setTimeUpdatedMs(System.currentTimeMillis());
				lQuotaUsed.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Updating storage used to "
							+ storageUsedInBytes);
			} else {
				// create new
				lQuotaUsed = new StorageQuotaUsed();
				lQuotaUsed.setAccountId(pApplication.getAccountId());
				lQuotaUsed.setApplicationId(pApplication.getId());
				lQuotaUsed.setTrackingId(pApplication.getTrackingId());
				lQuotaUsed.setStorageUsedInBytes(storageUsedInBytes);
				lQuotaUsed.setTimeCreatedMs(System.currentTimeMillis());
				lQuotaUsed.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				pm.makePersistent(lQuotaUsed);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Adding storage used to " + storageUsedInBytes);
			}

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void upsertApplicationUtilization(Long accountId, int applicationsUsed) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(ApplicationQuotaUsed.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("Long accountIdParam");
			List<ApplicationQuotaUsed> lQuotas = (List<ApplicationQuotaUsed>) q
					.execute(accountId);
			ApplicationQuotaUsed lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
				lQuota.setApplicationsUsed(applicationsUsed);
				lQuota.setTimeUpdatedMs(System.currentTimeMillis());
				lQuota.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Updating applications used to "
							+ applicationsUsed);
			} else {
				// create new
				lQuota = new ApplicationQuotaUsed();
				lQuota.setAccountId(accountId);
				lQuota.setApplicationsUsed(applicationsUsed);
				lQuota.setTimeCreatedMs(System.currentTimeMillis());
				lQuota.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				pm.makePersistent(lQuota);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Setting applications used to "
							+ applicationsUsed);
			}

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
}
