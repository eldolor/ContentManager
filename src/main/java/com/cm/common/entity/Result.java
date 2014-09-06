package com.cm.common.entity;



import java.io.Serializable;

public class Result implements Serializable {

    private String result;
    public static String SUCCESS = "SUCCESS";
    public static String FAILURE = "FAILURE";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

	@Override
	public String toString() {
		return "Result [result=" + result + "]";
	}
    
    
}
