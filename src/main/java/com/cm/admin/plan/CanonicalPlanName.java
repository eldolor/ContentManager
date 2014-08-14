package com.cm.admin.plan;

public enum CanonicalPlanName {
	LARGE("large"), MEDIUM("medium"), SMALL("small"), MICRO("micro"), FREE(
			"free");
	private String value;

	private CanonicalPlanName(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}