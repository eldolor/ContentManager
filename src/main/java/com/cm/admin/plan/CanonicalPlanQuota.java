package com.cm.admin.plan;

public enum CanonicalPlanQuota {
	LARGE(85899345920L) /** 4GB *20 **/
	, MEDIUM(32212254720L), SMALL(10737418240L), MICRO(2684354560L), FREE(
			104857600L);

	private long value;

	private CanonicalPlanQuota(long value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

}