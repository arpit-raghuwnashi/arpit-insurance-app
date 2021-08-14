package com.impetus.controller;

import static com.impetus.utility.InsuranceConstant.UI_MAPPING_URL;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.dao.ProductDao;
import com.impetus.dto.AdminDTO;
import com.impetus.dto.ProductDTO;
import com.impetus.dto.UserDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.services.AdminServices;
import com.impetus.services.UserServices;
import com.impetus.utility.CustomResponse;
import com.impetus.utility.ExceptionUtils;
import com.impetus.utility.SendEmailUtilis;

/**
 * @author rashi.jain
 * @author nikhar.jain
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminServices adminService;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserServices userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Testing API
	 * 
	 * @author nikhar.jain
	 * @return Welcome message for testing
	 */
	@GetMapping("/home")
	public String home() {
		logger.info("Inside home method of Admin controller");
		return "Welcome Home";
	}

	/**
	 * Getting all products available
	 * 
	 * @return List of products
	 * @author nikhar.jain
	 */

	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		logger.info("Inside get products method of Admin controller");
		List<Product> list = this.adminService.getProducts();
		if (list.isEmpty()) {
			logger.warn("No products available, list is empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			logger.info("Product list returned");
			return ResponseEntity.of(Optional.of(list));
		}
	}

	/**
	 * Adding Product to Database
	 * 
	 * @param product
	 * @author nikhar.jain
	 * @return Product added
	 */
	
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/products")
	public ResponseEntity<CustomResponse> addProduct(@RequestBody ProductDTO productDto) {
		logger.info("Inside Add product method of Admin controller");

		Product product = new Product(productDto);

		try {
			product.setCreatedDate(new Date());
			product.setModifiedDate(new Date());
			Product addedProduct = this.adminService.addProduct(product);
			CustomResponse customResponse = new CustomResponse(HttpStatus.CREATED.value(), "Product added Successfully",
					addedProduct);
			logger.info("Product added");
			return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.warn("Exception in Add product method of Admin controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not Add Product");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updating Product
	 * 
	 * @param product Product to be updated
	 * @author nikhar.jain
	 * @return Updated Product
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PutMapping("/updateproduct")
	public ResponseEntity<CustomResponse> updateProduct(@RequestBody ProductDTO productDto) {
		logger.info("Inside update product method of Admin controller");

		Product product = new Product(productDto);

		if (productDao.getProductByProductId(product.getProductId()) != null) {
			product.setModifiedDate(new Date());
			Product updatedProduct = this.adminService.updateProduct(product);
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Updated Product",
					updatedProduct);
			logger.info("Product Updated");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			logger.warn("Exception in Update product method of Admin controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Product With Given Id not found");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deleting a Product
	 * 
	 * @param productId Id of Product to be deleted
	 * @author nikhar.jain
	 * @return Deleted Product
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@DeleteMapping("/deleteproduct/{productId}")
	public ResponseEntity<CustomResponse> deleteProduct(@PathVariable("productId") long productId) {
		logger.info("Inside delete product method of Admin controller");

		if (adminService.checkProductIsDeletable(productId)) {
			Product deletedProduct = this.adminService.deleteProduct(productId);
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Deleted Product",
					deletedProduct);
			logger.info("Product Deleted");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			logger.warn("Exception in delete product method of Admin controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.CONFLICT.value(),
					"Product Cannot be Deleted");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}
	}

	/**
	 * Registers Underwriter and adds into Database
	 * 
	 * @param user
	 * @author rashi.jain
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/underwriterregister")
	public ResponseEntity<CustomResponse> register(@RequestBody UserDTO userDto) throws MessagingException {
		logger.info("Inside Underwriter Registration Method");

		User user = new User(userDto);

		boolean result = adminService.checkUnderwriter(user.getEmail());
		try {
			if (result) {
				user.setCreatedDate(new Date());
				user.setModifiedDate(new Date());
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setStatus(true);
				user.setRoleId(2);
				User u = this.userService.addUser(user);
				if (u != null) {
					SendEmailUtilis.sendmail(u.getEmail(), "Hi, Credentials for underwriter login ",
							"Email is: " + u.getEmail() + "  Password: " + userDto.getPassword());
					CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
							"Underwriter Registered and Mail Sent Sucessfully", u);
					return new ResponseEntity<>(customResponse, HttpStatus.OK);
				} else {
					throw new ExceptionUtils("User not registered");
				}
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
						"Underwriter Exists Already");
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while registering underwriter");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could Not Register Underwriter");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Fetches underwriters from Database
	 * 
	 * @param roleId
	 * @author rashi.jain
	 * @return underwriters
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/viewunderwriters/{roleId}")
	public ResponseEntity<CustomResponse> view(@PathVariable long roleId) {
		logger.info("Inside View Underwriters Method");

		List<User> underwriters = null;
		try {
			underwriters = adminService.getAllUnderWriters(roleId);
			boolean isEmpty = underwriters.isEmpty();

			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(), "Underwriters Are Present",
						underwriters);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Underwriters Are Not Present", underwriters);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Problem occurred while fetching underwriters");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch underwriters");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Fetches all customers from Database
	 * 
	 * @author nikhar.jain
	 * @return customers
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]','[ROLE_UNDERWRITER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/fetchCustomers")
	public ResponseEntity<CustomResponse> getCustomers() {
		logger.info("Inside getCustomers Method");
		List<User> customers = null;
		try {

			customers = adminService.customers();
			boolean isEmpty = customers.isEmpty();
			if (!isEmpty) {

				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(), "Customers Are Present",
						customers);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);

			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Customers Are Not Present", customers);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching customers");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch customers");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	/**
	 * Fetches dependents from orderId
	 * 
	 * @param orderId
	 * @return dependents
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/fetchDependents")
	public ResponseEntity<CustomResponse> getDependents(@RequestParam long orderId) {
		logger.info("Inside getDependents method of Admin Controller");
		List<Dependent> dependents = null;
		try {
			dependents = adminService.dependents(orderId);
			boolean isEmpty = dependents.isEmpty();
			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(), "Dependents Are Present",
						dependents);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);

			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Dependents Are Not Present", dependents);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching dependents");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch dependents");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/**
	 * Fetches nominees from orderId
	 * 
	 * @param orderId
	 * @return nominee
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/fetchNominee")
	public ResponseEntity<CustomResponse> getNominee(@RequestParam long orderId) {
		logger.info("Inside getNominee Method of Admin controller");
		List<Nominee> nominee = null;
		try {

			nominee = adminService.getNominee(orderId);
			boolean isEmpty = nominee.isEmpty();
			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(), "Nominee Is Present",
						nominee);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Nominee Is Not Present", nominee);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching nominee");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch nominee");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Fetches Data for DashBoard
	 * 
	 * @return count
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/total")
	public ResponseEntity<CustomResponse> totalCount() {
		logger.info("Inside totalCount Method of Admin Controller");

		try {
			AdminDTO admin = adminService.total();
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"Counting has been done successfully", admin);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Problem occurred while counting");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not count");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Fetches details of Customer Policy
	 * 
	 * @param userId
	 * @return customer policy details
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/customerPolicyDetails")
	public ResponseEntity<CustomResponse> getCustomerPolicyDetails(@RequestParam long userId) {
		logger.info("Inside customerPolicyDetails Method of Admin Controller");
		List<CustomerPolicyDetails> policyDetails = null;
		try {

			policyDetails = adminService.userPolicyDetails(userId);
			boolean isEmpty = policyDetails.isEmpty();
			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(),
						"Customer Policy Details Are Present", policyDetails);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Customer Policy Details Are Not Present", policyDetails);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching customer policy details");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch customer policy details");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Fetches product by it's Id
	 * 
	 * @param productId
	 * @return product
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/fetchProduct")
	public ResponseEntity<CustomResponse> getProduct(@RequestParam long productId) {
		logger.info("Inside fetchProduct Method of Admin Controller");
		List<Product> product = null;
		try {
			product = adminService.getProduct(productId);
			boolean isEmpty = product.isEmpty();
			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(), "Product Is Present",
						product);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Product Is Not Present", product);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Problem occurred while fetching product");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch product");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Fetches all claims from Database
	 * 
	 * @return claims
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/fetchClaims")
	public ResponseEntity<CustomResponse> getAllClaims() {
		logger.info("Inside fetchClaimDetails Method of Admin Controller");
		List<Claim> claims = null;
		try {
			claims = adminService.getClaims();
			boolean isEmpty = claims.isEmpty();
			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(), "Claims Are Present",
						claims);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Claims Are Not Present", claims);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("Problem occurred while fetching product");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch Claim Detatails");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Fetches Customer Policy Details By OrderId from Database
	 * 
	 * @param orderId
	 * @return customer policy detail
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/customerPolicyDetailByOrderId")
	public ResponseEntity<CustomResponse> getCustomerPolicyDetailsByOrderId(@RequestParam long orderId) {
		logger.info("Inside customerPolicyDetailByOrderId Method of Admin Controller");
		List<CustomerPolicyDetails> policyDetail = null;
		try {

			policyDetail = adminService.userPolicyDetailByOrderId(orderId);
			boolean isEmpty = policyDetail.isEmpty();
			if (!isEmpty) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(),
						"Customer Policy Detail Is Present", policyDetail);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Customer Policy Detail Is Not Present", policyDetail);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching customer policy detail");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch customer policy detail");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Updates Claim Status
	 * 
	 * @param claimId
	 * @return updated claim record
	 */
	@PreAuthorize("hasAuthority('[ROLE_ADMIN]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/updateClaimStatus")
	public ResponseEntity<CustomResponse> updateClaimStatus(@RequestParam long claimId) {
		logger.info("Inside updateClaimStatus Method of Admin Controller");
		Claim claim = null;
		try {

			claim = adminService.getClaimByClaimId(claimId);

			if (claim != null) {
				claim.setClaimStatus("Claimed");
				claim = adminService.updateClaim(claim);
				CustomResponse customResponse = new CustomResponse(HttpStatus.FOUND.value(),
						"Claim Record Is Updated Succesfully", claim);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
						"Claim Record Is Not Present", claim);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Problem occurred while fetching claim record");
			CustomResponse customResponse = new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Could not fetch claim record");
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
