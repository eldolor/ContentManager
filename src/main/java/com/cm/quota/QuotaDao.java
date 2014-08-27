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
			q.declareParameters("String accountIdParam");
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

	void save(Quota quota) {
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
			q.declareParameters("String accountIdParam");
			List<Quota> lQuotas = (List<Quota>) q.execute(accountId);
			Quota lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
				lQuota.setCanonicalPlanName(canonicalPlanName.getValue());
				lQuota.setStorageLimitInBytes(canonicalPlanQuota.getValue());
				lQuota.setApplicationLimit(canonicalApplicationQuota.getValue());
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

	void updateStorageUtilization(Long accountId, Long storageUsedInBytes) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Quota.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("String accountIdParam");
			List<Quota> lQuotas = (List<Quota>) q.execute(accountId);
			Quota lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
				lQuota.setStorageUsedInBytes(storageUsedInBytes);
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

	void updateApplicationUtilization(Long accountId, int applicationsUsed) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Quota.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("String accountIdParam");
			List<Quota> lQuotas = (List<Quota>) q.execute(accountId);
			Quota lQuota = null;
			if (lQuotas != null && (lQuotas.size() > 0)) {
				lQuota = lQuotas.get(0);
				lQuota.setApplicationsUsed(applicationsUsed);
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
}
