package com.cm.contentserver.transfer;

public class Content {
	private Long id;
	private Long contentGroupId;
	private Long applicationId;
	private String name;
	private String type;
	private String uri;
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
	
	
}
