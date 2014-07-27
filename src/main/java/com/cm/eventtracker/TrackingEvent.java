package com.cm.eventtracker;

import com.cm.common.entity.EventLocation;

public class TrackingEvent {
	private String id;
	private String contentGroupId;
	private String type;

	private String applicationId;
	private String deviceId;
	private EventLocation eventLocation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EventLocation getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(EventLocation eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getContentGroupId() {
		return contentGroupId;
	}

	public void setContentGroupId(String contentGroupId) {
		this.contentGroupId = contentGroupId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

}
