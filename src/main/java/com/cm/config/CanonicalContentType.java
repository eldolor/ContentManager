package com.cm.config;

public enum CanonicalContentType {
	VIDEO("video"), IMAGE("image");
	private String value;

	private CanonicalContentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}