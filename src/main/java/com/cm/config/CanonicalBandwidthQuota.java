package com.cm.config;

public enum CanonicalBandwidthQuota {
	LARGE(429496729600L), MEDIUM(171798691840L), SMALL(85899345920L), MICRO(
			42949672960L), FREE(21474836480L);

	private long value;

	private CanonicalBandwidthQuota(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

}