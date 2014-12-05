package com.cm.contentmanager.contentstat;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ContentStatByContentGroupSummary  implements ContentStatSummary {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long contentGroupId;
	@Persistent
	private Long count;

	@Persistent
	private Long eventStartTimeMs;
	@Persistent
	private Long eventEndTimeMs;
	@Persistent
	private Long eventTimeZoneOffsetMs;

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

	public Long getEventTimeZoneOffsetMs() {
		return eventTimeZoneOffsetMs;
	}

	public void setEventTimeZoneOffsetMs(Long eventTimeZoneOffsetMs) {
		this.eventTimeZoneOffsetMs = eventTimeZoneOffsetMs;
	}


	public Long getCount() {
		return (count !=null)?count:0L;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
