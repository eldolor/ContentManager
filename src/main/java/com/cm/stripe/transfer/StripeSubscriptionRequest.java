package com.cm.stripe.transfer;

import java.io.Serializable;

public class StripeSubscriptionRequest implements Serializable {

	private String stripeToken;
	private String canonicalPlanName;

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

	public String getCanonicalPlanName() {
		return canonicalPlanName;
	}

	public void setCanonicalPlanName(String canonicalPlanName) {
		this.canonicalPlanName = canonicalPlanName;
	}

}
