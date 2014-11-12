package com.cm.stripe.transfer;

import java.io.Serializable;

public class StripeSubscriptionRequest implements Serializable {

	private String stripeToken;
	private String canonicalPlanId;

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

	public String getCanonicalPlanId() {
		return canonicalPlanId;
	}

	public void setCanonicalPlanId(String canonicalPlanId) {
		this.canonicalPlanId = canonicalPlanId;
	}

}
