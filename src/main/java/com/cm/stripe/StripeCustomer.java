package com.cm.stripe;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class StripeCustomer {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	// stripe customer is tied to an account and not to a user
	@Persistent
	private Long accountId;

	@Persistent
	private Long userId;

	@Persistent
	private String username;

	@Persistent
	private String stripeId;
	@Persistent
	private String subscriptionId;
	@Persistent
	private Boolean deleted = false;

	// Start of the current period that the subscription has been invoiced for
	@Persistent
	private Long subscriptionCurrentPeriodStart;
	// End of the current period that the subscription has been invoiced for. At
	// the end of this period, a new invoice will be created.
	@Persistent
	private Long subscriptionCurrentPeriodEnd;
	// Possible values are trialing, active, past_due, canceled, or unpaid.
	@Persistent
	private String subscriptionStatus;

	@Persistent
	private String cardBrand;
	// This field was defined as a String within Stripe API
	@Persistent
	private String cardLast4;
	@Persistent
	private Integer cardExpMonth;
	@Persistent
	private Integer cardExpYear;
	@Persistent
	private String cardAddressZip;
	@Persistent
	private String cardFunding;

	@Persistent
	private String canonicalPlanId;
	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	public String getCanonicalPlanId() {
		return canonicalPlanId;
	}

	public void setCanonicalPlanId(String canonicalPlanId) {
		this.canonicalPlanId = canonicalPlanId;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getTimeCreatedMs() {
		return timeCreatedMs;
	}

	public void setTimeCreatedMs(Long timeCreatedMs) {
		this.timeCreatedMs = timeCreatedMs;
	}

	public Long getTimeCreatedTimeZoneOffsetMs() {
		return timeCreatedTimeZoneOffsetMs;
	}

	public void setTimeCreatedTimeZoneOffsetMs(Long timeCreatedTimeZoneOffsetMs) {
		this.timeCreatedTimeZoneOffsetMs = timeCreatedTimeZoneOffsetMs;
	}

	public Long getTimeUpdatedMs() {
		return timeUpdatedMs;
	}

	public void setTimeUpdatedMs(Long timeUpdatedMs) {
		this.timeUpdatedMs = timeUpdatedMs;
	}

	public Long getTimeUpdatedTimeZoneOffsetMs() {
		return timeUpdatedTimeZoneOffsetMs;
	}

	public void setTimeUpdatedTimeZoneOffsetMs(Long timeUpdatedTimeZoneOffsetMs) {
		this.timeUpdatedTimeZoneOffsetMs = timeUpdatedTimeZoneOffsetMs;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public String getCardLast4() {
		return cardLast4;
	}

	public void setCardLast4(String cardLast4) {
		this.cardLast4 = cardLast4;
	}

	public boolean isDeleted() {
		return (deleted != null) ? deleted : false;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getCardExpMonth() {
		return cardExpMonth;
	}

	public void setCardExpMonth(Integer cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}

	public Integer getCardExpYear() {
		return cardExpYear;
	}

	public void setCardExpYear(Integer cardExpYear) {
		this.cardExpYear = cardExpYear;
	}

	public String getCardAddressZip() {
		return cardAddressZip;
	}

	public void setCardAddressZip(String cardAddressZip) {
		this.cardAddressZip = cardAddressZip;
	}

	public String getCardFunding() {
		return cardFunding;
	}

	public void setCardFunding(String cardFunding) {
		this.cardFunding = cardFunding;
	}

	public Long getSubscriptionCurrentPeriodStart() {
		return subscriptionCurrentPeriodStart;
	}

	public void setSubscriptionCurrentPeriodStart(
			Long subscriptionCurrentPeriodStart) {
		this.subscriptionCurrentPeriodStart = subscriptionCurrentPeriodStart;
	}

	public Long getSubscriptionCurrentPeriodEnd() {
		return subscriptionCurrentPeriodEnd;
	}

	public void setSubscriptionCurrentPeriodEnd(Long subscriptionCurrentPeriodEnd) {
		this.subscriptionCurrentPeriodEnd = subscriptionCurrentPeriodEnd;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

}
