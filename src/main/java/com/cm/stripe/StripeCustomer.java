package com.cm.stripe;

import java.io.Serializable;

public class StripeCustomer implements Serializable {

	private String stripeToken;
	private String canonicalPlanName;

	public String getCanonicalPlanName() {
		return canonicalPlanName;
	}

	public void setCanonicalPlanName(String canonicalPlanName) {
		this.canonicalPlanName = canonicalPlanName;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

}
