package com.cm.config;

public enum CanonicalPlanName {
	LARGE(5, "large"), MEDIUM(4, "medium"), SMALL(3, "small"), MICRO(2, "micro"), FREE(
			1, "free");
	private String value;
	private int level;

	private CanonicalPlanName(int level, String value) {
		this.level = level;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public int getLevel() {
		return this.level;
	}

}