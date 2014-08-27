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

	public boolean hasSufficientStorageQuota(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Quota lQuota = quotaDao.get(accountId);
			if (lQuota.getStorageUsedInBytes() < lQuota
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
			Quota lQuota = quotaDao.get(accountId);
			if (lQuota.getApplicationsUsed() < lQuota.getApplicationLimit())
				return true;
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

	public void updateStorageUtilization(Long accountId, Long storageUsedInBytes) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.updateStorageUtilization(accountId, storageUsedInBytes);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void updateApplicationUtilization(Long accountId,
			int applicationsUsed) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.updateApplicationUtilization(accountId, applicationsUsed);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	public void create(Quota quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			quotaDao.save(quota);
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
