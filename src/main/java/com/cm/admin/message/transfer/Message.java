package com.cm.admin.message.transfer;

import java.io.Serializable;

public class Message implements Serializable {

	private Long id;
	private String message;
	private String messageClass;
	private Long timeViewedMs;
	private Long timeViewedTimeZoneOffsetMs;

	public Message() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTimeViewedMs() {
		return timeViewedMs;
	}

	public void setTimeViewedMs(Long timeViewedMs) {
		this.timeViewedMs = timeViewedMs;
	}

	public Long getTimeViewedTimeZoneOffsetMs() {
		return timeViewedTimeZoneOffsetMs;
	}

	public void setTimeViewedTimeZoneOffsetMs(Long timeViewedTimeZoneOffsetMs) {
		this.timeViewedTimeZoneOffsetMs = timeViewedTimeZoneOffsetMs;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", messageClass="
				+ messageClass + ", timeViewedMs=" + timeViewedMs
				+ ", timeViewedTimeZoneOffsetMs=" + timeViewedTimeZoneOffsetMs
				+ "]";
	}


}
