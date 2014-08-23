package com.cm.contentserver.transfer;

import java.io.Serializable;


public class Handshake implements Serializable {
	private String trackingId;
	private String gcmRegistrationId;
	private Long lastKnownTimestamp;
	private String deviceId;
	private String latitude;
	private String longitude;
	private String provider;
	private String speed;
	private String accuracy;
	private String bearing;
	private String altitude;
	private Long timeCreatedMs;
	private Long timeCreatedTimeZoneOffsetMs;
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getGcmRegistrationId() {
		return gcmRegistrationId;
	}
	public void setGcmRegistrationId(String gcmRegistrationId) {
		this.gcmRegistrationId = gcmRegistrationId;
	}
	public Long getLastKnownTimestamp() {
		return lastKnownTimestamp;
	}
	public void setLastKnownTimestamp(Long lastKnownTimestamp) {
		this.lastKnownTimestamp = lastKnownTimestamp;
	}
	public Long getTimeCreatedMs() {
		return timeCreatedMs;
	}
	public void setTimeCreatedMs(Long timeCreatedMs) {
		this.timeCreatedMs = timeCreatedMs;
	}
	public Long getTimeCreatedTimeZoneOffsetMs() {
		return timeCreatedTimeZoneOffsetMs;
	}
	public void setTimeCreatedTimeZoneOffsetMs(Long timeCreatedTimeZoneOffsetMs) {
		this.timeCreatedTimeZoneOffsetMs = timeCreatedTimeZoneOffsetMs;
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

}
