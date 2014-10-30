package com.cm.config;

public enum CanonicalPlan {
	LARGE("large", 5, "400GB", "400GB", "$50.00", 5000, Integer.MAX_VALUE,
			429496729600L, 429496729600L), MEDIUM("medium", 4, "160GB",
			"160GB", "$22.00", 2200, Integer.MAX_VALUE, 171798691840L,
			171798691840L), SMALL("small", 3, "80GB", "80GB", "$12.00", 1200,
			Integer.MAX_VALUE, 85899345920L, 85899345920L), MICRO("micro", 2,
			"40GB", "40GB", "$7.00", 700, Integer.MAX_VALUE, 42949672960L,
			42949672960L), FREE("free", 1, "20GB", "20GB", "FREE", 0,
			Integer.MAX_VALUE, 21474836480L, 21474836480L);
	private String name;
	private int level;
	private String displayNetworkBandwidth;
	private String displayStorage;
	private String displayPrice;
	private int priceInCents;
	private int applicationQuota;
	private long bandwidthQuota;
	private long storageQuota;

	private CanonicalPlan(String name, int level,
			String displayNetworkBandwidth, String displayStorage,
			String displayPrice, int priceInCents, int applicationQuota,
			long bandwidthQuota, long storageQuota) {
		this.name = name;
		this.level = level;
		this.displayStorage = displayStorage;
		this.displayNetworkBandwidth = displayNetworkBandwidth;
		this.displayPrice = displayPrice;
		this.priceInCents = priceInCents;
		this.applicationQuota = applicationQuota;
		this.bandwidthQuota = bandwidthQuota;
		this.storageQuota = storageQuota;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public String getDisplayNetworkBandwidth() {
		return displayNetworkBandwidth;
	}

	public String getDisplayStorage() {
		return displayStorage;
	}

	public String getDisplayPrice() {
		return displayPrice;
	}

	public int getPriceInCents() {
		return this.priceInCents;
	}

	public int getApplicationQuota() {
		return applicationQuota;
	}

	public long getBandwidthQuota() {
		return bandwidthQuota;
	}

	public long getStorageQuota() {
		return storageQuota;
	}

}