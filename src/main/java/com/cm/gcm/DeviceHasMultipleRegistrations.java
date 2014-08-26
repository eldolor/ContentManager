package com.cm.gcm;

public class DeviceHasMultipleRegistrations extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String canonicalRegistrationId;
	
	public DeviceHasMultipleRegistrations() {
		super();
	}

	public String getCanonicalRegistrationId() {
		return canonicalRegistrationId;
	}

	public void setCanonicalRegistrationId(String canonicalRegistrationId) {
		this.canonicalRegistrationId = canonicalRegistrationId;
	}
	
	
}
