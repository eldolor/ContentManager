package com.cm.config;

public enum CanonicalCouponTypes {
		REFER_A_FRIEND("REFER_A_FRIEND");

	private String value;

	private CanonicalCouponTypes(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}