package com.cm.quota.transfer;

import java.io.Serializable;

public class StorageQuota implements Serializable {
	private String canonicalPlanName;
	private long applicationId;
	private long storageLimitInBytes;
	private long storageUsedInBytes;
	private int percentageStorageUsed;

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public long getStorageUsedInBytes() {
		return storageUsedInBytes;
	}

	public void setStorageUsedInBytes(long storageUsedInBytes) {
		this.storageUsedInBytes = storageUsedInBytes;
	}

	public long getStorageLimitInBytes() {
		return storageLimitInBytes;
	}

	public void setStorageLimitInBytes(long storageLimitInBytes) {
		this.storageLimitInBytes = storageLimitInBytes;
	}

	public String getCanonicalPlanName() {
		return canonicalPlanName;
	}

	public void setCanonicalPlanName(String canonicalPlanName) {
		this.canonicalPlanName = canonicalPlanName;
	}

	public int getPercentageStorageUsed() {
		return percentageStorageUsed;
	}

	public void setPercentageStorageUsed(int percentageStorageUsed) {
		this.percentageStorageUsed = percentageStorageUsed;
	}

}
