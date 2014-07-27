package com.cm.gcm;

public enum GcmActions {
	DELETE_ROOT_DIRECTORY("delete_root_directory"), TRIGGER_CAMPAIGN_REFRESH(
			"trigger_campaign_refresh"), DELETE_DEVICE_REGISTRATION_FILE(
			"delete_device_registration_file"), UPLOAD_ROOT_DIRECTORY_LISTING(
			"upload_root_directory_listing"), UPLOAD_CAMPAIGN_FILE(
			"upload_campaign_file"), UPLOAD_DEVICE_REGISTRATION_FILE(
			"upload_device_registration_file"), SEND_NOTIFICATION("send_notification");

	private String type;

	private GcmActions(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
