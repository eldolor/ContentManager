package com.cm.quota;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.admin.plan.CanonicalApplicationQuota;
import com.cm.admin.plan.CanonicalPlanName;
import com.cm.admin.plan.CanonicalPlanQuota;

@Service
public class QuotaService {
	@Autowired
	private QuotaDao quotaDao;
	private static final Logger LOGGER = Logger.getLogger(QuotaService.class
			.getName());
	public static final String APPLICATION_QUOTA_REACHED_ERROR_CODE = "ApplicationQuotaReached";
	public static final String STORAGE_QUOTA_REACHED_ERROR_CODE = "StorageQuotaReached";

	public boolean hasSufficientStorageQuota(Long accountId, Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			StorageQuotaUsed lStorageQuotaUsed = quotaDao.getStorageQuotaUsed(
					accountId, applicationId);
			Quota lQuota = quotaDao.get(accountId);

			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Storage used: "
						+ lStorageQuotaUsed.getStorageUsedInBytes()
						+ " Storage Limit: " + lQuota.getStorageLimitInBytes());
			if (lStorageQuotaUsed.getStorageUsedInBytes() < lQuota
					.getStorageLimitInBytes())
				return true;
			return false;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public boolean hasSufficientApplicationQuota(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Account Id: " + accountId);
			ApplicationQuotaUsed lApplicationQuotaUsed = quotaDao
					.getApplicationQuotaUsed(accountId);

			Quota lQuota = quotaDao.get(accountId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Applications used: "
						+ lApplicationQuotaUsed.getApplicationsUsed()
						+ " Application Limit: " + lQuota.getApplicationLimit());

			if (lApplicationQuotaUsed.getApplicationsUsed() < lQuota
					.getApplicationLimit()) {
				return true;
			}
			return false;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void updatePlan(Long accountId, CanonicalPlanName canonicalPlanName,
			CanonicalPlanQuota canonicalPlanQuota,
			CanonicalApplicationQuota canonicalApplicationQuota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.updatePlan(accountId, canonicalPlanName,
					canonicalPlanQuota, canonicalApplicationQuota);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void upsertStorageUtilization(Long accountId, Long applicationId,
			Long storageUsedInBytes) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.upsertStorageUtilization(accountId, applicationId,
					storageUsedInBytes);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void upsertApplicationUtilization(Long accountId,
			int applicationsUsed) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.upsertApplicationUtilization(accountId, applicationsUsed);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void create(Quota quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.create(quota);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public Quota get(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return quotaDao.get(accountId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

}
