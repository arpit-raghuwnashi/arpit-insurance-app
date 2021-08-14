package com.impetus.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.impetus.dto.ClaimDTO;

@Entity
@Table(name="claim")
public class Claim {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long claimId;
	private long userId;
	private long orderId;
	private long nomineeId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date createdDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date modifiedDate;
	private String nomineeName;
	private String customerName;
	private String claimStatus;

	public Claim(ClaimDTO claim) {
		super();
		this.claimId = claim.getClaimId();
		this.userId = claim.getUserId();
		this.orderId = claim.getOrderId();
		this.nomineeId = claim.getNomineeId();
		this.createdDate = claim.getCreatedDate();
		this.modifiedDate = claim.getModifiedDate();
		this.nomineeName = claim.getNomineeName();
		this.customerName = claim.getCustomerName();
		this.claimStatus = claim.getClaimStatus();
	}

	public Claim() {
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
		return "Claim [claimId=" + claimId + ", userId=" + userId + ", orderId=" + orderId + ", nomineeId=" + nomineeId
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", nomineeName=" + nomineeName
				+ ", customerName=" + customerName + ", claimStatus=" + claimStatus + "]";
	}

}
