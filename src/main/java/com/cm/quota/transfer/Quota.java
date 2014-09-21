package com.cm.quota.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quota implements Serializable {

	private String canonicalPlanName;
	private int applicationLimit;
	private int applicationsUsed;
	private long bandwidthUsed;
	private long bandwidthLimit;
	private long storageUsed;
	private long storageLimit;
	private int percentageApplicationUsed;
	private int percentageBandwidthUsed;
	private int percentageStorageUsed;
	
	private List<StorageQuota> storageQuota;


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

	public int getPercentageBandwidthUsed() {
		return percentageBandwidthUsed;
	}

	public void setPercentageBandwidthUsed(int percentageBandwidthUsed) {
		this.percentageBandwidthUsed = percentageBandwidthUsed;
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

	public int getPercentageStorageUsed() {
		return percentageStorageUsed;
	}

	public void setPercentageStorageUsed(int percentageStorageUsed) {
		this.percentageStorageUsed = percentageStorageUsed;
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

}
