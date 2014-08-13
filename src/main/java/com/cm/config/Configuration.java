package com.cm.config;

public enum Configuration {
	GCM_MAX_ATTEMPTS("10"), GOOGLE_API_PROJECT_NUMBER("468566067831"), GOOGLE_API_KEY(
			"AIzaSyDmUXoFreTugYfSL5B2QvM8mUDwhCte7BM"), MESSAGE_TYPE(
			"MESSAGE_TYPE"), MESSAGE_TYPE_CONTENT_LIST(
			"MESSAGE_TYPE_CONTENT_LIST"), MESSAGE_TYPE_SEND_TO_SYNC(
			"MESSAGE_TYPE_SEND_TO_SYNC"), GCM_MESSAGE_SIZE_LIMIT_BYTES("4096"), SITE_NAME(
			"Roquette"), FORGOT_PASSWORD_URL(
			"https://roquette.com/forgotpassword"), FORGOT_PASSWORD_FROM_EMAIL_ADDRESS(
			"anshu.gaind@gmail.com"), FORGOT_PASSWORD_FROM_NAME(
			"Coconut Martini Inc");
	private String value;

	private Configuration(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}