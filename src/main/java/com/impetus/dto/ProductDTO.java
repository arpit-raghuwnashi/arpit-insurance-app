package com.impetus.dto;

import java.util.Date;

public class ProductDTO {
	private long productId;
	private String productName;
	private long policyId;
	private int minAgeLimit;
	private int maxAgeLimit;
	private int numberOfYearsCovered;
	private String productDescription;
	private Date createdDate;
	private Date modifiedDate;
	private long sumAssured;
	private int minNumDependents;
	private int maxNumDependents;

	public ProductDTO() {
		super();
	}

	public ProductDTO(ProductDTO product) {
		super();
		this.productId = product.productId;
		this.productName = product.productName;
		this.policyId = product.policyId;
		this.minAgeLimit = product.minAgeLimit;
		this.maxAgeLimit = product.maxAgeLimit;
		this.numberOfYearsCovered = product.numberOfYearsCovered;
		this.productDescription = product.productDescription;
		this.createdDate = product.createdDate;
		this.modifiedDate = product.modifiedDate;
		this.sumAssured = product.sumAssured;
		this.minNumDependents = product.minNumDependents;
		this.maxNumDependents = product.maxNumDependents;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

	public int getMinAgeLimit() {
		return minAgeLimit;
	}

	public void setMinAgeLimit(int minAgeLimit) {
		this.minAgeLimit = minAgeLimit;
	}

	public int getMaxAgeLimit() {
		return maxAgeLimit;
	}

	public void setMaxAgeLimit(int maxAgeLimit) {
		this.maxAgeLimit = maxAgeLimit;
	}

	public int getNumberOfYearsCovered() {
		return numberOfYearsCovered;
	}

	public void setNumberOfYearsCovered(int numberOfYearsCovered) {
		this.numberOfYearsCovered = numberOfYearsCovered;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
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

	public long getSumAssured() {
		return sumAssured;
	}

	public void setSumAssured(long sumAssured) {
		this.sumAssured = sumAssured;
	}

	public int getMinNumDependents() {
		return minNumDependents;
	}

	public void setMinNumDependents(int minNumDependents) {
		this.minNumDependents = minNumDependents;
	}

	public int getMaxNumDependents() {
		return maxNumDependents;
	}

	public void setMaxNumDependents(int maxNumDependents) {
		this.maxNumDependents = maxNumDependents;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", policyId=" + policyId
				+ ", minAgeLimit=" + minAgeLimit + ", maxAgeLimit=" + maxAgeLimit + ", numberOfYearsCovered="
				+ numberOfYearsCovered + ", productDescription=" + productDescription + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", sumAssured=" + sumAssured + ", minNumDependents="
				+ minNumDependents + ", maxNumDependents=" + maxNumDependents + "]";
	}

}
