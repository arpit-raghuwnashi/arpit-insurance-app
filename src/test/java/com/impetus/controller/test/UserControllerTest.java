package com.impetus.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.impetus.controller.UserController;
import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.UserDao;
import com.impetus.dto.ClaimDTO;
import com.impetus.dto.CustomerPolicyDetailsDTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
class UserControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

	Date date = new Date();

	@MockBean
	private UserServices userServices;

	@MockBean
	private ProductDao productDaoMock;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	@Autowired
	UserController userController;

	@MockBean
	private SubmitApplicationDTO sbd3;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Test
	void testUserRegisteration() throws Exception {

		UserDTO user = new UserDTO();
		user.setFirstName("abhi");
		user.setLastName("Jain");
		user.setEmail("abc@gmail.com");
		user.setGender("Female");
		user.setEducation("Post Graduation");
		user.setCreatedDate(date);
		user.setModifiedDate(date);
		user.setPhoneNo(1234567899);
		user.setAadharNo("987654321098");
		user.setAnnualIncome(100000);
		user.setDateOfBirth(date);
		user.setHasBp(false);
		user.setIfDiabetic(false);
		user.setIfSmoker(false);
		user.setStatus(false);
		user.setOccupation("ER");
		user.setPassword("1234");
		user.setPolicyCount(0);
		user.setRoleId(3);

		User user2 = new User(user);
		Mockito.when(userServices.checkUser(user.getEmail())).thenReturn(null);
		Mockito.when(userServices.addUser(user2)).thenReturn(user2);
		CustomResponse customResponse = new CustomResponse(200, "User Registered Sucessfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK), userController.register(user));

		UserDTO user1 = new UserDTO();
		user1.setFirstName("abhi");
		user1.setLastName("Jain");
		user1.setEmail("hardik@gmail.com");
		user1.setGender("Female");
		user1.setEducation("Post Graduation");
		user1.setCreatedDate(date);
		user1.setModifiedDate(date);
		user1.setPhoneNo(1234567899);
		user1.setAadharNo("987654321098");
		user1.setAnnualIncome(100000);
		user1.setDateOfBirth(date);
		user1.setHasBp(false);
		user1.setIfDiabetic(false);
		user1.setIfSmoker(false);
		user1.setStatus(false);
		user1.setOccupation("ER");
		user1.setPassword("1234");
		user1.setPolicyCount(0);
		user1.setRoleId(3);

		User user3 = new User(user1);
		Mockito.when(userServices.checkUser(user3.getEmail())).thenReturn(user3);
//		Mockito.when(userServices.addUser(user)).thenReturn(user);
		CustomResponse customResponse1 = new CustomResponse(208, "User Exist Already");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.register(user1));
	}

	@Test
	void testUpdateUser() {
		UserDTO user1 = new UserDTO();
		user1.setFirstName("abhi");
		user1.setLastName("Jain");
		user1.setEmail("hardik@gmail.com");
		user1.setGender("Female");
		user1.setEducation("Post Graduation");
		user1.setCreatedDate(date);
		user1.setModifiedDate(date);
		user1.setPhoneNo(1234567899);
		user1.setAadharNo("987654321098");
		user1.setAnnualIncome(100000);
		user1.setDateOfBirth(date);
		user1.setHasBp(true);
		user1.setIfDiabetic(false);
		user1.setIfSmoker(false);
		user1.setStatus(false);
		user1.setOccupation("ER");
		user1.setPassword("1234");
		user1.setPolicyCount(0);
		user1.setRoleId(3);
		user1.setUserId(197);

		User user2 = new User(user1);

//		Mockito.when(userDao.findUserByEmail(user1.getEmail())).thenReturn(user1);
		Mockito.when(userServices.getUserByEmail(user2.getEmail())).thenReturn(user2);
		Mockito.when(userServices.addUser(user2)).thenReturn(user2);
		CustomResponse customResponse1 = new CustomResponse(200, "User updated Sucessfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.updateUser(user1));

		UserDTO user = new UserDTO();
		user.setFirstName("abhi");
		user.setLastName("Jain");
		user.setEmail("hardik@gmail.com");
		user.setGender("Female");
		user.setEducation("Post Graduation");
		user.setCreatedDate(date);
		user.setModifiedDate(date);
		user.setPhoneNo(1234567899);
		user.setAadharNo("987654321098");
		user.setAnnualIncome(100000);
		user.setDateOfBirth(date);
		user.setHasBp(true);
		user.setIfDiabetic(false);
		user.setIfSmoker(false);
		user.setStatus(false);
		user.setOccupation("ER");
		user.setPassword("1234");
		user.setPolicyCount(0);
		user.setRoleId(3);
		user.setUserId(197);

		User user3 = new User(user);

//		Mockito.when(userDao.findUserByEmail(user1.getEmail())).thenReturn(user1);
		Mockito.when(userServices.getUserByEmail(user3.getEmail())).thenReturn(null);
//		Mockito.when(userServices.addUser(user1)).thenReturn(user1);
		CustomResponse customResponse = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
				"User Not Updated Successfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.ALREADY_REPORTED),
				userController.updateUser(user));

	}

	@Test
	void testLoginUser() {
		UserDTO user1 = new UserDTO();
		user1.setEmail("hardik@gmail.com");
		user1.setPassword("1234");

		User user2 = new User(user1);

		Mockito.when(userServices.login(user2.getEmail(), user2.getPassword())).thenReturn(user2);
		assertEquals(200, userController.loginUser(user1).getStatusCodeValue());

		UserDTO user = new UserDTO();
		user.setEmail("har@gmail.com");
		user.setPassword("1234");

		User user3 = new User(user);

		Mockito.when(userServices.login(user3.getEmail(), user3.getPassword())).thenReturn(null);
		assertEquals(208, userController.loginUser(user).getStatusCodeValue());
	}

	@Test
	void testSendOtp() {
		String email = "kunal.sharma@gmail.com";
//		User user = userDao.findUserByEmail(email);
		User user = new User();
		user.setEmail(email);
		Mockito.when(userServices.checkUser(email)).thenReturn(user);
		CustomResponse customResponse1 = new CustomResponse(200, "Otp has been sent successfully to your mail");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK), userController.sendOtp(email));

	}

	@Test
	void SendOtpFailTest() {
		String email1 = "har@gmail.com";
		User user1 = userDao.findUserByEmail(email1);
		Mockito.when(userServices.checkUser(email1)).thenReturn(user1);
		CustomResponse customResponse2 = new CustomResponse(400, "You are not registered user");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.BAD_REQUEST),
				userController.sendOtp(email1));

	}

	@Test
	void testVerifyOtp() {
		int otp = userServices.sendOtp();
		CustomResponse customResponse1 = new CustomResponse(200, "OTP Verified SuccessFully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK), userController.verifyOtp(otp));

		int otp1 = 1234;
		CustomResponse customResponse2 = new CustomResponse(208, "OTP Not Matched");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.ALREADY_REPORTED),
				userController.verifyOtp(otp1));
	}

//	@Test
//	void testResetPassword() {
//		Credentials credentials = new Credentials();
//		credentials.setEmail("hardik@gmail.com");
//		credentials.setPassword("12345");
////		Long roleId = userServices.getUserByEmail(credentials.getEmail()).getRoleId();
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
//				"Password has been updated successfully", 3);
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
//				userController.resetPassword(credentials));
//	}

	@Test
	void testAddNominee() {
		NomineeDTO nominee = new NomineeDTO();
		nominee.setEmail("kunal@google.com");
		nominee.setFirstName("kunal");
		nominee.setLastName("gupta");
		nominee.setOrderId(123L);
		nominee.setPhone(9876549870L);
		nominee.setUserId(201L);

		Nominee nomineeEntity = new Nominee(nominee);

		Mockito.when(userServices.checkNominee(nomineeEntity.getUserId())).thenReturn(null);
		Mockito.when(userServices.addNominee(nomineeEntity)).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Nominee Registered Sucessfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.addNominee(nominee));

		NomineeDTO nominee1 = new NomineeDTO();
		nominee1.setEmail("kunal@google.com");
		nominee1.setFirstName("kunal");
		nominee1.setLastName("gupta");
		nominee1.setOrderId(123L);
		nominee1.setPhone(9876549870L);
		nominee1.setUserId(201L);

		Nominee nomineeEntity2 = new Nominee(nominee1);

		Mockito.when(userServices.checkNominee(nomineeEntity2.getUserId())).thenReturn(nomineeEntity2);
		Mockito.when(userServices.addNominee(nomineeEntity2)).thenReturn(null);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(),
				"You can not add more than one Nominee");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.addNominee(nominee1));

	}

	@Test
	void testAddDependent() {
		Dependent dependents[] = new Dependent[2];
		Dependent dependent = new Dependent();
		dependent.setEmail("kunal@google.com");
		dependent.setFirstName("kunal");
		dependent.setLastName("gupta");
		dependent.setOrderId(123L);
		dependent.setPhone(9876549870L);
		dependent.setUserId(201L);
		dependents[0] = dependent;
		Mockito.when(userServices.addDependents(dependent)).thenReturn(dependent);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Dependents Registered Sucessfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.addDependents(dependents));
	}

	@Test
	void testAddPolicyDetails() {
		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
		customerPolicyDetails.setAmountPaid(1000);
		customerPolicyDetails.setNumberOfDependent(2);
		customerPolicyDetails.setOrderId(123L);
		customerPolicyDetails.setUserId(201L);
		customerPolicyDetails.setProductId(1L);
		customerPolicyDetails.setPolicyId(1L);
		customerPolicyDetails.setPremiumAmount(2000);
		customerPolicyDetails.setPaymentFrequency("Monthly");

		Nominee nominee = new Nominee();
		nominee.setEmail("kunal@google.com");
		nominee.setFirstName("kunal");
		nominee.setLastName("gupta");
		nominee.setPhone(9876549870L);
		nominee.setUserId(201L);

		Dependent dependents[] = new Dependent[1];
		Dependent dependent = new Dependent();
		dependent.setEmail("kunal@google.com");
		dependent.setFirstName("kunal");
		dependent.setLastName("gupta");
		dependent.setPhone(9876549870L);
		dependent.setUserId(201L);
		dependents[0] = dependent;

		Product product = new Product();
		product.setProductId(1L);
		product.setPolicyId(1L);

		SubmitApplicationDTO sbd = new SubmitApplicationDTO(customerPolicyDetails, nominee, dependents);
//		Product product = productDao.getProductByProductId(customerPolicyDetails.getProductId());
		Mockito.when(productDaoMock.getProductByProductId(customerPolicyDetails.getProductId())).thenReturn(product);

		Mockito.when(userServices.getProductByProductId(customerPolicyDetails.getProductId())).thenReturn(product);
		Mockito.when(userServices.checkApplication(customerPolicyDetails)).thenReturn("User is Approved");
		Mockito.when(userServices.addCustomerPolicyDetails(customerPolicyDetails)).thenReturn(customerPolicyDetails);
		Mockito.when(userServices.addNominee(nominee)).thenReturn(nominee);
		Mockito.when(userServices.addDependents(dependent)).thenReturn(dependent);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"Customer Policy Approved Successfully", customerPolicyDetails);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.addPolicyDetails(sbd));

		CustomerPolicyDetails customerPolicyDetails1 = new CustomerPolicyDetails();
		customerPolicyDetails1.setAmountPaid(1000);
		customerPolicyDetails1.setNumberOfDependent(2);
		customerPolicyDetails1.setOrderId(123L);
		customerPolicyDetails1.setUserId(201L);
		customerPolicyDetails1.setProductId(1);
		customerPolicyDetails1.setPolicyId(1);
		customerPolicyDetails1.setPremiumAmount(2000);
		customerPolicyDetails1.setPaymentFrequency("Monthly");

		Nominee nominee1 = new Nominee();
		nominee1.setEmail("kunal@google.com");
		nominee1.setFirstName("kunal");
		nominee1.setLastName("gupta");
		nominee1.setPhone(9876549870L);
		nominee1.setUserId(201L);

		Dependent dependents1[] = new Dependent[1];
		Dependent dependent1 = new Dependent();
		dependent1.setEmail("kunal@google.com");
		dependent1.setFirstName("kunal");
		dependent1.setLastName("gupta");
		dependent1.setPhone(9876549870L);
		dependent1.setUserId(201L);
		dependents1[0] = dependent;

		SubmitApplicationDTO sbd1 = new SubmitApplicationDTO(customerPolicyDetails1, nominee1, dependents1);
		Product product1 = productDao.getProductByProductId(customerPolicyDetails1.getProductId());
		Mockito.when(userServices.getProductByProductId(customerPolicyDetails1.getProductId())).thenReturn(product1);
		String applicationResult = "User dependents are not matched";
		Mockito.when(userServices.checkApplication(customerPolicyDetails1)).thenReturn(applicationResult);
		customerPolicyDetails1.setStatus("Pending");
		Mockito.when(userServices.addCustomerPolicyDetails(customerPolicyDetails1)).thenReturn(customerPolicyDetails1);
		Mockito.when(userServices.addNominee(nominee1)).thenReturn(nominee1);
		Mockito.when(userServices.addDependents(dependent1)).thenReturn(dependent1);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.OK.value(),
				applicationResult + ". So This Policy May be approved By UnderWriter", customerPolicyDetails1);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.addPolicyDetails(sbd1));

		CustomerPolicyDetails customerPolicyDetails2 = new CustomerPolicyDetails();
		customerPolicyDetails2.setAmountPaid(1000);
		customerPolicyDetails2.setOrderId(123L);
//		customerPolicyDetails2.setUserId(-1L);
		customerPolicyDetails2.setProductId(1);
		customerPolicyDetails2.setPolicyId(1);
		customerPolicyDetails2.setPremiumAmount(2000);
		customerPolicyDetails2.setPaymentFrequency("Monthly");

		Nominee nominee2 = null;

		Dependent dependents2[] = new Dependent[1];
		Dependent dependent2 = new Dependent();
		dependent2.setEmail("kunal@google.com");
		dependent2.setFirstName("kunal");
		dependent2.setLastName("gupta");
		dependent2.setPhone(9876549870L);
		dependent2.setUserId(207L);
		dependents2[0] = dependent2;

		SubmitApplicationDTO sbd2 = new SubmitApplicationDTO(customerPolicyDetails2, nominee2, dependents2);
		Product product2 = productDao.getProductByProductId(customerPolicyDetails2.getProductId());
		Mockito.when(userServices.getProductByProductId(customerPolicyDetails2.getProductId())).thenReturn(product2);
		Mockito.when(userServices.addCustomerPolicyDetails(customerPolicyDetails2)).thenReturn(customerPolicyDetails2);
		Mockito.when(userServices.addNominee(nominee2)).thenReturn(nominee2);
		Mockito.when(userServices.addDependents(dependent2)).thenReturn(dependent2);
		CustomResponse customResponse2 = new CustomResponse(HttpStatus.OK.value(), "Field is Missing");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.OK),
				userController.addPolicyDetails(sbd2));

		CustomerPolicyDetails customerPolicyDetails3 = new CustomerPolicyDetails();
		customerPolicyDetails3.setAmountPaid(1000);
		customerPolicyDetails3.setOrderId(123L);
//		customerPolicyDetails3.setUserId(201L);
		customerPolicyDetails3.setProductId(1);
		customerPolicyDetails3.setPolicyId(1);
		customerPolicyDetails3.setPremiumAmount(2000);
		customerPolicyDetails3.setPaymentFrequency("Monthly");

		Nominee nominee3 = new Nominee();
		nominee3.setEmail("kunal@google.com");
//		nominee3.setFirstName("kunal");
		nominee3.setLastName("gupta");
		nominee3.setPhone(9876549870L);
		nominee3.setUserId(201L);

		Dependent dependents3[] = new Dependent[1];
		Dependent dependent3 = new Dependent();
		dependent3.setEmail("kunal@google.com");
		dependent3.setFirstName("kunal");
		dependent3.setLastName("gupta");
		dependent3.setPhone(9876549870L);
		dependent3.setUserId(201L);
		dependents3[0] = dependent3;

//		SubmitApplicationDto sbd3 = new SubmitApplicationDto(customerPolicyDetails3, nominee3, dependents3);
//		Product product3 = productDao.getProductByProductId(customerPolicyDetails2.getProductId());
//		Mockito.when(userServices.getProductByProductId(customerPolicyDetails2.getProductId())).thenReturn(product2);
//		Product exceptionProduct = null;
		Mockito.when(sbd3.getCustomerPolicyDetails()).thenThrow(new NullPointerException());
		CustomResponse customResponse3 = new CustomResponse(HttpStatus.OK.value(), "Something Went Wrong");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse3, HttpStatus.OK),
				userController.addPolicyDetails(sbd3));

	}

	@Test
	void testGetProductByPolicyId() {
		ProductDTO product = new ProductDTO();
		product.setMaxAgeLimit(45);
		product.setMinAgeLimit(18);
		product.setMaxNumDependents(5);
		product.setMinNumDependents(1);
		product.setNumberOfYearsCovered(4);
		product.setPolicyId(1);

		Product productEntity = new Product(product);

		List<Product> productsList = new ArrayList<>();
		productsList.add(productEntity);

		Mockito.when(productDaoMock.getProductByPolicyId(productEntity.getPolicyId())).thenReturn(productsList);
		Mockito.when(userServices.getProductByPolicyId(productEntity.getPolicyId())).thenReturn(productsList);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Policy is fetched Successfully",
				productsList);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.getProductByPolicyId(product));

		ProductDTO product1 = new ProductDTO();
		product1.setMaxAgeLimit(45);
		product1.setMinAgeLimit(18);
		product1.setMaxNumDependents(5);
		product1.setMinNumDependents(1);
		product1.setNumberOfYearsCovered(4);
//		product1.setPolicyId(5);

		Product productEntity2 = new Product(product1);

		List<Product> list = new ArrayList<>();

		Mockito.when(userServices.getProductByPolicyId(productEntity2.getPolicyId())).thenReturn(list);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "There is no any policy");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.getProductByPolicyId(product1));

	}

	@Test
	void TestGetProductByProductId() {
		ProductDTO product = new ProductDTO();
		product.setMaxAgeLimit(45);
		product.setMinAgeLimit(18);
		product.setMaxNumDependents(5);
		product.setMinNumDependents(1);
		product.setNumberOfYearsCovered(4);
		product.setProductId(1L);

		Product productEntity = new Product(product);

		Mockito.when(userServices.getProductByProductId(productEntity.getProductId())).thenReturn(productEntity);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Policy is fetched Successfully",
				productEntity);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.getProductByProductId(product));

		ProductDTO product1 = new ProductDTO();
		product1.setMaxAgeLimit(45);
		product1.setMinAgeLimit(18);
		product1.setMaxNumDependents(5);
		product1.setMinNumDependents(1);
		product1.setNumberOfYearsCovered(4);
		product.setProductId(201L);

		Product productEntity2 = new Product(product1);

		Mockito.when(userServices.getProductByProductId(productEntity2.getProductId())).thenReturn(null);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "There is no any products");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.getProductByProductId(product1));

	}

	@Test
	void TestFetchUserByEmail() {
		UserDTO user = new UserDTO();
		user.setEmail("hardik@gmail.com");

		User userEntity = new User(user);

		Mockito.when(userServices.getUserByEmail(userEntity.getEmail())).thenReturn(userEntity);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "User is fetched Successfully",
				userEntity);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.getUserByEmail(user));

		UserDTO user1 = new UserDTO();
		user1.setEmail("shayam@gmail.com");

		User userEntity2 = new User(user1);

		Mockito.when(userServices.getUserByEmail(userEntity2.getEmail())).thenReturn(null);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "There is no such User");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				userController.getUserByEmail(user1));
	}

	@Test
	void TestGetAdmin() {
		User user = new User();
		user.setEmail("hardik@gmail.com");
		Mockito.when(userServices.getUserByEmail(user.getEmail())).thenReturn(user);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Admin Data retrived successfully",
				user);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.getAdmin(user.getEmail()));

	}

	@Test
	void TestGetAdminNegative() {
		User user1 = new User();
//		user1.setEmail(null);
		Mockito.when(userServices.getUserByEmail(user1.getEmail())).thenReturn(null);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "Not Available..");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.ALREADY_REPORTED),
				userController.getAdmin(user1.getEmail()));

		Mockito.when(userServices.getUserByEmail(user1.getEmail())).thenThrow(new NullPointerException());
		CustomResponse customResponse2 = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "Not Available..");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.ALREADY_REPORTED),
				userController.getAdmin(user1.getEmail()));

	}

//	@Test
//	void TestUpdateAdminProfile() {
//		UserDTO user = new UserDTO();
//		user.setEmail("hardik@gmail.com");
//		user.setUserId(201L);
//
//		User user2 = new User();
//		Mockito.when(userServices.updateUser(user2)).thenReturn(user2);
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Profile updated Sucessfully", user);
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
//				userController.updateAdminProfile(user));
//	}

	@Test
	void TestUpdateAdminProfileNegative() {
		UserDTO user1 = new UserDTO();

		User user2 = new User();

		Mockito.when(userServices.updateUser(user2)).thenReturn(null);
		CustomResponse customResponse1 = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "Not Updated..");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.ALREADY_REPORTED),
				userController.updateAdminProfile(user1));

		Mockito.when(userServices.updateUser(user2)).thenThrow(new NullPointerException());
		CustomResponse customResponse2 = new CustomResponse(HttpStatus.ALREADY_REPORTED.value(), "Not Updated..");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.ALREADY_REPORTED),
				userController.updateAdminProfile(user1));
	}

//	@Test
//	void TestCheckUserWithProduct() {
//		CustomerPolicyDetailsDTO customerPolicyDetails = new CustomerPolicyDetailsDTO();
//		customerPolicyDetails.setUserId(10L);
//		customerPolicyDetails.setProductId(1L);
//
//		CustomerPolicyDetails customerPolicyDetails1 = new CustomerPolicyDetails(customerPolicyDetails);
//
//		List<CustomerPolicyDetails> customerPolicyDetailsList = customerPolicyDetailsDao
//				.checkUserWithOrder(customerPolicyDetails1.getProductId(), customerPolicyDetails1.getUserId());
//		Mockito.when(userServices.checkUserWithProduct(customerPolicyDetails1.getProductId(),
//				customerPolicyDetails1.getUserId())).thenReturn(customerPolicyDetailsList);
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
//				"You are ordering the same Policy that you already have.");
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
//				userController.checkUserWithProduct(customerPolicyDetails));
//
//	}

	@Test
	void TestCheckUserWithProductNegative() {
		CustomerPolicyDetailsDTO customerPolicyDetails = new CustomerPolicyDetailsDTO();
		customerPolicyDetails.setUserId(201L);
		customerPolicyDetails.setProductId(1L);

		CustomerPolicyDetails customerPolicyDetails2 = new CustomerPolicyDetails(customerPolicyDetails);

		List<CustomerPolicyDetails> customerPolicyDetailsList = customerPolicyDetailsDao
				.checkUserWithOrder(customerPolicyDetails2.getProductId(), customerPolicyDetails2.getUserId());
		Mockito.when(userServices.checkUserWithProduct(customerPolicyDetails2.getProductId(),
				customerPolicyDetails2.getUserId())).thenReturn(customerPolicyDetailsList);
		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"This policy is not in userAccount");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.checkUserWithProduct(customerPolicyDetails));

	}

//	@Test
//	void TestAddClaim() {
//		ClaimDTO claim = new ClaimDTO();
//		claim.setUserId(201L);
//		claim.setClaimStatus("NotClaimed");
//		claim.setNomineeId(123L);
//		claim.setNomineeName("Harshal");
//		claim.setOrderId(123L);
//		claim.setCustomerName("Rpsingh");
//
//		Claim claim2 = new Claim(claim);
//
//		Mockito.when(userServices.addClaim(claim2)).thenReturn(claim2);
//		Mockito.doNothing().when(userServices).updateClaimStatus(claim.getOrderId());
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Claim are saved Successfully");
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK), userController.addClaim(claim));
//	}

	@Test
	void TestAddClaimNegative() {
		ClaimDTO claim = new ClaimDTO();

		Claim claim2 = new Claim(claim);

		Mockito.when(userServices.addClaim(claim2)).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(), "Claim are not saved");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.BAD_REQUEST),
				userController.addClaim(claim));
	}

	@Test
	void TestUpdatePolicyDetails() {
		CustomerPolicyDetailsDTO customerPolicyDetails = new CustomerPolicyDetailsDTO();
		customerPolicyDetails.setAmountPaid(1000);
		customerPolicyDetails.setNumberOfDependent(2);
		customerPolicyDetails.setOrderId(123L);
		customerPolicyDetails.setUserId(201L);
		customerPolicyDetails.setProductId(1);
		customerPolicyDetails.setPolicyId(1);
		customerPolicyDetails.setPremiumAmount(2000);
		customerPolicyDetails.setPaymentFrequency("Monthly");

		CustomerPolicyDetails customerPolicyDetails2 = new CustomerPolicyDetails(customerPolicyDetails);

		Mockito.when(userServices.addCustomerPolicyDetails(customerPolicyDetails2)).thenReturn(customerPolicyDetails2);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"Customer Policy details are updated Successfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.updatePolicyDetails(customerPolicyDetails));
	}

	@Test
	void TestUpdatePolicyDetailsNegative() {
		CustomerPolicyDetailsDTO customerPolicyDetails = new CustomerPolicyDetailsDTO();
//		customerPolicyDetails.setAmountPaid(1000);
//		customerPolicyDetails.setNumberOfDependent(2);
		customerPolicyDetails.setOrderId(123L);
		customerPolicyDetails.setUserId(201L);
		customerPolicyDetails.setProductId(1);
		customerPolicyDetails.setPolicyId(1);
		customerPolicyDetails.setPremiumAmount(2000);
		customerPolicyDetails.setPaymentFrequency("Monthly");

		CustomerPolicyDetails customerPolicyDetails2 = new CustomerPolicyDetails(customerPolicyDetails);

		Mockito.when(userServices.addCustomerPolicyDetails(customerPolicyDetails2)).thenReturn(customerPolicyDetails2);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"Customer Policy Details are not updated Sucessfully");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.updatePolicyDetails(customerPolicyDetails));
	}

//	@Test
//	void TestgetPolicyDetails() {
//		List<com.impetus.entity.CustomerPolicyDetails> getCustomerPolicyDetails = customerPolicyDetailsDao.findAll();
//		Mockito.when(userServices.getCustomerPolicyDetails()).thenReturn(getCustomerPolicyDetails);
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
//				"Customer Policy details are fetched Successfully", getCustomerPolicyDetails);
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
//				userController.getPolicyDetails());
//	}

	@Test
	void TestgetPolicyDetailsNegative() {
		List<com.impetus.entity.CustomerPolicyDetails> getCustomerPolicyDetails = Collections.emptyList();
//		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
		Mockito.when(userServices.getCustomerPolicyDetails()).thenReturn(getCustomerPolicyDetails);
		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"There is no any policy Details");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.BAD_REQUEST),
				userController.getPolicyDetails());
	}

	@Test
	void TestFetchUserProducts() {
		UserDTO user = new UserDTO();
		user.setUserId(201L);

		User user2 = new User(user);

//		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
		UserPolicyDataDTO userProductDetails = new UserPolicyDataDTO();
		Mockito.when(userServices.fetchUserOderDetails(user2.getUserId())).thenReturn(userProductDetails);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "User Order Details Are Fetched",
				userProductDetails);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
				userController.fetchUserProducts(user));
	}

	@Test
	void TestFetchUserProductsNegative() {
		UserDTO user = new UserDTO();
		user.setUserId(201L);

		User user2 = new User();
//		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
//		UserPolicyDataDto userProductDetails = new UserPolicyDataDto();
		Mockito.when(userServices.fetchUserOderDetails(user2.getUserId())).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"User Order Details are not fetched");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.BAD_REQUEST),
				userController.fetchUserProducts(user));
	}

//	@Test
//	void TestAddPayment() {
//		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
//		customerPolicyDetails.setAmountPaid(10000);
//		customerPolicyDetails.setOrderId(172L);
//		Mockito.when(userServices.payAmount(customerPolicyDetails.getAmountPaid(), customerPolicyDetails.getOrderId()))
//				.thenReturn(customerPolicyDetails);
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Amount Added  Successfully");
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK),
//				userController.addPayment(customerPolicyDetails));
//	}

//	@Test
//	void TestAddPaymentNegative() {
//		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
//		customerPolicyDetails.setAmountPaid(100);
//		customerPolicyDetails.setOrderId(172L);
//		Mockito.when(userServices.payAmount(customerPolicyDetails.getAmountPaid(), customerPolicyDetails.getOrderId()))
//				.thenThrow(new NullPointerException());
//		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
//				"Amounts is not added Successfully");
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.BAD_REQUEST),
//				userController.addPayment(customerPolicyDetails));
//	}

	@Test
	void resetPasswordTest() {
		LOGGER.info("Inside resetPasswordTest");
		Credentials credentials = new Credentials("arpit@gmail.com", "12345678");

		User user = new User();
		user.setFirstName("abhi");
		user.setLastName("Jain");
		user.setEmail("arpit@gmail.com");
		user.setGender("Female");
		user.setEducation("Post Graduation");
		user.setCreatedDate(date);
		user.setModifiedDate(date);
		user.setPhoneNo(1234567899);
		user.setAadharNo("987654321098");
		user.setAnnualIncome(100000);
		user.setDateOfBirth(date);
		user.setHasBp(false);
		user.setIfDiabetic(false);
		user.setIfSmoker(false);
		user.setStatus(false);
		user.setOccupation("ER");
		user.setPassword("1234");
		user.setPolicyCount(0);
		user.setRoleId(3);

		when(this.userServices.getUserByEmail(credentials.getEmail())).thenReturn(user);
		doNothing().when(userServices).resetPassword(credentials.getEmail(), credentials.getPassword());

		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"Password has been updated successfully", user.getRoleId());

		ResponseEntity<CustomResponse> result = this.userController.resetPassword(credentials);
		LOGGER.info("Result is not null : {}", result);
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);
	}

	@Test
	void resetPasswordFailTest() {
		LOGGER.info("Inside resetPasswordTest");
		Credentials credentials = new Credentials("arpit@gmail.com", "12345678");

		when(this.userServices.getUserByEmail(credentials.getEmail())).thenReturn(null);
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
//		doNothing().when(userServices).resetPassword(credentials.getEmail(), credentials.getPassword());

		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"Password reset has been failed");

		ResponseEntity<CustomResponse> result = this.userController.resetPassword(credentials);
		LOGGER.info("Result is not null : {}", result);
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST), result);
	}

	@Test
	void resetPasswordFailNUllCredentialsTest() {
		LOGGER.info("Inside resetPasswordFailNUllCredentialsTest");
		Credentials credentials = new Credentials();
		credentials.setEmail("");
		credentials.setPassword("");

//		when(this.userServices.getUserByEmail(credentials.getEmail())).thenReturn(null);
		doNothing().when(userServices).resetPassword(credentials.getEmail(), credentials.getPassword());

		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"user email and passwrod in not found");

		ResponseEntity<CustomResponse> result = this.userController.resetPassword(credentials);
		LOGGER.info("Result is not null : {}", result);
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST), result);
	}

	@Test
	void resetPasswordFailEmptyCredentialsTest() {
		LOGGER.info("Inside resetPasswordFailEmptyCredentialsTest");
		Credentials credentials = new Credentials();
		credentials.setEmail("adc@impetus.com");
		credentials.setPassword("");

//		when(this.userServices.getUserByEmail(credentials.getEmail())).thenReturn(null);
		doNothing().when(userServices).resetPassword(credentials.getEmail(), credentials.getPassword());

		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"user email and passwrod in not found");

		ResponseEntity<CustomResponse> result = this.userController.resetPassword(credentials);
		LOGGER.info("Result is not null : {}", result);
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST), result);
	}

}
