package com.impetus.dto;

import java.util.Date;

public class ClaimDTO {
	private long claimId;
	private long userId;
	private long orderId;
	private long nomineeId;
	private Date createdDate;
	private Date modifiedDate;
	private String nomineeName;
	private String customerName;
	private String claimStatus;

	public ClaimDTO(ClaimDTO claim) {
		super();
		this.claimId = claim.claimId;
		this.userId = claim.userId;
		this.orderId = claim.orderId;
		this.nomineeId = claim.nomineeId;
		this.createdDate = claim.createdDate;
		this.modifiedDate = claim.modifiedDate;
		this.nomineeName = claim.nomineeName;
		this.customerName = claim.customerName;
		this.claimStatus = claim.claimStatus;
	}

	public ClaimDTO() {
		super();
	}

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(long nomineeId) {
		this.nomineeId = nomineeId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	@Override
	public String toString() {
		return "ClaimDTO [claimId=" + claimId + ", userId=" + userId + ", orderId=" + orderId + ", nomineeId="
				+ nomineeId + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", nomineeName="
				+ nomineeName + ", customerName=" + customerName + ", claimStatus=" + claimStatus + "]";
	}
}
