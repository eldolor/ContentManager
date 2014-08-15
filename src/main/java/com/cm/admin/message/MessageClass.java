package com.cm.admin.message;

public enum MessageClass {
	SUCCESS("com.cm.admin.message.class.SUCCESS"), INFO(
			"com.cm.admin.message.class.INFO"), WARNING(
			"com.cm.admin.message.class.WARNING"), ALERT(
			"com.cm.admin.message.class.ALERT");
	private String value;

	private MessageClass(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}