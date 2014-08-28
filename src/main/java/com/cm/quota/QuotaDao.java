package com.cm.quota;

import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.admin.plan.CanonicalApplicationQuota;
import com.cm.admin.plan.CanonicalPlanName;
import com.cm.admin.plan.CanonicalPlanQuota;
import com.cm.util.PMF;

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

	void updatePlan(Long accountId, CanonicalPlanName canonicalPlanName,
			CanonicalPlanQuota canonicalPlanQuota,
			CanonicalApplicationQuota canonicalApplicationQuota) {
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
				lQuota.setCanonicalPlanName(canonicalPlanName.getValue());
				lQuota.setStorageLimitInBytes(canonicalPlanQuota.getValue());
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

	void upsertStorageUtilization(Long accountId, Long applicationId,
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
					accountId, applicationId);
			StorageQuotaUsed lQuotaUsed = null;
			if (lList != null && (lList.size() > 0)) {
				lQuotaUsed = lList.get(0);
				lQuotaUsed.setStorageUsedInBytes(storageUsedInBytes);
				lQuotaUsed.setTimeUpdatedMs(System.currentTimeMillis());
				lQuotaUsed.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
			} else {
				// create new
				lQuotaUsed = new StorageQuotaUsed();
				lQuotaUsed.setAccountId(accountId);
				lQuotaUsed.setApplicationId(applicationId);
				lQuotaUsed.setStorageUsedInBytes(storageUsedInBytes);
				lQuotaUsed.setTimeCreatedMs(System.currentTimeMillis());
				lQuotaUsed.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				pm.makePersistent(lQuotaUsed);
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
			} else {
				// create new
				lQuota = new ApplicationQuotaUsed();
				lQuota.setAccountId(accountId);
				lQuota.setApplicationsUsed(applicationsUsed);
				lQuota.setTimeCreatedMs(System.currentTimeMillis());
				lQuota.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				pm.makePersistent(lQuota);
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
