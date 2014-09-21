package com.cm.admin.message;

public enum MessageType {
	CREDIT_CARD_EXPIRING_SOON(
			"com.cm.admin.message.type.CREDIT_CARD_EXPIRING_SOON"), CREDIT_CARD_EXPIRED(
			"com.cm.admin.message.type.CREDIT_CARD_EXPIRED"), BANDWIDTH_QUOTA_EXCEEDED(
			"com.cm.admin.message.type.BANDWIDTH_QUOTA_EXCEEDED"), STORAGE_QUOTA_EXCEEDED(
			"com.cm.admin.message.type.STORAGE_QUOTA_EXCEEDED");
	private String value;

	private MessageType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}