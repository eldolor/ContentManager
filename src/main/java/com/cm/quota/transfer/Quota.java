package com.cm.quota.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quota implements Serializable {

	private String canonicalPlanId;
	private int applicationLimit;
	private int applicationsUsed;
	private long bandwidthUsed;
	private long bandwidthLimit;
	private long storageUsed;
	private long storageLimit;
	private String percentageApplicationUsed;
	private String percentageBandwidthUsed;
	private String percentageStorageUsed;
	
	private List<StorageQuota> storageQuota;


	public String getCanonicalPlanId() {
		return canonicalPlanId;
	}

	public void setCanonicalPlanId(String canonicalPlanId) {
		this.canonicalPlanId = canonicalPlanId;
	}

	public int getApplicationLimit() {
		return applicationLimit;
	}

	public void setApplicationLimit(int applicationLimit) {
		this.applicationLimit = applicationLimit;
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



	public long getBandwidthUsed() {
		return bandwidthUsed;
	}

	public void setBandwidthUsed(long bandwidthUsed) {
		this.bandwidthUsed = bandwidthUsed;
	}

	public long getStorageUsed() {
		return storageUsed;
	}

	public void setStorageUsed(long storageUsed) {
		this.storageUsed = storageUsed;
	}



	public long getBandwidthLimit() {
		return bandwidthLimit;
	}

	public void setBandwidthLimit(long bandwidthLimit) {
		this.bandwidthLimit = bandwidthLimit;
	}

	public long getStorageLimit() {
		return storageLimit;
	}

	public void setStorageLimit(long storageLimit) {
		this.storageLimit = storageLimit;
	}

	public String getPercentageApplicationUsed() {
		return percentageApplicationUsed;
	}

	public void setPercentageApplicationUsed(String percentageApplicationUsed) {
		this.percentageApplicationUsed = percentageApplicationUsed;
	}

	public String getPercentageBandwidthUsed() {
		return percentageBandwidthUsed;
	}

	public void setPercentageBandwidthUsed(String percentageBandwidthUsed) {
		this.percentageBandwidthUsed = percentageBandwidthUsed;
	}

	public String getPercentageStorageUsed() {
		return percentageStorageUsed;
	}

	public void setPercentageStorageUsed(String percentageStorageUsed) {
		this.percentageStorageUsed = percentageStorageUsed;
	}


}
