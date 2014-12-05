package com.cm.gcm.transfer;

import java.io.Serializable;

public class NotificationMessage implements Serializable{
	private String trackingId;
	private String message;
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
