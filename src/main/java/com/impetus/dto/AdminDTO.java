package com.impetus.dto;

public class AdminDTO {

	private long totalUsers;
	private long totalUnderwriters;
	private long totalCustomers;
	private long totalPolicies;
	private long totalPendingPolicies;
	private long totalSuccessfulClaims;
	private long totalTypeOfPolicies;

	public AdminDTO() {
		super();

	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalUnderwriters() {
		return totalUnderwriters;
	}

	public void setTotalUnderwriters(long totalUnderwriters) {
		this.totalUnderwriters = totalUnderwriters;
	}

	public long getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(long totalCustomers) {
		this.totalCustomers = totalCustomers;
	}

	public long getTotalPolicies() {
		return totalPolicies;
	}

	public void setTotalPolicies(long totalPolicies) {
		this.totalPolicies = totalPolicies;
	}

	public long getTotalPendingPolicies() {
		return totalPendingPolicies;
	}

	public void setTotalPendingPolicies(long totalPendingPolicies) {
		this.totalPendingPolicies = totalPendingPolicies;
	}

	public long getTotalSuccessfulClaims() {
		return totalSuccessfulClaims;
	}

	public void setTotalSuccessfulClaims(long totalSuccessfulClaims) {
		this.totalSuccessfulClaims = totalSuccessfulClaims;
	}

	public long getTotalTypeOfPolicies() {
		return totalTypeOfPolicies;
	}

	public void setTotalTypeOfPolicies(long totalTypeOfPolicies) {
		this.totalTypeOfPolicies = totalTypeOfPolicies;
	}

	@Override
	public String toString() {
		return "Admin [totalUsers=" + totalUsers + ", totalUnderwriters=" + totalUnderwriters + ", totalCustomers="
				+ totalCustomers + ", totalPolicies=" + totalPolicies + ", totalPendingPolicies=" + totalPendingPolicies
				+ ", totalSuccessfulClaims=" + totalSuccessfulClaims + ", totalTypeOfPolicies=" + totalTypeOfPolicies
				+ "]";
	}

}
