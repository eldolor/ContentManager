package com.cm.contentmanager.contentgroup;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cm.contentmanager.search.transfer.Searchable;

@PersistenceCapable
public class ContentGroup implements Searchable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long applicationId;
	@Persistent
	private Long accountId;

	@Persistent
	private Long userId;

	@Persistent
	private String name;

	@Persistent
	private String description;
	@Persistent
	private String startDateIso8601;

	@Persistent
	private String endDateIso8601;

	@Persistent
	private Long startDateMs;

	@Persistent
	private Long endDateMs;

	// default to true
	@Persistent
	private Boolean enabled = true;

	@Persistent
	private Boolean deleted = false;

	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getStartDateMs() {
		return startDateMs;
	}

	public void setStartDateMs(Long startDateMs) {
		this.startDateMs = startDateMs;
	}

	public Long getEndDateMs() {
		return endDateMs;
	}

	public void setEndDateMs(Long endDateMs) {
		this.endDateMs = endDateMs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStartDateIso8601() {
		return startDateIso8601;
	}

	public void setStartDateIso8601(String startDateIso8601) {
		this.startDateIso8601 = startDateIso8601;
	}

	public String getEndDateIso8601() {
		return endDateIso8601;
	}

	public void setEndDateIso8601(String endDateIso8601) {
		this.endDateIso8601 = endDateIso8601;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
