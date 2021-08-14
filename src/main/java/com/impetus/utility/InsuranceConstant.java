package com.impetus.utility;

/**
 * @author arpit.raghuwanshi A constant class for all declaration
 */
public final class InsuranceConstant {

	private InsuranceConstant() {
	}

	// Security Security SECRET KEY
	public static final String SECRETKEY = "InsuranceApplication";

	// CustomerPolicyDetails status field values
	public static final String APPROVED = "APPROVED";
	public static final String PENDING = "PENDING";
	public static final String DECLINE = "DECLINE";

	// Role value fields values
	public static final String ADMIN = "ADMIN";
	public static final String UNDERWRITER = "UNDERWRITER";
	public static final String USER = "USER";

	// user messages
	public static final String NOT_AVAILABLE = "Not Available";

	public static final Integer HOURS_4=4;
	
	public static final String UI_MAPPING_URL="http://localhost:4200";
}
