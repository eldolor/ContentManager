package com.cm.quota;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.config.CanonicalPlan;
import com.cm.contentmanager.application.Application;

@Service
public class QuotaService {
	@Autowired
	private QuotaDao quotaDao;
	private static final Logger LOGGER = Logger.getLogger(QuotaService.class
			.getName());
	public static final String APPLICATION_QUOTA_REACHED_ERROR_CODE = "ApplicationQuotaReached";
	public static final String STORAGE_QUOTA_REACHED_ERROR_CODE = "StorageQuotaReached";

	public StorageQuotaUsed getStorageQuotaUsed(Long accountId,
			Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return quotaDao.getStorageQuotaUsed(accountId, applicationId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public List<StorageQuotaUsed> getStorageQuotaUsed(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return quotaDao.getStorageQuotaUsed(accountId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public ApplicationQuotaUsed getApplicationQuotaUsed(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Account Id: " + accountId);
			return quotaDao.getApplicationQuotaUsed(accountId);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public BandwidthQuotaUsed getBandwidthQuotaUsed(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Account Id: " + accountId);
			return quotaDao.getBandwidthQuotaUsed(accountId);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public boolean hasSufficientStorageQuota(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<StorageQuotaUsed> lList = quotaDao
					.getStorageQuotaUsed(accountId);
			long lStorageQuotaUsed = 0L;
			// collect
			for (StorageQuotaUsed storageQuotaUsed : lList) {
				lStorageQuotaUsed += storageQuotaUsed.getStorageUsedInBytes();
			}

			Quota lQuota = quotaDao.get(accountId);

			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Storage used: "
						+ lStorageQuotaUsed
						+ " Storage Limit: "
						+ (lQuota.getStorageLimitInBytes() + lQuota
								.getBonusStorageLimitInBytes()));
			if (lStorageQuotaUsed < (lQuota.getStorageLimitInBytes() + lQuota
					.getBonusStorageLimitInBytes()))
				return true;
			return false;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

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
						+ " Storage Limit: "
						+ (lQuota.getStorageLimitInBytes() + lQuota
								.getBonusStorageLimitInBytes()));
			if (lStorageQuotaUsed.getStorageUsedInBytes() < (lQuota
					.getStorageLimitInBytes() + lQuota
					.getBonusStorageLimitInBytes()))
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
						+ ((lApplicationQuotaUsed != null) ? lApplicationQuotaUsed
								.getApplicationsUsed() : 0)
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

	public boolean hasSufficientBandwidthQuota(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Account Id: " + accountId);
			BandwidthQuotaUsed lQuotaUsed = quotaDao
					.getBandwidthQuotaUsed(accountId);

			Quota lQuota = quotaDao.get(accountId);
			if (lQuotaUsed == null) {
				LOGGER.info("Bandwidth quota used not found");
				// assuming that the user has sufficient quota
				return true;
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Bandwidth used: "
						+ lQuotaUsed.getBandwidthUsedInBytes()
						+ " Bandwidth Limit: "
						+ (lQuota.getBandwidthLimitInBytes() + lQuota
								.getBonusBandwidthLimitInBytes()));

			if (lQuotaUsed.getBandwidthUsedInBytes() < (lQuota
					.getBandwidthLimitInBytes() + lQuota
					.getBonusBandwidthLimitInBytes())) {
				return true;
			}
			return false;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void updatePlan(Long accountId, CanonicalPlan canonicalPlan) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.updatePlan(accountId, canonicalPlan);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void upsertBandwidthUtilization(Application pApplication,
			Long bandwidthUsedInBytes) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.upsertBandwidthUtilization(pApplication,
					bandwidthUsedInBytes);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void upsertStorageUtilization(Application pApplication,
			Long storageUsedInBytes) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.upsertStorageUtilization(pApplication, storageUsedInBytes);

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

	public void update(Quota quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.update(quota);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

}
