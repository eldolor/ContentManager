package com.cm.gcm;

public class DeviceHasMoreThanOneRegistration extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceHasMoreThanOneRegistration() {
		super();
	}

	public DeviceHasMoreThanOneRegistration(String message) {
		super(message);
	}
}
