package com.impetus.dto;

import java.util.List;

import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;

public class InvoiceDataDTO {

	private User user;
	private Product product;
	private CustomerPolicyDetails customerPolicyDetails;
	private Nominee nominee;
	private List<Dependent> dependents;

	public InvoiceDataDTO() {
	}

	public InvoiceDataDTO(User user, Product product, CustomerPolicyDetails customerPolicyDetails, Nominee nominee,
			List<Dependent> dependents) {
		super();
		this.user = user;
		this.product = product;
		this.customerPolicyDetails = customerPolicyDetails;
		this.nominee = nominee;
		this.dependents = dependents;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public List<Dependent> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependent> dependents) {
		this.dependents = dependents;
	}

	@Override
	public String toString() {
		return "InvoiceDataDto [user=" + user + ", product=" + product + ", customerPolicyDetails="
				+ customerPolicyDetails + ", nominee=" + nominee + ", dependents=" + dependents + "]";
	}

}
