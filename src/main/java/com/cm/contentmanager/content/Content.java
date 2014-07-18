package com.cm.contentmanager.content;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Content {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long contentGroupId;
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
	private Long startDateMs;
	@Persistent
	private String endDateIso8601;
	@Persistent
	private Long endDateMs;
	@Persistent
	private String type;
	@Persistent
	public Boolean deleted = false;
	@Persistent
	public Boolean enabled = true;
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
	public Long getContentGroupId() {
		return contentGroupId;
	}
	public void setContentGroupId(Long contentGroupId) {
		this.contentGroupId = contentGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDateIso8601() {
		return startDateIso8601;
	}
	public void setStartDateIso8601(String startDateIso8601) {
		this.startDateIso8601 = startDateIso8601;
	}
	public Long getStartDateMs() {
		return startDateMs;
	}
	public void setStartDateMs(Long startDateMs) {
		this.startDateMs = startDateMs;
	}
	public String getEndDateIso8601() {
		return endDateIso8601;
	}
	public void setEndDateIso8601(String endDateIso8601) {
		this.endDateIso8601 = endDateIso8601;
	}
	public Long getEndDateMs() {
		return endDateMs;
	}
	public void setEndDateMs(Long endDateMs) {
		this.endDateMs = endDateMs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
