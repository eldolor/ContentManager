package com.cm.util;

import java.io.Serializable;

public class ValidationError implements Serializable
{

	private String code;
	private String description;

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
