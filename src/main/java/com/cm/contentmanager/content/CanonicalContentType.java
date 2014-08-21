package com.cm.contentmanager.content;

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