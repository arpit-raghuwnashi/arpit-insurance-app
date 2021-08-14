package com.impetus.services.impl;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.impetus.dao.ClaimDao;
import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.DependentsDao;
import com.impetus.dao.NomineeDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.UserDao;
import com.impetus.dto.ClaimDTO;
import com.impetus.dto.InvoiceDataDTO;
import com.impetus.dto.UserPolicyDataDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.services.UserServices;
import com.impetus.utility.ExceptionUtils;

/**
 * User Services
 * 
 * @author kunal.sharma
 * @author mukul.pratap
 *
 */
@Service
public class UserServiceImpl implements UserServices {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private NomineeDao nomineeDao;

	@Autowired
	private DependentsDao dependentsDao;

	@Autowired
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ClaimDao claimDao;

	/**
	 * Adding a user
	 */
	@Override
	public User addUser(User user) {
		try {
			userDao.save(user);
		} catch (Exception e) {
			logger.info("Exception in User Registration");
		}
		return user;
	}

	/**
	 * Check if user already exists
	 * 
	 * @param user email
	 * @return Valid User details
	 */
	@Override
	public User checkUser(String email) {
		User user = null;
		try {
			user = userDao.findUserByEmail(email);
		} catch (Exception e) {
			logger.info("Exception in Check User");
		}
		return user;
	}

	/**
	 * Check if the user is valid
	 * 
	 * @param user email and Password
	 * @return Valid User details
	 */
	@Override
	public User login(String email, String password) {
		User user = null;
		try {
			user = userDao.login(email, password);
			if (user != null) {
				return user;
			}
		} catch (Exception e) {
			logger.info("Exception Ouccured during Login");
		}
		return user;
	}

	/**
	 * Sending OTP to the User
	 * 
	 * @return OTP sent
	 */
	@Override
	public int sendOtp() {
		return new SecureRandom().nextInt(1000000);
	}

	/**
	 * Check if the Nominee already exists
	 * 
	 * @param user UserId
	 * @return Nominee details.
	 */
	@Override
	public Nominee checkNominee(Long userId) {
		Nominee nominee = null;
		try {
			nominee = nomineeDao.findNomineeByUserId(userId);
		} catch (Exception e) {
			logger.info("Exception in Check Nominee");
		}
		return nominee;
	}

	/**
	 * Adds Nominee Details.
	 * 
	 * @param NomineeDTO details
	 * @return Registered nominee
	 */
	@Override
	public Nominee addNominee(Nominee nominee) {
		try {
			nomineeDao.save(nominee);
		} catch (Exception e) {
			logger.info("Exception Ouccured at Nominee Registration");
		}
		return nominee;
	}

	/**
	 * Add dependent details
	 * 
	 * @param dependent details
	 * @return registered dependent
	 */
	@Override
	public Dependent addDependents(Dependent dependents) {

		try {
			dependentsDao.save(dependents);
		} catch (Exception e) {
			logger.info("Exception Ouccured at dependents Registration");
		}
		return dependents;
	}

	/**
	 * Fetch the User by emailId
	 * 
	 * @param user emailId
	 * @return Valid User details
	 */
	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			user = userDao.findUserByEmail(email);
		} catch (Exception e) {
			logger.info("Exception ouccured while fetching user by email");
		}
		return user;
	}

	/**
	 * Add purchase details
	 * 
	 * @param Customer Policy Details
	 */
	@Override
	public CustomerPolicyDetails addCustomerPolicyDetails(CustomerPolicyDetails customerPolicyDetails) {

		try {
			customerPolicyDetailsDao.save(customerPolicyDetails);
		} catch (Exception e) {
			logger.info("Exception Ouccured while storing Customer Policy details");
		}
		return customerPolicyDetails;
	}

	/**
	 * Fetching Customer Policy Details
	 * 
	 * @return All customer policies
	 */
	@Override
	public List<CustomerPolicyDetails> getCustomerPolicyDetails() {
		List<CustomerPolicyDetails> listPolicyDetails = null;
		try {
			listPolicyDetails = customerPolicyDetailsDao.findAll();
		} catch (Exception e) {
			logger.info("Exception ouccured in Listing Policy Details");
		}
		return listPolicyDetails;
	}

	/**
	 * Fetch Producting by policyId
	 * 
	 * @param PolicyId
	 * @return Product List
	 */
	@Override
	public List<Product> getProductByPolicyId(long policyId) {
		List<Product> productList = null;
		try {
			productList = productDao.getProductByPolicyId(policyId);
		} catch (Exception e) {
			logger.info("Exception Ouccured fetching policy by Id");
		}
		return productList;
	}


	/**
	 * Checks user health records according to the Blood pressure , diabetes and
	 * Smoking
	 * 
	 * @param user
	 */
	public boolean healthRule(User user) {
		int count = 0;
		boolean flag = true;

		if (user.isHasBp()) {
			count++;
		}

		if (user.isIfSmoker()) {
			count++;
		}

		if (user.isIfDiabetic()) {
			count++;
		}

		if (count > 1) {
			flag = false;
		}
		return flag;
	}

	public int caluculateAgeFromDob(int dob) {
		int currentDate = Calendar.getInstance().get(Calendar.YEAR);
		return currentDate - dob;
	}


	public boolean checkAge(int userAge, Product product) {
		return ((product.getMaxAgeLimit() >= userAge) && (product.getMinAgeLimit() <= userAge));
	}


	public boolean checkDependent(Product product, CustomerPolicyDetails customerPolicyDetails) {
		return ((product.getMaxNumDependents() >= customerPolicyDetails.getNumberOfDependent())
				&& (product.getMinNumDependents() <= customerPolicyDetails.getNumberOfDependent()));
	}

	/**
	 * Checks if user is applicable for policy
	 * 
	 * @param customerPolicyDetails
	 */
	@Override
	public String checkApplication(CustomerPolicyDetails customerPolicyDetails) {
		String result = "";
		Product product = productDao.getProductByProductId(customerPolicyDetails.getProductId());
		User user = userDao.getUserById(customerPolicyDetails.getUserId());

		// calculate the user age from Date of Birth
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int yearOfBirth = Integer.parseInt(df.format(user.getDateOfBirth()));
		int userAge = this.caluculateAgeFromDob(yearOfBirth);
		// get user report approval according to Blood pressure,diabetes,smoking.
		boolean healthReport = this.healthRule(user);
		boolean ageCheck = this.checkAge(userAge, product);
		boolean dependentCheck = this.checkDependent(product, customerPolicyDetails);
		if (!healthReport) {
			return "User Health is not matched";
		} else if (!ageCheck) {
			return "User Age is not matched";
		} else if (!dependentCheck) {
			return "User dependents are not matched";
		} else {
			result = "User is Approved";
		}
		return result;
	}

	/**
	 * Fetching Product by productId
	 * 
	 * @param productID
	 * @return Product details
	 */
	@Override
	public Product getProductByProductId(long productId) {
		Product product = null;
		try {
			product = productDao.getProductByProductId(productId);
		} catch (Exception e) {
			logger.info("Exception Ouccured fetching policy by Product Id");
		}
		return product;
	}

	/**
	 * Check if the user already has the policy
	 * 
	 * @param ProductId and userId.
	 */
	@Override
	public List<CustomerPolicyDetails> checkUserWithProduct(long productId, long userId) {
		List<CustomerPolicyDetails> customerPolicyDetails = null;
		try {
			customerPolicyDetails = customerPolicyDetailsDao.checkUserWithOrder(productId, userId);
		} catch (Exception e) {
			logger.info("Exception ouccur in CheckUserWithProduct");
		}
		return customerPolicyDetails;
	}

	/**
	 * fetch the User Order details.
	 * 
	 * @param userID
	 * @return All orders for a user
	 */
	@Override
	public UserPolicyDataDTO fetchUserOderDetails(Long userId) {
		UserPolicyDataDTO userPolicyDataDto = new UserPolicyDataDTO();
		try {
			List<CustomerPolicyDetails> customerPolicyDetailsList = customerPolicyDetailsDao.fetchUserOrders(userId);
			List<Product> product = new ArrayList<>();
			List<CustomerPolicyDetails> customerPolicyDetailsArray = new ArrayList<>();
			for (int i = 0; i < customerPolicyDetailsList.size(); i++) {
				Product singleproduct = productDao
						.getProductByProductId(customerPolicyDetailsList.get(i).getProductId());
				product.add(singleproduct);
				CustomerPolicyDetails singleCustomerPolicyDetails = customerPolicyDetailsList.get(i);
				customerPolicyDetailsArray.add(singleCustomerPolicyDetails);
			}
			User user = userDao.getUserById(userId);
			userPolicyDataDto.setCustomerPolicyDetails(customerPolicyDetailsArray);
			userPolicyDataDto.setProduct(product);
			userPolicyDataDto.setUser(user);

		} catch (Exception e) {
			logger.info("Exception ouccur in Fech user Policeies");
		}
		return userPolicyDataDto;
	}

	/**
	 * Adds claim data to Database
	 * 
	 * @param claim data
	 */
	@Override
	public Claim addClaim(Claim claim) {
		Claim savedClaim = null;
		try {
			savedClaim = claimDao.save(claim);
		} catch (Exception e) {
			logger.info("Exception claim info");
		}
		return savedClaim;
	}

	/**
	 * Updates the claim status if the user claimed for Policy
	 * 
	 * @param orderID
	 */
	@Override
	public void updateClaimStatus(long orderId) {
		try {
			String status = "underProcess";
			customerPolicyDetailsDao.updateClaimStatus(status, orderId);
		} catch (Exception e) {
			logger.info("Exception in Claim Status Updataion ");
		}

	}

	/**
	 * Updated the Paid Amount/EMI
	 * 
	 * @param amount and orderId
	 */
	@Override
	public void payAmount(int amount, long orderId) {
		CustomerPolicyDetails orderDetails=null;
		try {
			orderDetails = customerPolicyDetailsDao.getOne(orderId);
			customerPolicyDetailsDao.payAmount(orderDetails.getAmountPaid() + amount, orderId, new Date());
		} catch (Exception e) {
			logger.info("Exception in Pay Amount");
		}
	}

	/**
	 * Updates/Resets user password
	 */
	@Override
	public void resetPassword(String email, String newPassword) {
		logger.info("Inside resetPassword method in UserServiceImpl");
		try {
			userDao.resetPassword(email, newPassword);
			logger.info("User password has been successfully updated");
		} catch (Exception e) {
			logger.error("Error inside resetPassword method in UserServiceImpl");
			throw new ExceptionUtils("Exception occurred while reseting passwrod " + e);
		}

	}

	/**
	 * Updates User Details
	 */
	@Override
	public User updateUser(User user) {
		logger.info("Inside Update Admin");
		User admin = null;
		try {
			Optional<User> optional = userDao.findById(user.getUserId());
			if (optional.isPresent()) {
				User user3 = optional.get();
				user.setCreatedDate(user3.getCreatedDate());
				user.setModifiedDate(new Date());
				admin = userDao.save(user);
				logger.info("Admin data Updated");
				return admin;
			}
		} catch (Exception e) {
			logger.info("Exception in updating Admin in User Service Impl");
		}
		return null;
	}

	@Override
	public InvoiceDataDTO getInvoiceData(long orderId) {
		InvoiceDataDTO invoiceData = new InvoiceDataDTO();
		CustomerPolicyDetails customerPolicyDetails = customerPolicyDetailsDao.getOne(orderId);
		User user = userDao.getUserById(customerPolicyDetails.getUserId());
		Product product = productDao.getProductByProductId(customerPolicyDetails.getProductId());
		Nominee nominee = nomineeDao.getOneNomineesByOrderId(orderId);
		List<Dependent> dependents = dependentsDao.getDependentsByOrderId(orderId);
		invoiceData.setCustomerPolicyDetails(customerPolicyDetails);
		
		invoiceData.setUser(user);
		invoiceData.setProduct(product);
		invoiceData.setNominee(nominee);
		invoiceData.setDependents(dependents);
		return invoiceData;
	}

	@Override
	public boolean checkNomineeByNomineeId(Long nomineeID) {
		boolean result =false;
		try {
			Nominee nominee =  nomineeDao.getOneNomineesByNomineeId(nomineeID);
			if(nominee != null)
			{
				result = true;
			}
		}catch(Exception e) {
			logger.info("Exception in check nominee by nominee Id");
		}
		return result;
	}

	@Override
	public boolean checkOrderId(long orderId) {
		boolean result =false;
		try {
			Optional<CustomerPolicyDetails> order =  customerPolicyDetailsDao.findById(orderId);
			if(order.isPresent())
			{
				result = true;
			}
		}catch(Exception e) {
			logger.info("Exception in Check order by OrderId Id");
		}
		return result;
	}
	
	@Override
	public boolean checkUserById(long userId) {
		boolean result =false;
		try {
			Optional<User> order = userDao.findById(userId);
			if(order.isPresent())
			{
				result = true;
			}
		}catch(Exception e) {
			logger.info("Exception in check user by user Id");
		}
		return result;
	}

	@Override
	public boolean verifyClaim(ClaimDTO claimDto) {
		boolean result = false;
		try {
			CustomerPolicyDetails order =  customerPolicyDetailsDao.getOne(claimDto.getOrderId());
		    Nominee nominee = nomineeDao.getOneNomineesByOrderId(claimDto.getOrderId());
		    if(order.getUserId() == nominee.getUserId() && order.getUserId()==claimDto.getUserId())
		    {
		    	result=true;
		    }
		} catch (Exception e) {
			logger.info("Exception in verify Claim");
		}
		return result;
	}

	@Override
	public void updatePolicyCount(long userId) {
		try {
			User user = userDao.getUserById(userId);
			int policyCount = user.getPolicyCount()+1;
			userDao.updatePolicyCount(policyCount, userId);
		} catch (Exception e) {
			logger.info("Exception in Update PolicyCount");
		}
		
	}
	

}
