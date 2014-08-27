package com.cm.quota.transfer;

public class Quota {

	private String canonicalPlanName;
	private long storageLimitInBytes;
	private long storageUsedInBytes;
	private int applicationLimit;
	private int applicationsUsed;

	private int percentageStorageUsed;
	private int percentageApplicationUsed;

	public String getCanonicalPlanName() {
		return canonicalPlanName;
	}

	public void setCanonicalPlanName(String canonicalPlanName) {
		this.canonicalPlanName = canonicalPlanName;
	}

	public int getApplicationLimit() {
		return applicationLimit;
	}

	public void setApplicationLimit(int applicationLimit) {
		this.applicationLimit = applicationLimit;
	}

	public long getStorageLimitInBytes() {
		return storageLimitInBytes;
	}

	public void setStorageLimitInBytes(long storageLimitInBytes) {
		this.storageLimitInBytes = storageLimitInBytes;
	}

	public int getPercentageStorageUsed() {
		return percentageStorageUsed;
	}

	public void setPercentageStorageUsed(int percentageStorageUsed) {
		this.percentageStorageUsed = percentageStorageUsed;
	}

	public int getPercentageApplicationUsed() {
		return percentageApplicationUsed;
	}

	public void setPercentageApplicationUsed(int percentageApplicationUsed) {
		this.percentageApplicationUsed = percentageApplicationUsed;
	}

	public long getStorageUsedInBytes() {
		return storageUsedInBytes;
	}

	public void setStorageUsedInBytes(long storageUsedInBytes) {
		this.storageUsedInBytes = storageUsedInBytes;
	}

	public int getApplicationsUsed() {
		return applicationsUsed;
	}

	public void setApplicationsUsed(int applicationsUsed) {
		this.applicationsUsed = applicationsUsed;
	}

}
