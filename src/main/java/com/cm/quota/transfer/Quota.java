package com.cm.quota.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quota implements Serializable {

	private String canonicalPlanName;
	private int applicationLimit;
	private int applicationsUsed;
	private List<StorageQuota> storageQuota;

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

	public int getPercentageApplicationUsed() {
		return percentageApplicationUsed;
	}

	public void setPercentageApplicationUsed(int percentageApplicationUsed) {
		this.percentageApplicationUsed = percentageApplicationUsed;
	}

	public int getApplicationsUsed() {
		return applicationsUsed;
	}

	public void setApplicationsUsed(int applicationsUsed) {
		this.applicationsUsed = applicationsUsed;
	}

	public List<StorageQuota> getStorageQuota() {
		return storageQuota;
	}

	public void setStorageQuota(List<StorageQuota> storageQuota) {
		this.storageQuota = storageQuota;
	}

	public void addStorageQuota(StorageQuota storageQuota) {
		if (this.storageQuota == null)
			this.storageQuota = new ArrayList<StorageQuota>();

		this.storageQuota.add(storageQuota);
	}

}
