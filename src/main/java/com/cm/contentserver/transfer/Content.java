package com.cm.contentserver.transfer;

import java.io.Serializable;

public class Content implements Serializable {
	private Long id;
	private Long contentGroupId;
	private Long applicationId;
	private String name;
	private String[] tags;	
	private String type;
	private String uri;
	private boolean updateOverWifiOnly;
	private boolean collectUsageData;
	private Long sizeInBytes;
	private Long timeCreatedMs;
	private Long timeUpdatedMs;

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

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getTimeCreatedMs() {
		return timeCreatedMs;
	}

	public void setTimeCreatedMs(Long timeCreatedMs) {
		this.timeCreatedMs = timeCreatedMs;
	}

	public Long getTimeUpdatedMs() {
		return timeUpdatedMs;
	}

	public void setTimeUpdatedMs(Long timeUpdatedMs) {
		this.timeUpdatedMs = timeUpdatedMs;
	}

	public boolean isUpdateOverWifiOnly() {
		return updateOverWifiOnly;
	}

	public void setUpdateOverWifiOnly(boolean updateOverWifiOnly) {
		this.updateOverWifiOnly = updateOverWifiOnly;
	}

	public Long getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(Long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public boolean isCollectUsageData() {
		return collectUsageData;
	}

	public void setCollectUsageData(boolean collectUsageData) {
		this.collectUsageData = collectUsageData;
	}

}
