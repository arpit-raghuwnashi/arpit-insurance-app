package com.impetus.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.impetus.dto.ProductDTO;

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long productId;
	private String productName;
	private long policyId;
	private int minAgeLimit;
	private int maxAgeLimit;
	private int numberOfYearsCovered;
	private String productDescription;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date modifiedDate;
	private long sumAssured;
	private int minNumDependents;
	private int maxNumDependents;

	public Product() {
		super();
	}

	public Product(ProductDTO product) {
		super();
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.policyId = product.getPolicyId();
		this.minAgeLimit = product.getMinAgeLimit();
		this.maxAgeLimit = product.getMaxAgeLimit();
		this.numberOfYearsCovered = product.getNumberOfYearsCovered();
		this.productDescription = product.getProductDescription();
		this.createdDate = product.getCreatedDate();
		this.modifiedDate = product.getModifiedDate();
		this.sumAssured = product.getSumAssured();
		this.minNumDependents = product.getMinNumDependents();
		this.maxNumDependents = product.getMaxNumDependents();
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
				+ ", minAgeLimit=" + minAgeLimit + ", maxAgeLimit=" + maxAgeLimit + ", numberOfearsCovered="
				+ numberOfYearsCovered + ", productDescription=" + productDescription + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", sumAssured=" + sumAssured + ", minNumDependents="
				+ minNumDependents + ", maxNumDependents=" + maxNumDependents + "]";
	}

}
