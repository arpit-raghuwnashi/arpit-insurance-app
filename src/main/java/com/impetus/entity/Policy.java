package com.impetus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "policy")
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long policyId;

	private String policyType;

	public Policy() {
	}

	public Policy(Long policyId, String policyType) {
		super();
		this.policyId = policyId;
		this.policyType = policyType;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	@Override
	public String toString() {
		return "Policy [policyId=" + policyId + ", policyType=" + policyType + "]";
	}

}
