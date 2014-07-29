package com.cm.contentserver.transfer;

import java.io.Serializable;

public class ContentRequest implements Serializable {
	private String applicationId;
	private String deviceId;
	private String latitude;
	private String longitude;
	private String provider;
	private String speed;
	private String accuracy;
	private String bearing;
	private String altitude;
	private Long timeMs;
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
