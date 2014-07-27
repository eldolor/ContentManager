package com.cm.contentserver;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ContentRequest implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String applicationId;
	@Persistent
	private String deviceId;
	@Persistent
	private String latitude;
	@Persistent
	private String longitude;
	@Persistent
	private String provider;
	@Persistent
	private String speed;
	@Persistent
	private String accuracy;
	@Persistent
	private String bearing;
	@Persistent
	private String altitude;
	@Persistent
	private Long timeMs;
	@Persistent
	private Long timeZoneOffsetMs;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getBearing() {
		return bearing;
	}

	public void setBearing(String bearing) {
		this.bearing = bearing;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public Long getTimeMs() {
		return timeMs;
	}

	public void setTimeMs(Long timeMs) {
		this.timeMs = timeMs;
	}

	public Long getTimeZoneOffsetMs() {
		return timeZoneOffsetMs;
	}

	public void setTimeZoneOffsetMs(Long timeZoneOffsetMs) {
		this.timeZoneOffsetMs = timeZoneOffsetMs;
	}

}
