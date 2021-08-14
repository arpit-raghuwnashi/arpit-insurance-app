package com.impetus.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.impetus.dto.CustomerPolicyDetailsDTO;

@Entity
@Table(name = "customerPolicyDetails")
public class CustomerPolicyDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;
	private int numberOfDependent;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date orderDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
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

	public CustomerPolicyDetails() {
		super();
	}

	public CustomerPolicyDetails(CustomerPolicyDetailsDTO customerPolicyDetails) {
		super();
		this.orderId = customerPolicyDetails.getOrderId();
		this.numberOfDependent = customerPolicyDetails.getNumberOfDependent();
		this.orderDate = customerPolicyDetails.getOrderDate();
		this.startDate = customerPolicyDetails.getStartDate();
		this.endDate = customerPolicyDetails.getEndDate();
		this.modifiedDate = customerPolicyDetails.getModifiedDate();
		this.premiumAmount = customerPolicyDetails.getPremiumAmount();
		this.discount = customerPolicyDetails.getDiscount();
		this.amountPaid = customerPolicyDetails.getAmountPaid();
		this.status = customerPolicyDetails.getClaimStatus();
		this.paymentFrequency = customerPolicyDetails.getPaymentFrequency();
		this.userId = customerPolicyDetails.getUserId();
		this.policyId = customerPolicyDetails.getPolicyId();
		this.productId = customerPolicyDetails.getProductId();
		this.claimStatus = customerPolicyDetails.getClaimStatus();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
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
