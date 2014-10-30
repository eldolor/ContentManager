package com.cm.config;

public enum CanonicalPlanDetails {
	LARGE("400GB", "400GB", "$50.00", 5000), MEDIUM("160GB", "160GB", "$22.00", 2200), SMALL(
			"80GB", "80GB", "$12.00", 1200), MICRO("40GB", "40GB", "$7.00", 700), FREE(
			"20GB", "20GB", "FREE", 0);
	private String networkBandwidth;
	private String storage;
	private String price;
	private int priceInCents;

	private CanonicalPlanDetails(String networkBandwidth, String storage,
			String price, int priceInCents) {
		this.storage = storage;
		this.networkBandwidth = networkBandwidth;
		this.price = price;
		this.priceInCents = priceInCents;
	}

	public String getNetworkBandwidth() {
		return this.networkBandwidth;
	}

	public String getStorage() {
		return this.storage;
	}

	public String getPrice() {
		return this.price;
	}

	public int getPriceInCents() {
		return this.priceInCents;
	}
}