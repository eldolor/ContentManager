package com.cm.quota.transfer;

import java.io.Serializable;

public class StorageQuota implements Serializable {
	private long applicationId;
	private String trackingId;
	private long storageUsedInBytes;

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


	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

}
