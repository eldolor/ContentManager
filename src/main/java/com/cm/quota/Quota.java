package com.cm.quota;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cm.admin.plan.CanonicalApplicationQuota;
import com.cm.admin.plan.CanonicalPlanName;
import com.cm.admin.plan.CanonicalPlanQuota;

@PersistenceCapable
public class Quota {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long accountId;

	// default to free
	@Persistent
	private String canonicalPlanName = CanonicalPlanName.FREE.getValue();
	// default to free
	@Persistent
	private Long storageLimitInBytes = CanonicalPlanQuota.FREE.getValue();

	@Persistent
	private Long storageUsedInBytes;

	@Persistent
	private Integer applicationLimit = CanonicalApplicationQuota.FREE
			.getValue();
	@Persistent
	private Integer applicationsUsed;

	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	public Quota() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimeCreatedMs() {
		return timeCreatedMs;
	}

	public void setTimeCreatedMs(Long timeCreatedMs) {
		this.timeCreatedMs = timeCreatedMs;
	}

	public Long getTimeCreatedTimeZoneOffsetMs() {
		return timeCreatedTimeZoneOffsetMs;
	}

	public void setTimeCreatedTimeZoneOffsetMs(Long timeCreatedTimeZoneOffsetMs) {
		this.timeCreatedTimeZoneOffsetMs = timeCreatedTimeZoneOffsetMs;
	}

	public Long getTimeUpdatedMs() {
		return timeUpdatedMs;
	}

	public void setTimeUpdatedMs(Long timeUpdatedMs) {
		this.timeUpdatedMs = timeUpdatedMs;
	}

	public Long getTimeUpdatedTimeZoneOffsetMs() {
		return timeUpdatedTimeZoneOffsetMs;
	}

	public void setTimeUpdatedTimeZoneOffsetMs(Long timeUpdatedTimeZoneOffsetMs) {
		this.timeUpdatedTimeZoneOffsetMs = timeUpdatedTimeZoneOffsetMs;
	}

	public String getCanonicalPlanName() {
		return canonicalPlanName;
	}

	public void setCanonicalPlanName(String canonicalPlanName) {
		this.canonicalPlanName = canonicalPlanName;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getApplicationLimit() {
		return applicationLimit;
	}

	public void setApplicationLimit(Integer applicationLimit) {
		this.applicationLimit = applicationLimit;
	}

	public Long getStorageLimitInBytes() {
		return storageLimitInBytes;
	}

	public void setStorageLimitInBytes(Long storageLimitInBytes) {
		this.storageLimitInBytes = storageLimitInBytes;
	}

	public Long getStorageUsedInBytes() {
		return storageUsedInBytes;
	}

	public void setStorageUsedInBytes(Long storageUsedInBytes) {
		this.storageUsedInBytes = storageUsedInBytes;
	}

	public Integer getApplicationsUsed() {
		return applicationsUsed;
	}

	public void setApplicationsUsed(Integer applicationsUsed) {
		this.applicationsUsed = applicationsUsed;
	}

}
