package com.impetus.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.impetus.dto.ClaimDTO;
import com.impetus.dto.InvoiceDataDTO;
import com.impetus.dto.UserPolicyDataDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;

@Service
public interface UserServices {
	public User addUser(User user);

	public User checkUser(String email);

	public User login(String email, String password);

	public int sendOtp();

	public Nominee checkNominee(Long userId);

	public Nominee addNominee(Nominee nominee);

	public Dependent addDependents(Dependent dependents);

	public User getUserByEmail(String email);

	public User updateUser(User user);

	public CustomerPolicyDetails addCustomerPolicyDetails(CustomerPolicyDetails customerPolicyDetails);

	public List<CustomerPolicyDetails> getCustomerPolicyDetails();

	public List<Product> getProductByPolicyId(long policyId);

	public Product getProductByProductId(long productId);

	public String checkApplication(CustomerPolicyDetails customerPolicyDetails);

	public List<CustomerPolicyDetails> checkUserWithProduct(long orderId, long userId);

	public UserPolicyDataDTO fetchUserOderDetails(Long userId);

	public Claim addClaim(Claim claim);

	public void updateClaimStatus(long orderId);

	public void payAmount(int amount, long orderId);

	public void resetPassword(String email, String newPassword);
	 
	public InvoiceDataDTO getInvoiceData(long orderId);

	public boolean checkNomineeByNomineeId(Long nomineeID);

	public boolean checkOrderId(long orderId);

	public boolean checkUserById(long userId);

	public boolean verifyClaim(ClaimDTO claimDto);

	public void updatePolicyCount(long userId);


}
