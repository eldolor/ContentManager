package com.cm.config;

public enum CanonicalStorageQuota {
	LARGE(85899345920L) /** 4GB *20 **/
	, MEDIUM(32212254720L), SMALL(10737418240L), MICRO(2684354560L), FREE(
			1024L);

	private long value;

	private CanonicalStorageQuota(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

}