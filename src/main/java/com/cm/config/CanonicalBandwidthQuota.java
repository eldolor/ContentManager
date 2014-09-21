package com.cm.config;

public enum CanonicalBandwidthQuota {
	LARGE(2199023255552L), MEDIUM(1649267441664L), SMALL(1099511627776L), MICRO(
			536870912000L), FREE(107374182400L);

	private long value;

	private CanonicalBandwidthQuota(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

}