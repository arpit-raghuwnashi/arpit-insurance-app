package com.impetus.controller;

import static com.impetus.utility.InsuranceConstant.UI_MAPPING_URL;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.dto.ClaimDTO;
import com.impetus.dto.ContactUsDTO;
import com.impetus.dto.CustomerPolicyDetailsDTO;
import com.impetus.dto.InvoiceDataDTO;
import com.impetus.dto.NomineeDTO;
import com.impetus.dto.ProductDTO;
import com.impetus.dto.SubmitApplicationDTO;
import com.impetus.dto.UserDTO;
import com.impetus.dto.UserPolicyDataDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.services.UserServices;
import com.impetus.utility.Credentials;
import com.impetus.utility.CustomResponse;
import com.impetus.utility.SendEmailUtilis;

/**
 * @author kunal.sharma
 * @author mukul.pratap
 */

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
		MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	// it will inject object automatically
	@Autowired
	private UserServices userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// for storing the one time password
	private int otp;

	/**
	 * Registers User and stores in Database
	 * 
	 * @param UserDTO data
	 */

	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/userRegistration")
	public ResponseEntity<CustomResponse> register(@Valid @RequestBody UserDTO userDto) {
		// it checks that user exist or not
		User user = new User(userDto);

		User result = userService.checkUser(user.getEmail());
		// if user is not exist than we will register the user.
		if (result == null) {
			// saving the user
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setCreatedDate(new Date());
			user.setModifiedDate(new Date());
			user.setStatus(true);
			this.userService.addUser(user);

			// sending the Custom Response in {code:200,message:"Your Message",data:[]} this
			// format.
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "User Registered Sucessfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
					"User Exist Already");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Updates User Details
	 * 
	 * @param UserDTO data
	 * @return Updated User Details
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PutMapping("/register")
	public ResponseEntity<CustomResponse> updateUser(@RequestBody UserDTO userDto) {
		// it checks that user exist or not

		User user = new User(userDto);

		User singleUser = userService.getUserByEmail(user.getEmail());
		if (singleUser != null) {
			user.setModifiedDate(new Date());
			this.userService.addUser(user);
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "User updated Sucessfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
					"User Not Updated Successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
		}
	}

	/**
	 * Checking User Credentials
	 * 
	 * @param UserName and Password
	 * @return Valid User details
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/login")
	public ResponseEntity<CustomResponse> loginUser(@RequestBody UserDTO userDto) {

		User user = new User(userDto);

		User loginUser = userService.login(user.getEmail(), user.getPassword());
		if (loginUser != null) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Login Successfully", loginUser);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
					"Wrong Credentials");
			return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
		}
	}

	/**
	 * @param Valid email address for sending OTP
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/sendOtp/{email}")
	public ResponseEntity<CustomResponse> sendOtp(@PathVariable String email) {
		logger.info("Inside sendOtp method in UserContoller");
		otp = userService.sendOtp();

		// check user already exist in database.
		User result = userService.checkUser(email);
		if (result != null) {
			SendEmailUtilis.properMailFormat(email, otp);
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"Otp has been sent successfully to your mail");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"You are not registered user");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Verify OTP sent to user's email.
	 * 
	 * @param OTP
	 * @return Verifying OTP
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/verifyOtp/{confirmOtp}")
	public ResponseEntity<CustomResponse> verifyOtp(@PathVariable Integer confirmOtp) {
		logger.info("inside verifyOtp method in UserController");

		if (otp == confirmOtp) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "OTP Verified SuccessFully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			logger.info("opt not metched inside verifyOtp method in controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "OTP Not Matched");
			return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
		}
	}

	/**
	 * Resets Password
	 * 
	 * @param user Credentials
	 * @return updated user password
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PutMapping("/resetPasswrod")
	public ResponseEntity<CustomResponse> resetPassword(@Valid @RequestBody Credentials credentials) {
		logger.info("Inside resetPassword method in UserController");

		try {

			if (credentials.getEmail().equals("") || credentials.getEmail() == null
					|| credentials.getPassword().equals("") || credentials.getPassword() == null) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
						"user email and passwrod in not found");
				logger.info("Email and password is not valid");
				return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
			}

			Long roleId = userService.getUserByEmail(credentials.getEmail()).getRoleId();
			credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
			userService.resetPassword(credentials.getEmail(), credentials.getPassword());
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"Password has been updated successfully", roleId);
			logger.info("User Passwrod has been successfully updated");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error inside resetPassword method in UserController");
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Password reset has been failed");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Add the Nominee of User
	 * 
	 * @param NomineeDTO Details
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/addNominee")
	public ResponseEntity<CustomResponse> addNominee(@RequestBody NomineeDTO nomineeDto) {

		Nominee nominee = new Nominee(nomineeDto);

		Nominee result = userService.checkNominee(nominee.getUserId());
		if (result != null) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
					"You can not add more than one Nominee");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			this.userService.addNominee(nominee);
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Nominee Registered Sucessfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Adding dependents for user in an array[]
	 * 
	 * @param dependents details.
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/addDependents")
	public ResponseEntity<CustomResponse> addDependents(@RequestBody Dependent[] dependents) {
		// Iterate each dependent and store each one into database
		for (int i = 0; i < dependents.length; i++) {
			this.userService.addDependents(dependents[i]);
		}
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Dependents Registered Sucessfully");
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

	/**
	 * Adding the details of purchased policy
	 * 
	 * @param Customer Policy details Data
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/addPolicyDetails")
	public ResponseEntity<CustomResponse> addPolicyDetails(@RequestBody SubmitApplicationDTO submitApplicationDto) {
		// SubmitApplication is the DTO which binds up the CustomerPolicy details,
		// Nominee and dependents.
		try {

			// Fetch customerPolicy, nominee and dependents details respectively from
			// SubmitApplicationDto.
			CustomerPolicyDetails customerPolicyDetails = submitApplicationDto.getCustomerPolicyDetails();
			Nominee nominee = submitApplicationDto.getNominee();
			Dependent[] dependent = submitApplicationDto.getDependent();

			// check whether the following fields are present during the request is or not.
			if (nominee != null && !nominee.getEmail().equals("") && !nominee.getFirstName().equals("")
					&& !nominee.getLastName().equals("") && nominee.getUserId() > 0 && dependent.length > 0
					&& customerPolicyDetails.getNumberOfDependent() >= 0
					&& !customerPolicyDetails.getPaymentFrequency().equals("")
					&& customerPolicyDetails.getPremiumAmount() > 0 && customerPolicyDetails.getUserId() > 0
					&& customerPolicyDetails.getPolicyId() > 0 && customerPolicyDetails.getProductId() > 0) {

				// setting the current date in the customerPolicyDetails.
				customerPolicyDetails.setOrderDate(new Date());
				customerPolicyDetails.setModifiedDate(new Date());
				customerPolicyDetails.setStartDate(new Date());
				customerPolicyDetails.setClaimStatus("NotClaimed");

				// get the number of years covered by policy which admin setted while creating
				// the new Policy.
				Product product = userService.getProductByProductId(customerPolicyDetails.getProductId());
				int numberOfYearsCovered = product.getNumberOfYearsCovered();

				// calculating the end date of the policy.
				Date date = new Date();
				Calendar calender = Calendar.getInstance();
				calender.setTime(date);
				calender.add(Calendar.YEAR, numberOfYearsCovered);
				Date endDate = calender.getTime();

				// set it inside the CustomerPolicyDetails
				customerPolicyDetails.setEndDate(endDate);

				// this method check whether the user is applicable for the poilcy or not.
				String applicationResult = userService.checkApplication(customerPolicyDetails);

				// if user Application is approved than set application status Approved.
				if (applicationResult.equals("User is Approved")) {
					customerPolicyDetails.setStatus("Approved");
					customerPolicyDetails = userService.addCustomerPolicyDetails(customerPolicyDetails);
					userService.updatePolicyCount(customerPolicyDetails.getUserId());
					nominee.setOrderId(customerPolicyDetails.getOrderId());
					userService.addNominee(nominee);

					// adding the Multiple dependents into the database.
					for (int i = 0; i < dependent.length; i++) {
						dependent[i].setOrderId(customerPolicyDetails.getOrderId());
						dependent[i].setUserId(nominee.getUserId());
						this.userService.addDependents(dependent[i]);
					}
					CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
							"Customer Policy Approved Successfully", customerPolicyDetails);
					return new ResponseEntity<>(customResponse, HttpStatus.OK);
				} else {
					customerPolicyDetails.setStatus("Pending");
					customerPolicyDetails = userService.addCustomerPolicyDetails(customerPolicyDetails);
					nominee.setOrderId(customerPolicyDetails.getOrderId());
					userService.updatePolicyCount(customerPolicyDetails.getUserId());
					userService.addNominee(nominee);
					for (int i = 0; i < dependent.length; i++) {
						dependent[i].setOrderId(customerPolicyDetails.getOrderId());
						dependent[i].setUserId(nominee.getUserId());
						this.userService.addDependents(dependent[i]);
					}
					CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
							applicationResult + ". So This Policy May be approved By UnderWriter",
							customerPolicyDetails);
					return new ResponseEntity<>(customResponse, HttpStatus.OK);
				}

			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Field is Missing");
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Something Went Wrong");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}
	
	
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/checkApplicationApproval")
	public ResponseEntity<CustomResponse> checkApplicationApproval(@RequestBody CustomerPolicyDetailsDTO customerPolicyDetailsDTO) {
		try {
			CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails(customerPolicyDetailsDTO);
			customerPolicyDetails.setOrderDate(new Date());
			customerPolicyDetails.setModifiedDate(new Date());
			customerPolicyDetails.setStartDate(new Date());
			String applicationResult = userService.checkApplication(customerPolicyDetails);
			if (applicationResult.equals("User is Approved")) {
				customerPolicyDetails.setStatus("Approved");
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"Customer Policy Approved Successfully", customerPolicyDetails);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				customerPolicyDetails.setStatus("Pending");
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						applicationResult + ". So This Policy May be approved By UnderWriter",
						customerPolicyDetails);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			}

		}catch(Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Something Went Wrong");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}
	
	
	
	/**
	 * Updating User Policy details
	 * 
	 * @param Policy details data
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PutMapping("/addPolicyDetails")
	public ResponseEntity<CustomResponse> updatePolicyDetails(
			@RequestBody CustomerPolicyDetailsDTO customerPolicyDetailsDto) {

		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails(customerPolicyDetailsDto);

		if (customerPolicyDetails.getNumberOfDependent() >= 0 && !customerPolicyDetails.getPaymentFrequency().equals("")
				&& customerPolicyDetails.getPremiumAmount() > 0 && customerPolicyDetails.getAmountPaid() > 0
				&& customerPolicyDetails.getUserId() > 0 && customerPolicyDetails.getPolicyId() > 0
				&& customerPolicyDetails.getProductId() > 0) {
			userService.addCustomerPolicyDetails(customerPolicyDetails);
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"Customer Policy details are updated Successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"Customer Policy Details are not updated Sucessfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Fetches all policies created by Admin
	 * 
	 * @return policies created by Admin
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/getPolicyDetails")
	public ResponseEntity<CustomResponse> getPolicyDetails() {
		List<CustomerPolicyDetails> getCustomerPolicyDetails = userService.getCustomerPolicyDetails();
		if (getCustomerPolicyDetails.isEmpty()) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"There is no any policy Details");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);

		} else {

			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"Customer Policy details are fetched Successfully", getCustomerPolicyDetails);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Fetching Policies according to policy type
	 * 
	 * @return Products according to the policy type
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/getProductByPolicyId")
	public ResponseEntity<CustomResponse> getProductByPolicyId(@RequestBody ProductDTO productDto) {

		Product product = new Product(productDto);

		List<Product> getProductById = userService.getProductByPolicyId(product.getPolicyId());
		if (getProductById.isEmpty()) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"There is no any policy");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Policy is fetched Successfully",
					getProductById);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	
	
	/**
	 * Fetching Products by it's productId
	 * 
	 * @return Products with given productId
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/getProductByProductId")
	public ResponseEntity<CustomResponse> getProductByProductId(@RequestBody ProductDTO productDto) {

		Product product = new Product(productDto);

		Product getProductById = userService.getProductByProductId(product.getProductId());
		if (getProductById == null) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"There is no any products");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Policy is fetched Successfully",
					getProductById);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Fetching user details by email
	 * 
	 * @param userEmail
	 * @return Details of User by his emailId
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/getUserByEmail")
	public ResponseEntity<CustomResponse> getUserByEmail(@RequestBody UserDTO userDto) {

		User user = new User(userDto);

		User getUserByEmail = userService.getUserByEmail(user.getEmail());
		if (getUserByEmail == null) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "There is no such User");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "User is fetched Successfully",
					getUserByEmail);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Fetching Admin Details by his associated emailId
	 * 
	 * @param email
	 * @return
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@GetMapping("/getAdminByEmail/{email}")
	public ResponseEntity<CustomResponse> getAdmin(@PathVariable("email") String email) {
		try {
			User admin = this.userService.getUserByEmail(email);
			if (admin != null) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"Admin Data retrived successfully", admin);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
						"Not Available..");
				return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
			}
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "Not Available..");
			return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
		}
	}

	/**
	 * Updating Admin profile
	 * 
	 * @author nikhar.jain
	 * @param user
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PutMapping("/updateAdminProfile")
	public ResponseEntity<CustomResponse> updateAdminProfile(@Valid @RequestBody UserDTO userDto) {

		logger.info("Inside user controller UpdateAdminProfile");
		User user = new User(userDto);

		try {
			User admin = this.userService.updateUser(user);
			if (admin != null) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Profile updated Sucessfully",
						admin);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
						"Not Updated..");
				return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
			}
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "Not Updated..");
			return new ResponseEntity<>(customResponse, HttpStatus.ALREADY_REPORTED);
		}
	}

	/**
	 * Checks if the user already has the policy
	 * 
	 * @param Policy details
	 */
	// check whether the user has already purchased the current policy or not.
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/checkUserWithProduct")
	public ResponseEntity<CustomResponse> checkUserWithProduct(
			@RequestBody CustomerPolicyDetailsDTO customerPolicyDetailsDto) {

		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails(customerPolicyDetailsDto);

		List<CustomerPolicyDetails> userWithProduct = userService
				.checkUserWithProduct(customerPolicyDetails.getProductId(), customerPolicyDetails.getUserId());
		if (!userWithProduct.isEmpty()) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
					"You are ordering the same Policy that you already have.");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"This policy is not in userAccount");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	/**
	 * Fetches all policies purchased by user
	 * 
	 * @param user
	 * @return Policies purchased by user
	 */

	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/fetchUserProducts")
	public ResponseEntity<CustomResponse> fetchUserProducts(@RequestBody UserDTO userDto) {

		User user = new User(userDto);

		UserPolicyDataDTO userProductDetails = userService.fetchUserOderDetails(user.getUserId());
		if (userProductDetails != null) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "User Order Details Are Fetched",
					userProductDetails);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"User Order Details are not fetched");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Adds Claim data for Life Insurance
	 * 
	 * @param Claim Details
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/addClaim")
	public ResponseEntity<CustomResponse> addClaim(@RequestBody ClaimDTO claimDto) {

		Claim claim = new Claim(claimDto);
		claim.setCreatedDate(new Date());
		claim.setModifiedDate(new Date());
		claim.setClaimStatus("Under Process");
		Claim savedClaim = userService.addClaim(claim);
		if (savedClaim != null) {
			userService.updateClaimStatus(claim.getOrderId());
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Claim are saved Successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} else {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Claim are not saved");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Updates the Amount/EMI paid by user
	 * 
	 * @param Customer policy details
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/addPayment")
	public ResponseEntity<CustomResponse> addPayment(@RequestBody CustomerPolicyDetailsDTO customerPolicyDetailsDto) {

		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails(customerPolicyDetailsDto);

		try {
			userService.payAmount(customerPolicyDetails.getAmountPaid(), customerPolicyDetails.getOrderId());
			CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Amount Added  Successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Amounts is not added Successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Fetches Data for Invoice
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/getInvoiceData")
	public ResponseEntity<CustomResponse> getInvoice(@RequestBody CustomerPolicyDetailsDTO customerPolicyDetailsDto) {

		logger.info("Inside getInvoice method of UserController ");

		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails(customerPolicyDetailsDto);
		try {
			InvoiceDataDTO invoiceDataDto = userService.getInvoiceData(customerPolicyDetails.getOrderId());
			CustomResponse customResponse = null;
			if (invoiceDataDto != null) {
				customResponse = new CustomResponse(HttpStatus.OK.value(), "Invoice data fetch Successfully",
						invoiceDataDto);
			}
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Invoice data is not fetched Succefully");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Checks if Nominee exists for a given Id
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/checkNominee")
	public ResponseEntity<CustomResponse> checkNominee(@RequestBody ClaimDTO claimDto) {

		logger.info("Inside Nominee Check ");
		try {
			boolean isNomineeExist = userService.checkNomineeByNomineeId(claimDto.getNomineeId());
			CustomResponse customResponse = null;
			if (isNomineeExist) {
				customResponse = new CustomResponse(HttpStatus.OK.value(), "Nominee is Exist");
			} else {
				customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Nominee is not Exist");
			}
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Somthing went wrong in check Nominee");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Checks if User exists for a given Id
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/checkUserById")
	public ResponseEntity<CustomResponse> checkUserById(@RequestBody ClaimDTO claimDto) {

		logger.info("Inside User Check ");
		try {
			boolean isUserExist = userService.checkUserById(claimDto.getUserId());
			CustomResponse customResponse = null;
			if (isUserExist) {
				customResponse = new CustomResponse(HttpStatus.OK.value(), "User is Exist");
			} else {
				customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "User is not Exist");
			}
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Something went wrong in checkUserById");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Checks if Order Id exists for a given Id
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/checkOrder")
	public ResponseEntity<CustomResponse> checkOrder(@RequestBody ClaimDTO claimDto) {

		logger.info("Inside checkOrder method of UserController");
		try {
			boolean isOrderExist = userService.checkOrderId(claimDto.getOrderId());
			CustomResponse customResponse = null;
			if (isOrderExist) {
				customResponse = new CustomResponse(HttpStatus.OK.value(), "Order is Exist");
			} else {
				customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Order is not Exist");
			}
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Something went wrong in Check Order");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Verifies Details entered for Claim
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/verifyClaimDetails")
	public ResponseEntity<CustomResponse> verifyClaim(@RequestBody ClaimDTO claimDto) {

		logger.info("Inside verifyClaimDetails of UserController");
		try {
			boolean isVerified = userService.verifyClaim(claimDto);
			CustomResponse customResponse = null;
			if (isVerified) {
				customResponse = new CustomResponse(HttpStatus.OK.value(), "Claim is Verified");
			} else {
				customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Claim is not Verified");
			}
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Something went wrong in verifyClaimDetails");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Sends mail with User Queries
	 */
	@PreAuthorize("hasAuthority('[ROLE_USER]')")
	@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
	@PostMapping("/contactUs")
	public ResponseEntity<CustomResponse> contactUs(@RequestBody ContactUsDTO contactUs) {

		logger.info("Inside of contactUs method of UserController");
		try {
				SendEmailUtilis.sendmail("impinsimpins33@gmail.com", "Contact Us", contactUs.getMessage());
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Message Has been Send");
			
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (Exception e) {
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Something went wrong");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

}
