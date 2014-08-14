package com.cm.stripe;

import java.io.Serializable;

public class StripeCustomer implements Serializable {

	private String stripeId;
	private String canonicalPlanName;

	public String getCanonicalPlanName() {
		return canonicalPlanName;
	}

	public void setCanonicalPlanName(String canonicalPlanName) {
		this.canonicalPlanName = canonicalPlanName;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

}
