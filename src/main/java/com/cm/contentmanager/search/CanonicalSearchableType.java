package com.cm.contentmanager.search;

public enum CanonicalSearchableType {
	APPLICATION("application"), CONTENT_GROUP("content_group"), CONTENT(
			"content");

	private String type;

	private CanonicalSearchableType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}