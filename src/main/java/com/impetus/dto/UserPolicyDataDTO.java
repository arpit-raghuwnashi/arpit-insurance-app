package com.impetus.dto;

import java.util.List;

import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Product;
import com.impetus.entity.User;

 
/**
 * @author arpit.raghuwanshi
 * Show the data of user policies
 */
public class UserPolicyDataDTO {
	private List<CustomerPolicyDetails> customerPolicyDetails;
	private List<Product> product;
	private com.impetus.entity.User user;
	
	public UserPolicyDataDTO() {
	}
	
	public UserPolicyDataDTO(List<Product> product, com.impetus.entity.User user,List<CustomerPolicyDetails> customerPolicyDetails) {
		super();
		this.product = product;
		this.user = user;
		this.customerPolicyDetails = customerPolicyDetails;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CustomerPolicyDetails> getCustomerPolicyDetails() {
		return customerPolicyDetails;
	}

	public void setCustomerPolicyDetails(List<CustomerPolicyDetails> customerPolicyDetails) {
		this.customerPolicyDetails = customerPolicyDetails;
	}

}
