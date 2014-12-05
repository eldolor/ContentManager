package com.cm.contentmanager.contentstat.transfer;

import java.io.Serializable;

public class UnmanagedContentStatByUrlSummary implements Serializable {
	private long id;
	private long applicationId;
	private String urlHash;
	private String url;
	private long count;

	private long eventStartTimeMs;
	private long eventEndTimeMs;
	private long eventTimeZoneOffsetMs;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public String getUrlHash() {
		return urlHash;
	}

	public void setUrlHash(String urlHash) {
		this.urlHash = urlHash;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getEventStartTimeMs() {
		return eventStartTimeMs;
	}

	public void setEventStartTimeMs(long eventStartTimeMs) {
		this.eventStartTimeMs = eventStartTimeMs;
	}

	public long getEventEndTimeMs() {
		return eventEndTimeMs;
	}

	public void setEventEndTimeMs(long eventEndTimeMs) {
		this.eventEndTimeMs = eventEndTimeMs;
	}

	public long getEventTimeZoneOffsetMs() {
		return eventTimeZoneOffsetMs;
	}

	public void setEventTimeZoneOffsetMs(long eventTimeZoneOffsetMs) {
		this.eventTimeZoneOffsetMs = eventTimeZoneOffsetMs;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
