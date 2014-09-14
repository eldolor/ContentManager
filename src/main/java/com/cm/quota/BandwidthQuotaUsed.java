package com.cm.quota;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class BandwidthQuotaUsed {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long accountId;
	@Persistent
	private Long applicationId;
	@Persistent
	private String trackingId;

	@Persistent
	private Long bandwidthUsedInBytes;

	@Persistent
	private Long subscriptionPeriodStartMs;
	@Persistent
	private Long subscriptionPeriodEndMs;

	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	public BandwidthQuotaUsed() {
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getBandwidthUsedInBytes() {
		return (bandwidthUsedInBytes != null) ? bandwidthUsedInBytes : 0L;
	}

	public void setBandwidthUsedInBytes(Long bandwidthUsedInBytes) {
		this.bandwidthUsedInBytes = bandwidthUsedInBytes;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public Long getSubscriptionPeriodStartMs() {
		return subscriptionPeriodStartMs;
	}

	public void setSubscriptionPeriodStartMs(Long subscriptionPeriodStartMs) {
		this.subscriptionPeriodStartMs = subscriptionPeriodStartMs;
	}

	public Long getSubscriptionPeriodEndMs() {
		return subscriptionPeriodEndMs;
	}

	public void setSubscriptionPeriodEndMs(Long subscriptionPeriodEndMs) {
		this.subscriptionPeriodEndMs = subscriptionPeriodEndMs;
	}

}
