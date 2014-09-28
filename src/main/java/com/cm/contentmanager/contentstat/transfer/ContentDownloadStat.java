package com.cm.contentmanager.contentstat.transfer;

import java.io.Serializable;

public class ContentDownloadStat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long contentId;
	private long applicationId;
	private long contentGroupId;
	private long sizeInBytes;
	private String deviceId;
	private String latitude;
	private String longitude;
	private String provider;
	private String speed;
	private String accuracy;
	private String bearing;
	private String altitude;
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

	public long getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
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
