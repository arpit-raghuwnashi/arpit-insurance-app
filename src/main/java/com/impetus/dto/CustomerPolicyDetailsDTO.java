package com.impetus.dto;

import java.util.Date;

public class CustomerPolicyDetailsDTO {
	private long orderId;
	private int numberOfDependent;
	private Date orderDate;
	private Date startDate;
	private Date endDate;
	private Date modifiedDate;
	private int premiumAmount;
	private int discount;
	private int amountPaid;
	private String status;
	private String paymentFrequency;
	private long userId;
	private long productId;
	private long policyId;
	private String claimStatus;

	public CustomerPolicyDetailsDTO() {
		super();
	}

	public CustomerPolicyDetailsDTO(CustomerPolicyDetailsDTO customerPolicyDetails) {
		super();
		this.orderId = customerPolicyDetails.orderId;
		this.numberOfDependent = customerPolicyDetails.numberOfDependent;
		this.orderDate = customerPolicyDetails.orderDate;
		this.startDate = customerPolicyDetails.startDate;
		this.endDate = customerPolicyDetails.endDate;
		this.modifiedDate = customerPolicyDetails.modifiedDate;
		this.premiumAmount = customerPolicyDetails.premiumAmount;
		this.discount = customerPolicyDetails.discount;
		this.amountPaid = customerPolicyDetails.amountPaid;
		this.status = customerPolicyDetails.status;
		this.paymentFrequency = customerPolicyDetails.paymentFrequency;
		this.userId = customerPolicyDetails.userId;
		this.productId = customerPolicyDetails.productId;
		this.policyId = customerPolicyDetails.policyId;
		this.productId = customerPolicyDetails.productId;
		this.claimStatus = customerPolicyDetails.claimStatus;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getNumberOfDependent() {
		return numberOfDependent;
	}

	public void setNumberOfDependent(int numberOfDependent) {
		this.numberOfDependent = numberOfDependent;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(int premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	@Override
	public String toString() {
		return "CustomerPolicyDetails [orderId=" + orderId + ", numberOfDependent=" + numberOfDependent + ", orderDate="
				+ orderDate + ", startDate=" + startDate + ", endDate=" + endDate + ", modifiedDate=" + modifiedDate
				+ ", premiumAmount=" + premiumAmount + ", discount=" + discount + ", amountPaid=" + amountPaid
				+ ", status=" + status + ", paymentFrequency=" + paymentFrequency + ", userId=" + userId
				+ ", product_id=" + productId + ", policy_id=" + policyId + ", claimId" + claimStatus + "]";
	}

}
