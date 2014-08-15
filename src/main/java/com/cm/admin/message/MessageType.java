package com.cm.admin.message;

public enum MessageType {
	CREDIT_CARD_EXPIRING_SOON("com.cm.admin.message.type.CREDIT_CARD_EXPIRING_SOON"), CREDIT_CARD_EXPIRED(
			"com.cm.admin.message.type.CREDIT_CARD_EXPIRED");
	private String value;

	private MessageType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}