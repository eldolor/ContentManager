package com.cm.contentmanager.contentstat;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ContentStatDailySummary {
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
	private Long applicationIdCount;
	@Persistent
	private Long contentGroupIdCount;
	@Persistent
	private Long contentIdCount;
	@Persistent
	private Long eventTypeCount;

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

	public Long getApplicationIdCount() {
		return applicationIdCount;
	}

	public void setApplicationIdCount(Long applicationIdCount) {
		this.applicationIdCount = applicationIdCount;
	}

	public Long getContentGroupIdCount() {
		return contentGroupIdCount;
	}

	public void setContentGroupIdCount(Long contentGroupIdCount) {
		this.contentGroupIdCount = contentGroupIdCount;
	}

	public Long getContentIdCount() {
		return contentIdCount;
	}

	public void setContentIdCount(Long contentIdCount) {
		this.contentIdCount = contentIdCount;
	}

	public Long getEventTypeCount() {
		return eventTypeCount;
	}

	public void setEventTypeCount(Long eventTypeCount) {
		this.eventTypeCount = eventTypeCount;
	}



}
