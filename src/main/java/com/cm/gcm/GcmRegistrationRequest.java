package com.cm.gcm;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class GcmRegistrationRequest {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String gcmId;
	@Persistent
	private String trackingId;
	@Persistent
	private String clientKey;
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
	private Boolean deleted = false;

	@Persistent
	private Boolean deprecated = false;

	@Persistent
	private Boolean gcmDeviceNotRegistered = false;
	@Persistent
	private Boolean gcmDeviceHasMultipleRegistrations = false;
	/** provided by GCM when trying to send message **/
	@Persistent
	private String canonicalGcmId;

	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return (deleted == null) ? false : deleted;
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

	public Long getTimeUpdatedMs() {
		return timeUpdatedMs;
	}

	public void setTimeUpdatedMs(Long timeUpdatedMs) {
		this.timeUpdatedMs = timeUpdatedMs;
	}

	public Long getTimeUpdatedTimeZoneOffsetMs() {
		return timeUpdatedTimeZoneOffsetMs;
	}

	public void setTimeUpdatedTimeZoneOffsetMs(Long timeUpdatedTimeZoneOffsetMs) {
		this.timeUpdatedTimeZoneOffsetMs = timeUpdatedTimeZoneOffsetMs;
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

	public boolean getGcmDeviceNotRegistered() {
		return (gcmDeviceNotRegistered == null) ? false
				: gcmDeviceNotRegistered;
	}

	public void setGcmDeviceNotRegistered(boolean gcmDeviceNotRegistered) {
		this.gcmDeviceNotRegistered = gcmDeviceNotRegistered;
	}

	public boolean getGcmDeviceHasMultipleRegistrations() {
		return (gcmDeviceHasMultipleRegistrations == null) ? false
				: gcmDeviceHasMultipleRegistrations;
	}

	public void setGcmDeviceHasMultipleRegistrations(
			boolean gcmDeviceHasMultipleRegistrations) {
		this.gcmDeviceHasMultipleRegistrations = gcmDeviceHasMultipleRegistrations;
	}

	public boolean isDeprecated() {
		return (deprecated == null) ? false : deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public String getCanonicalGcmId() {
		return canonicalGcmId;
	}

	public void setCanonicalGcmId(String canonicalGcmId) {
		this.canonicalGcmId = canonicalGcmId;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
