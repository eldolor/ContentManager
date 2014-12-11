package com.cm.quota;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cm.config.CanonicalPlan;

@PersistenceCapable
public class Quota {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long accountId;

	// default to free
	@Persistent
	private String canonicalPlanId = CanonicalPlan.FREE.getId();
	// default to free
	@Persistent
	private Long storageLimitInBytes = CanonicalPlan.FREE.getStorageQuota();
	@Persistent
	private Long bonusStorageLimitInBytes = 0L;
	@Persistent
	private Long bandwidthLimitInBytes = CanonicalPlan.FREE.getBandwidthQuota();
	@Persistent
	private Long bonusBandwidthLimitInBytes = 0L;

	@Persistent
	private Integer applicationLimit = CanonicalPlan.FREE.getApplicationQuota();

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

	public String getCanonicalPlanId() {
		return canonicalPlanId;
	}

	public void setCanonicalPlanId(String canonicalPlanId) {
		this.canonicalPlanId = canonicalPlanId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getApplicationLimit() {
		return (applicationLimit != null) ? applicationLimit : 0;
	}

	public void setApplicationLimit(Integer applicationLimit) {
		this.applicationLimit = applicationLimit;
	}

	public Long getStorageLimitInBytes() {
		return (storageLimitInBytes != null) ? storageLimitInBytes : 0L;
	}

	public void setStorageLimitInBytes(Long storageLimitInBytes) {
		this.storageLimitInBytes = storageLimitInBytes;
	}

	public Long getBandwidthLimitInBytes() {
		return bandwidthLimitInBytes;
	}

	public void setBandwidthLimitInBytes(Long bandwidthLimitInBytes) {
		this.bandwidthLimitInBytes = bandwidthLimitInBytes;
	}

	public Long getBonusStorageLimitInBytes() {
		return bonusStorageLimitInBytes;
	}

	public void setBonusStorageLimitInBytes(Long bonusStorageLimitInBytes) {
		this.bonusStorageLimitInBytes = bonusStorageLimitInBytes;
	}

	public Long getBonusBandwidthLimitInBytes() {
		return bonusBandwidthLimitInBytes;
	}

	public void setBonusBandwidthLimitInBytes(Long bonusBandwidthLimitInBytes) {
		this.bonusBandwidthLimitInBytes = bonusBandwidthLimitInBytes;
	}

}
