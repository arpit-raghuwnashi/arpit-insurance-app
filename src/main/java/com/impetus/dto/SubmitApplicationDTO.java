package com.impetus.dto;

import java.util.Arrays;

import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;

public class SubmitApplicationDTO {
	private CustomerPolicyDetails customerPolicyDetails;
	private Nominee nominee;
	private Dependent[] dependent;

	public SubmitApplicationDTO(CustomerPolicyDetails customerPolicyDetails, Nominee nominee, Dependent[] dependent) {
		super();
		this.customerPolicyDetails = customerPolicyDetails;
		this.nominee = nominee;
		this.dependent = dependent;
	}

	public CustomerPolicyDetails getCustomerPolicyDetails() {
		return customerPolicyDetails;
	}

	public void setCustomerPolicyDetails(CustomerPolicyDetails customerPolicyDetails) {
		this.customerPolicyDetails = customerPolicyDetails;
	}

	public Nominee getNominee() {
		return nominee;
	}

	public void setNominee(Nominee nominee) {
		this.nominee = nominee;
	}

	public Dependent[] getDependent() {
		return dependent;
	}

	public void setDependent(Dependent[] dependent) {
		this.dependent = dependent;
	}

	@Override
	public String toString() {
		return "SubmitApplicationDto [customerPolicyDetails=" + customerPolicyDetails + ", nominee=" + nominee
				+ ", dependent=" + Arrays.toString(dependent) + "]";
	}

}
