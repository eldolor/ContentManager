package com.cm.contentmanager.application;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cm.contentmanager.search.transfer.Searchable;

@PersistenceCapable
public class Application implements Searchable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String trackingId;

	@Persistent
	private Long accountId;

	@Persistent
	private Long userId;

	@Persistent
	private String name;

	/** search index **/
	@Persistent
	private String nameIdx;

	@Persistent
	private String description;

	// default to true
	@Persistent
	private Boolean updateOverWifiOnly = true;

	// default to true
	@Persistent
	private Boolean enabled = true;

	// default to true
	@Persistent
	private Boolean collectUsageData = true;

	@Persistent
	private Boolean deleted = false;

	@Persistent
	private Boolean deletedOnPlanDowngrade = false;

	@Persistent
	private Boolean changesStaged = false;

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

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public Boolean isUpdateOverWifiOnly() {
		return updateOverWifiOnly;
	}

	public void setUpdateOverWifiOnly(Boolean updateOverWifiOnly) {
		this.updateOverWifiOnly = updateOverWifiOnly;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean isCollectUsageData() {
		return collectUsageData;
	}

	public void setCollectUsageData(Boolean collectUsageData) {
		this.collectUsageData = collectUsageData;
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Boolean getChangesStaged() {
		return changesStaged;
	}

	public void setChangesStaged(Boolean changesStaged) {
		this.changesStaged = changesStaged;
	}

	public boolean isDeletedOnPlanDowngrade() {
		return (deletedOnPlanDowngrade != null) ? deletedOnPlanDowngrade
				: false;
	}

	public void setDeletedOnPlanDowngrade(Boolean deletedOnPlanDowngrade) {
		this.deletedOnPlanDowngrade = deletedOnPlanDowngrade;
	}

	public String getNameIdx() {
		return nameIdx;
	}

	public void setNameIdx(String nameIdx) {
		this.nameIdx = nameIdx;
	}

}
