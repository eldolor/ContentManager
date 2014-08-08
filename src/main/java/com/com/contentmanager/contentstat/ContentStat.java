package com.com.contentmanager.contentstat;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ContentStat {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long contentId;
	@Persistent
	private Long applicationId;
	@Persistent
	private Long contentGroupId;
	@Persistent
	private String eventType;
	@Persistent
	private Long eventTimeMs;
	@Persistent
	private Long eventTimeZoneOffsetMs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Long getEventTimeMs() {
		return eventTimeMs;
	}

	public void setEventTimeMs(Long eventTimeMs) {
		this.eventTimeMs = eventTimeMs;
	}

	public Long getEventTimeZoneOffsetMs() {
		return eventTimeZoneOffsetMs;
	}

	public void setEventTimeZoneOffsetMs(Long eventTimeZoneOffsetMs) {
		this.eventTimeZoneOffsetMs = eventTimeZoneOffsetMs;
	}

}
