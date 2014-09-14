package com.cm.config;

public enum CanonicalBandwidthQuota {
	LARGE(20), MEDIUM(15), SMALL(10), MICRO(5), FREE(1);

	private int value;

	private CanonicalBandwidthQuota(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}