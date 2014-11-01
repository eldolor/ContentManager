package com.cm.util;

import java.io.Serializable;

public class ValidationError implements Serializable {
	public static final String CATEGORY_WARNING = "warning";
	public static final String CATEGORY_ERROR = "error";

	private String code;
	private String description;
	private String category;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getCategory() {
		return (category == null) ? CATEGORY_ERROR : category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
