package com.impetus.dto;

public class UnderWriterDTO {

	private Long totalPolices;

	private Long totalUsers;

	private Long totalTypesOfPolices;

	private Long totalPendingPolices;

	private Long totalDeclinePolices;

	public UnderWriterDTO() {
	}

	public UnderWriterDTO(Long totalPolices, Long totalUsers, Long totalTypesOfPolices, Long totalPendingPolices,
			Long totalDeclinePolices) {
		this.totalPolices = totalPolices;
		this.totalUsers = totalUsers;
		this.totalTypesOfPolices = totalTypesOfPolices;
		this.totalPendingPolices = totalPendingPolices;
		this.totalDeclinePolices = totalDeclinePolices;
	}

	public Long getTotalPolices() {
		return totalPolices;
	}

	public void setTotalPolices(Long totalPolices) {
		this.totalPolices = totalPolices;
	}

	public Long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Long getTotalTypesOfPolices() {
		return totalTypesOfPolices;
	}

	public void setTotalTypesOfPolices(Long totalTypesOfPolices) {
		this.totalTypesOfPolices = totalTypesOfPolices;
	}

	public Long getTotalPendingPolices() {
		return totalPendingPolices;
	}

	public void setTotalPendingPolices(Long totalPendingPolices) {
		this.totalPendingPolices = totalPendingPolices;
	}

	public Long getTotalDeclinePolices() {
		return totalDeclinePolices;
	}

	public void setTotalDeclinePolices(Long totalDeclinePolices) {
		this.totalDeclinePolices = totalDeclinePolices;
	}

	@Override
	public String toString() {
		return "UnderWriterDto [totalPolices=" + totalPolices + ", totalUsers=" + totalUsers + ", totalTypesOfPolices="
				+ totalTypesOfPolices + ", totalPendingPolices=" + totalPendingPolices + ", totalDeclinePolices="
				+ totalDeclinePolices + "]";
	}

}
