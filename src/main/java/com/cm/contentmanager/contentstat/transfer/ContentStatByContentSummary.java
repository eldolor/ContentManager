package com.cm.contentmanager.contentstat.transfer;

import java.io.Serializable;

public class ContentStatByContentSummary implements Serializable {
	private long id;
	private long contentId;
	private String name;
	private long count;
	private Long eventStartTimeMs;
	private Long eventEndTimeMs;
	private long eventTimeZoneOffsetMs;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Long getEventStartTimeMs() {
		return eventStartTimeMs;
	}

	public void setEventStartTimeMs(Long eventStartTimeMs) {
		this.eventStartTimeMs = eventStartTimeMs;
	}

	public Long getEventEndTimeMs() {
		return eventEndTimeMs;
	}

	public void setEventEndTimeMs(Long eventEndTimeMs) {
		this.eventEndTimeMs = eventEndTimeMs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

}
