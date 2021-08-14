package com.impetus.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.impetus.dto.AdminDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.services.AdminServices;
import com.impetus.utility.ExceptionUtils;

/**
 * Admin Services Implementation
 * 
 * @author nikhar.jain
 * @author rashi.jain
 *
 */
@Service
public class AdminServicesImpl implements AdminServices {

	private static final Logger logger = LoggerFactory.getLogger(AdminServicesImpl.class);

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private NomineeDao nomineeDao;

	@Autowired
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	@Autowired
	private ClaimDao claimDao;

	@Autowired
	private DependentsDao dependentsDao;

	/**
	 * Product Service Default Constructor
	 * 
	 * @author nikhar.jain
	 */
	public AdminServicesImpl() {
		super();
	}

	/**
	 * Adds Product to Database
	 * 
	 * @author nikhar.jain
	 * @return Product added
	 */
	@Override
	public Product addProduct(Product product) {
		productDao.save(product);
		logger.info("Inside addProduct method of Product service");
		return product;
	}

	/**
	 * Get all Products
	 * 
	 * @author nikhar.jain
	 * @return List of all products
	 */
	@Override
	public List<Product> getProducts() {
		logger.info("Inside getProducts method of Product service");
		return productDao.findAll();
	}

	/**
	 * Updates Product in Database
	 * 
	 * @author nikhar.jain
	 * @return Updated Product
	 */
	@Override
	public Product updateProduct(Product product) {
		logger.info("Inside updateProduct method of Product service");
		productDao.save(product);
		return product;
	}

	/**
	 * Deletes Product
	 * 
	 * @author nikhar.jain
	 * @return Deleted Product
	 */
	@Override
	public Product deleteProduct(long productId) {
		logger.info("Inside deleteProduct method of Product service");
		Product product = productDao.getProductByProductId(productId);
		productDao.delete(product);
		return product;
	}

	/**
	 * Checks if product can be deleted
	 * 
	 * @author nikhar.jain
	 * @param productId of {@link Product} to be deleted
	 * @return Boolean if product can be deleted
	 */
	@Override
	public boolean checkProductIsDeletable(long productId) {
		logger.info("Checking if product is deletable");
		Product product = productDao.getProductByProductId(productId);

		List<CustomerPolicyDetails> customersWithGivenProduct = productDao.getCustomersWithProductId(productId);
		if (product == null) {
			logger.warn("Cannot delete: No such product found");
			return false;
		} else if (!customersWithGivenProduct.isEmpty()) {
			logger.warn("Cannot delete: One or more Customer has purchased the product");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Fetching all active underwriters from DataBase
	 */

	@Override
	public List<User> getAllUnderWriters(long roleId) {
		logger.info("Inside of getAllUnderWriters Method of Admin Services");
		List<User> users = null;
		try {
			if (roleId == 2) {
				users = userDao.getAllUnderwriter(roleId, true);
			} else {
				throw new ExceptionUtils("RoleId is not valid");
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching active underwriters");
			return new ArrayList<>();
		}
		return users;
	}

	/**
	 * Checks Admin Credentials
	 */
	@Override
	public boolean login(String email, String password, int roleId) {
		logger.info("Inside of login Method of Admin Services");
		boolean flag = false;
		try {
			User admin = null;
			admin = userDao.findAdminByEmail(email, roleId);
			if (admin != null && admin.getPassword().equals(password)) {

				flag = true;
			}
		} catch (Exception e) {
			logger.error("Problem occurred while matching credentials of Admin login");
		}
		return flag;
	}

	/**
	 * Check if Underwriter exists using email
	 */
	@Override
	public boolean checkUnderwriter(String email) {
		logger.info("Inside checkUnderwriter Method of Admin Services");

		User underwriter = userDao.findUnderwriterByEmail(email, 2);
		return underwriter == null;

	}

	/**
	 * Checks if Underwriter exists or not using id
	 */
	@Override
	public boolean checkUnderwriterByUserId(long id) {
		logger.info("Inside checkUnderwriterByUserId Method of admin services");

		User underwriter = userDao.findUnderwriterById(id, 2);
		return underwriter == null;
	}

	/**
	 * Fetches all Customers from Database
	 * 
	 * @return all customers
	 */
	@Override
	public List<User> customers() {
		logger.info("Inside customers Method of Admin Services");
		List<User> users = null;
		try {
			users = userDao.findByRoleId(3);
			if (!users.isEmpty()) {
				users = users.stream().filter(customer -> customer.getPolicyCount() > 0).collect(Collectors.toList());

			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching customers in admin services");
			return new ArrayList<>();
		}
		return users;
	}

	/**
	 * Check if user is a customer or not
	 */
	@Override
	public boolean checkCustomer(long userId) {
		logger.info("Inside checkCustomer Method of Admin Services");
		boolean flag = false;
		User user = null;
		user = userDao.getUserById(userId);
		if (user != null && user.getPolicyCount() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Fetching Dependents using orderId
	 * 
	 * @return dependents
	 */
	@Override
	public List<Dependent> dependents(long orderId) {
		logger.info("Inside dependents Method of Admin Services");
		List<Dependent> dependents = null;
		try {
			dependents = dependentsDao.getDependentsByOrderId(orderId);
			if(dependents==null) {
				throw new ExceptionUtils("No dependent found");
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching dependents in admin services");
			return new ArrayList<>(); 
		}
		return dependents;
	}

	/**
	 * Fetching Nominee using orderId
	 * 
	 * @return nominee
	 */
	@Override
	public List<Nominee> getNominee(long orderId) {
		logger.info("Inside getNominee Method of Admin Services");
		List<Nominee> nominee = null;
		try {
			nominee = nomineeDao.getNomineesByOrderId(orderId);
			if(nominee==null) {
				throw new ExceptionUtils("No Nominee found");
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching nominee in admin services");
			return new ArrayList<>();
		}
		return nominee;
	}

	/**
	 * Fetching DashBoard Data for Admin
	 */
	@Override
	public AdminDTO total() {
		logger.info("Inside total Method of Admin Services");
		AdminDTO admin = new AdminDTO();
		try {
			admin.setTotalUsers(userDao.totalUsers(3));
			admin.setTotalUnderwriters(userDao.totalUnderwriters(2, true));
			admin.setTotalCustomers(userDao.totalCustomers(3));
			admin.setTotalPendingPolicies(customerPolicyDetailsDao.getPendingCustomerPolicyCount("Pending"));
			admin.setTotalPolicies(productDao.totalProducts());
			admin.setTotalSuccessfulClaims(claimDao.totalSuccessfulClaims("Claimed"));
			admin.setTotalTypeOfPolicies(3);
		} catch (Exception e) {
			logger.error("Problem occurred while counting in admin services");
		}
		return admin;
	}

	/**
	 * Fetching customer policy details using userId
	 */
	@Override
	public List<CustomerPolicyDetails> userPolicyDetails(long userId) {
		logger.info("Inside userPolicyDetails Method of Admin Services");
		List<CustomerPolicyDetails> customerPolicyDetails = null;
		try {
			customerPolicyDetails = customerPolicyDetailsDao.fetchUserOrders(userId);
			if(customerPolicyDetails==null) {
				throw new ExceptionUtils("User Order not found");
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching customer policy details in admin services");
			return new ArrayList<>();
		}

		return customerPolicyDetails;

	}

	/**
	 * Fetching product by productId
	 */
	@Override
	public List<Product> getProduct(long productId) {
		logger.info("Inside getProduct Method of Admin Services");
		List<Product> product = null;
		try {
			
			product = productDao.getProductByProductIdList(productId);
			if(product==null) {
				throw new ExceptionUtils("Product list is null");
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching product in admin services");
			return new ArrayList<>();
		}
		return product;
	}

	/**
	 * Fetching All Claims from Database
	 * 
	 * @return all claims
	 */
	public List<Claim> getClaims() {
		logger.info("Inside getClaims Method of Admin Services");
		List<Claim> claims = null;
		try {
			claims = claimDao.findAll();
		} catch (Exception e) {
			logger.error("Problem occurred while fetching claims in admin services");
		}
		return claims;
	}

	/**
	 * Fetching all Customer policies
	 * 
	 * @return customer policy detail
	 */
	public List<CustomerPolicyDetails> userPolicyDetailByOrderId(long orderId) {
		logger.info("Inside userPolicyDetailByOrderId Method of Admin Services");
		List<CustomerPolicyDetails> customerPolicyDetail = null;
		try {
			customerPolicyDetail = customerPolicyDetailsDao.fetchUserOrderByOrderId(orderId);
			if(customerPolicyDetail==null) {
				throw new ExceptionUtils("Customer policy details is null");
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching customer policy detail in admin services");
			return new ArrayList<>();
		}

		return customerPolicyDetail;
	}

	/**
	 * Fetching Claim by claimId
	 * 
	 * @return claim
	 */
	public Claim getClaimByClaimId(long claimId) {
		logger.info("Inside getClaimByClaimId Method of Admin Services");
		Claim claim = null;
		try {
			claim = claimDao.getClaimRecord(claimId);
		} catch (Exception e) {
			logger.error("Problem occurred while fetching claim in admin services");
		}
		return claim;
	}

	/**
	 * Updating Claim Details
	 * 
	 * @return updated claim record
	 */
	public Claim updateClaim(Claim claim) {
		logger.info("Inside updateClaim Method of Admin Services");
		try {
			customerPolicyDetailsDao.updateClaimStatus("Claimed",claim.getOrderId());
			claim = claimDao.save(claim);
		} catch (Exception e) {
			logger.error("Problem occurred while updating claim in admin services");
		}
		return claim;
	}
}
