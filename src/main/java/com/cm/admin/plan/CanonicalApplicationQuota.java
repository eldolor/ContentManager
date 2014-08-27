package com.cm.admin.plan;

public enum CanonicalApplicationQuota {
	LARGE(20), MEDIUM(15), SMALL(10), MICRO(5), FREE(1);

	private int value;

	private CanonicalApplicationQuota(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}