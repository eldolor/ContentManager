package com.cm.accountmanagement.account;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cm.config.CanonicalPlan;

@PersistenceCapable
public class Account {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	// default to free
//	@Persistent
//	private String canonicalPlanId = CanonicalPlan.FREE.getId();
	
	@Persistent
	private String name;
	@Persistent
	private String description;
	@Persistent
	private Boolean enabled = true;

	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	public Account() {
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

	public Boolean isEnabled() {
		return (enabled != null) ? enabled : false;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

//	public String getCanonicalPlanId() {
//		return canonicalPlanId;
//	}
//
//	public void setCanonicalPlanId(String canonicalPlanId) {
//		this.canonicalPlanId = canonicalPlanId;
//	}

}
