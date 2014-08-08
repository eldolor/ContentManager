package com.com.contentmanager.contentstat.transfer;

import java.io.Serializable;

public class ContentStat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long contentId;
	private long applicationId;
	private long contentGroupId;
	private String eventType;
	private long eventTimeMs;
	private long eventTimeZoneOffsetMs;

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public long getContentGroupId() {
		return contentGroupId;
	}

	public void setContentGroupId(long contentGroupId) {
		this.contentGroupId = contentGroupId;
	}

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public long getEventTimeMs() {
		return eventTimeMs;
	}

	public void setEventTimeMs(long eventTimeMs) {
		this.eventTimeMs = eventTimeMs;
	}

	public long getEventTimeZoneOffsetMs() {
		return eventTimeZoneOffsetMs;
	}

	public void setEventTimeZoneOffsetMs(long eventTimeZoneOffsetMs) {
		this.eventTimeZoneOffsetMs = eventTimeZoneOffsetMs;
	}

}
