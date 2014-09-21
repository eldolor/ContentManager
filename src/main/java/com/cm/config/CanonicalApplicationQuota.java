package com.cm.config;

public enum CanonicalApplicationQuota {
	LARGE(Integer.MAX_VALUE), MEDIUM(Integer.MAX_VALUE), SMALL(Integer.MAX_VALUE), MICRO(Integer.MAX_VALUE), FREE(Integer.MAX_VALUE);

	private int value;

	private CanonicalApplicationQuota(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}