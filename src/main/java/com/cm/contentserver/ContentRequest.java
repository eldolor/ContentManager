package com.cm.contentserver;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cm.common.entity.EventLocation;
@PersistenceCapable
public class ContentRequest implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String applicationId;
	@Persistent
	private String deviceId;
	@Persistent
	private EventLocation eventLocation;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public EventLocation getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(EventLocation eventLocation) {
		this.eventLocation = eventLocation;
	}

}
