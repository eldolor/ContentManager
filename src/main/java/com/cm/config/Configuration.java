package com.cm.config;

public enum Configuration {
	GCM_MAX_ATTEMPTS("com.cm.GCM_MAX_ATTEMPTS", "10"), GOOGLE_API_PROJECT_NUMBER(
			"com.cm.GOOGLE_API_PROJECT_NUMBER", "468566067831"), GOOGLE_API_KEY(
			"com.cm.GOOGLE_API_KEY", "AIzaSyDmUXoFreTugYfSL5B2QvM8mUDwhCte7BM"), MESSAGE_TYPE(
					"com.cm.MESSAGE_TYPE", "MESSAGE_TYPE"), MESSAGE_TYPE_CONTENT_LIST(
			"com.cm.MESSAGE_TYPE_CONTENT_LIST", "MESSAGE_TYPE_CONTENT_LIST"), MESSAGE_TYPE_SEND_TO_SYNC(
					"com.cm.MESSAGE_TYPE_SEND_TO_SYNC", "MESSAGE_TYPE_SEND_TO_SYNC");
	private String name;
	private String value;

	private Configuration(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}