package com.impetus.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.impetus.dto.AdminDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;

@Service
public interface AdminServices {

	public List<Product> getProducts();

	public Product updateProduct(Product product);

	public Product deleteProduct(long productId);

	public Product addProduct(Product product);

	public boolean checkProductIsDeletable(long productId);

	public List<User> getAllUnderWriters(long roleId);

	public boolean login(String email, String password, int roleid);

	public boolean checkUnderwriter(String email);

	public boolean checkUnderwriterByUserId(long id);

	public List<User> customers();

	public boolean checkCustomer(long userId);

	public List<Dependent> dependents(long orderId);

	public List<Nominee> getNominee(long orderId);

	public AdminDTO total();

	public List<CustomerPolicyDetails> userPolicyDetails(long userId);

	public List<Product> getProduct(long productId);

	public List<Claim> getClaims();

	public List<CustomerPolicyDetails> userPolicyDetailByOrderId(long orderId);

	public Claim getClaimByClaimId(long claimId);

	public Claim updateClaim(Claim claim);
}
