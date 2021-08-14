package com.impetus.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.impetus.dao.ClaimDao;
import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.DependentsDao;
import com.impetus.dao.NomineeDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.UserDao;
import com.impetus.dto.AdminDTO;
import com.impetus.dto.ProductDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.services.AdminServices;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);

	@Autowired
	private AdminServices adminServices;

	@MockBean
	private UserDao userDao;

	@MockBean
	private NomineeDao nomineeDao;

	@MockBean
	private DependentsDao dependentsDao;

	@MockBean
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	@MockBean
	private ClaimDao claimDao;

	@MockBean
	private ProductDao productDao;

	@Test
	void testCustomers() {
		long roleId = 3;
		User mockCustomer1 = new User();
		mockCustomer1.setFirstName("Rashi");
		mockCustomer1.setLastName("Jain");
		mockCustomer1.setEmail("rashi123@gmail.com");
		mockCustomer1.setGender("Female");
		mockCustomer1.setEducation("Post Graduation");
		mockCustomer1.setCreatedDate(new Date());
		mockCustomer1.setModifiedDate(new Date());
		mockCustomer1.setPhoneNo(123456789);
		mockCustomer1.setRoleId(3);
		mockCustomer1.setPolicyCount(0);
		User mockCustomer2 = new User();
		mockCustomer2.setFirstName("Sakshi");
		mockCustomer2.setLastName("Jain");
		mockCustomer2.setEmail("sakshi123@gmail.com");
		mockCustomer2.setGender("Female");
		mockCustomer2.setEducation("Post Graduation");
		mockCustomer2.setCreatedDate(new Date());
		mockCustomer2.setModifiedDate(new Date());
		mockCustomer2.setPhoneNo(123456789);
		mockCustomer2.setRoleId(3);
		mockCustomer2.setPolicyCount(3);
		User mockCustomer3 = new User();
		mockCustomer3.setFirstName("Naman");
		mockCustomer3.setLastName("Sharma");
		mockCustomer3.setEmail("naman123@gmail.com");
		mockCustomer3.setGender("Male");
		mockCustomer3.setEducation("Graduation");
		mockCustomer3.setCreatedDate(new Date());
		mockCustomer3.setModifiedDate(new Date());
		mockCustomer3.setPhoneNo(123456789);
		mockCustomer3.setRoleId(3);
		mockCustomer3.setPolicyCount(2);
		List<User> customers = new ArrayList<User>();
		customers.add(mockCustomer1);
		customers.add(mockCustomer2);
		customers.add(mockCustomer3);
		Mockito.when(userDao.findByRoleId(roleId)).thenReturn(customers);
		assertEquals(2, adminServices.customers().size());
	}

	@Test
	void customersFailTest() {
		logger.info("Inside customersFailTest");
		when(userDao.findByRoleId(3)).thenReturn(null);
		List<User> result = this.adminServices.customers();
		logger.info("Result size is 0 : {}", result.size());
		assertEquals(0, result.size());
	}

	@Test
	void testGetAllUnderWriters() throws Exception {
		User mockUnderwriter1 = new User();
		mockUnderwriter1.setFirstName("Rashi");
		mockUnderwriter1.setLastName("Jain");
		mockUnderwriter1.setEmail("rashi123@gmail.com");
		mockUnderwriter1.setGender("Female");
		mockUnderwriter1.setEducation("Post Graduation");
		mockUnderwriter1.setCreatedDate(new Date());
		mockUnderwriter1.setModifiedDate(new Date());
		mockUnderwriter1.setPhoneNo(123456789);
		mockUnderwriter1.setRoleId(2);
		mockUnderwriter1.setStatus(true);
		User mockUnderwriter2 = new User();
		mockUnderwriter2.setFirstName("Sakshi");
		mockUnderwriter2.setLastName("Jain");
		mockUnderwriter2.setEmail("sakshi123@gmail.com");
		mockUnderwriter2.setGender("Female");
		mockUnderwriter2.setEducation("Post Graduation");
		mockUnderwriter2.setCreatedDate(new Date());
		mockUnderwriter2.setModifiedDate(new Date());
		mockUnderwriter2.setPhoneNo(123456789);
		mockUnderwriter2.setRoleId(2);
		mockUnderwriter2.setStatus(true);

		List<User> underwriters = new ArrayList<User>();
		underwriters.add(mockUnderwriter1);
		underwriters.add(mockUnderwriter2);

		Mockito.when(userDao.getAllUnderwriter(2l, true)).thenReturn(underwriters);

		List<User> result = adminServices.getAllUnderWriters(2);
		logger.info("result : {} ", result.size());
		assertEquals(2, result.size());
	}

	@Test
	void getAllUnderwriterFailTest() {
		logger.info("Inside getAllUnderwriterFailTest");
		List<User> result = this.adminServices.getAllUnderWriters(3);
		logger.info("Result size is 0 : {}", result.size());
		assertEquals(0, result.size());
	}

	@Test
	void testLogin() throws Exception {
		String email = "admin@gmail.com";
		String password = "admin@1234";
		int roleId = 1;
		User admin = new User();
		admin.setEmail(email);
		admin.setPassword(password);
		admin.setRoleId(1);
		Mockito.when(userDao.findAdminByEmail(email, roleId)).thenReturn(admin);
		assertEquals(true, adminServices.login(email, password, roleId));
	}

	@Test
	void testCheckCustomer() throws Exception {
		long userId = 32;
		User mockUser = new User();
		mockUser.setUserId(32);
		mockUser.setFirstName("Sakshi");
		mockUser.setLastName("Jain");
		mockUser.setEmail("sakshi123@gmail.com");
		mockUser.setGender("Female");
		mockUser.setEducation("Post Graduation");
		mockUser.setCreatedDate(new Date());
		mockUser.setModifiedDate(new Date());
		mockUser.setPhoneNo(123456789);
		mockUser.setRoleId(3);
		mockUser.setPolicyCount(3);
		Mockito.when(userDao.getUserById(userId)).thenReturn(mockUser);
		assertEquals(true, adminServices.checkCustomer(userId));
	}

	@Test
	void testGetProduct() {
		long productId = 6;
		Product product1 = new Product();
		product1.setProductId(productId);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		List<Product> product = new ArrayList<Product>();
		Mockito.when(productDao.getProductByProductIdList(productId)).thenReturn(product);
		assertEquals(product, adminServices.getProduct(productId));
	}

	@Test
	void getProductFailTest() {
		logger.info("Inside getProductFailTest");
		when(productDao.getProductByProductIdList(6)).thenReturn(null);
		List<Product> result = this.adminServices.getProduct(6l);
		logger.info("Result size is 0 : {}", result.size());
		assertEquals(0, result.size());

	}

	@Test
	void testGetProducts() {
		long productId1 = 6;
		Product product1 = new Product();
		product1.setProductId(productId1);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);

		long productId2 = 8;
		Product product2 = new Product();
		product2.setProductId(productId2);
		product2.setProductName("Life Safety");
		product2.setCreatedDate(new Date());
		product2.setModifiedDate(new Date());
		product2.setMinAgeLimit(18);
		product2.setMaxAgeLimit(45);
		product2.setMinNumDependents(0);
		product2.setMaxNumDependents(3);
		product2.setPolicyId(3);
		product2.setSumAssured(700000);
		product2.setNumberOfYearsCovered(15);

		List<Product> product = new ArrayList<Product>();
		product.add(product1);
		product.add(product2);

		Mockito.when(productDao.findAll()).thenReturn(product);
		assertEquals(product, adminServices.getProducts());
	}

	@Test
	void testAddProduct() {
		Product product1 = new Product();
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		assertEquals(product1, adminServices.addProduct(product1));
	}

	@Test
	void testUpdateProduct() {
		long productId = 6;
		Product product1 = new Product();
		product1.setProductId(productId);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		assertEquals(product1, adminServices.updateProduct(product1));
	}

	@Test
	void testDeleteProduct() {
		long productId = 6;
		Product product1 = new Product();
		product1.setProductId(productId);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		Mockito.when(productDao.getProductByProductId(productId)).thenReturn(product1);
		assertEquals(product1, adminServices.deleteProduct(productId));
	}

	@Test
	void testGetNominee() throws Exception {
		long orderId = 45;
		long userId = 30;
		Nominee nominee1 = new Nominee();
		nominee1.setFirstName("Divya");
		nominee1.setLastName("Yadav");
		nominee1.setEmail("divya@gmail.com");
		nominee1.setPhone(9999999999L);
		nominee1.setOrderId(orderId);
		nominee1.setUserId(userId);
		List<Nominee> nominee = new ArrayList<Nominee>();
		nominee.add(nominee1);
		Mockito.when(nomineeDao.getNomineesByOrderId(orderId)).thenReturn(nominee);
		assertEquals(nominee, adminServices.getNominee(orderId));

	}

	@Test
	void getNomineeFailTest() {
		logger.info("Inside getNomineeFailTest");
		Mockito.when(nomineeDao.getNomineesByOrderId(45)).thenReturn(null);
		List<Nominee> result = adminServices.getNominee(45);
		logger.info("Result is 0 : {} ", result.size());
		assertEquals(0, result.size());
	}

	@Test
	void testDependents() throws Exception {
		long orderId = 45;
		long userId = 30;
		Dependent dependent = new Dependent();
		dependent.setFirstName("Divya");
		dependent.setLastName("Yadav");
		dependent.setMiddleName("A");
		dependent.setEmail("divya@gmail.com");
		dependent.setPhone(9999999999L);
		dependent.setAge(20);
		dependent.setGender("Female");
		dependent.setOrderId(orderId);
		dependent.setUserId(userId);
		List<Dependent> dependentsList = new ArrayList<Dependent>();
		dependentsList.add(dependent);
		Mockito.when(dependentsDao.getDependentsByOrderId(orderId)).thenReturn(dependentsList);
		assertEquals(dependentsList, adminServices.dependents(orderId));

	}

	@Test
	void dependentsFailTest() {
		logger.info("Inside dependentsFailTest");
		when(dependentsDao.getDependentsByOrderId(45)).thenReturn(null);
		List<Dependent> result = adminServices.dependents(45);
		logger.info("Result is 0 : {} ", result.size());
		assertEquals(0, result.size());

	}

	@Test
	void testUserPolicyDetails() {
		long userId = 30;
		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setClaimStatus("false");
		policyDetails1.setStatus("Active");
		policyDetails1.setProductId(4);
		policyDetails1.setPolicyId(1);
		CustomerPolicyDetails policyDetails2 = new CustomerPolicyDetails();
		policyDetails2.setOrderId(36L);
		policyDetails2.setUserId(30L);
		policyDetails2.setNumberOfDependent(2);
		policyDetails2.setPremiumAmount(700000);
		policyDetails2.setAmountPaid(50000);
		policyDetails2.setPaymentFrequency("Quaterly");
		policyDetails2.setClaimStatus("false");
		policyDetails2.setStatus("Active");
		policyDetails2.setProductId(5);
		policyDetails2.setPolicyId(2);
		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		policyDetails.add(policyDetails1);
		policyDetails.add(policyDetails2);
		Mockito.when(customerPolicyDetailsDao.fetchUserOrders(userId)).thenReturn(policyDetails);
		assertEquals(policyDetails, adminServices.userPolicyDetails(userId));
	}

	@Test
	void testGetClaims() {
		Claim mockClaim1 = new Claim();
		mockClaim1.setClaimStatus("Under Process");
		mockClaim1.setCustomerName("Rashi Jain");
		mockClaim1.setNomineeName("Sakshi Jain");
		mockClaim1.setCreatedDate(new Date());
		mockClaim1.setModifiedDate(new Date());
		mockClaim1.setNomineeId(45);
		mockClaim1.setUserId(57);
		mockClaim1.setOrderId(34);
		Claim mockClaim2 = new Claim();
		mockClaim2.setClaimStatus("Finished");
		mockClaim2.setCustomerName("Paras Sharma");
		mockClaim2.setNomineeName("Divya Sharma");
		mockClaim2.setCreatedDate(new Date());
		mockClaim2.setModifiedDate(new Date());
		mockClaim2.setNomineeId(56);
		mockClaim2.setUserId(76);
		mockClaim2.setOrderId(49);
		List<Claim> claims = new ArrayList<Claim>();
		claims.add(mockClaim1);
		claims.add(mockClaim2);
		Mockito.when(claimDao.findAll()).thenReturn(claims);
		assertEquals(2, adminServices.getClaims().size());
	}

	@Test
	void testUserPolicyDetailByOrderId() {
		long orderId = 45;
		long userId = 30;
		long productId = 4;
		long policyId = 1;
		CustomerPolicyDetails policyDetail1 = new CustomerPolicyDetails();
		policyDetail1.setOrderId(orderId);
		policyDetail1.setUserId(userId);
		policyDetail1.setNumberOfDependent(2);
		policyDetail1.setPremiumAmount(500000);
		policyDetail1.setAmountPaid(50000);
		policyDetail1.setPaymentFrequency("Quaterly");
		policyDetail1.setClaimStatus("false");
		policyDetail1.setStatus("Active");
		policyDetail1.setProductId(productId);
		policyDetail1.setPolicyId(policyId);
		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		policyDetails.add(policyDetail1);
		Mockito.when(customerPolicyDetailsDao.fetchUserOrderByOrderId(orderId)).thenReturn(policyDetails);
		assertEquals(policyDetails, adminServices.userPolicyDetailByOrderId(orderId));
	}

	@Test
	void userPolicyDetailByOrderIdFailTest() {
		logger.info("Inside userPolicyDetailByOrderIdFailTest");
		when(customerPolicyDetailsDao.fetchUserOrderByOrderId(45)).thenReturn(null);
		List<CustomerPolicyDetails> result = this.adminServices.userPolicyDetailByOrderId(45);
		logger.info("Result is 0 : {} ", result.size());
		assertEquals(0, result.size());
	}

	@Test
	void testGetClaimByClaimId() {
		long claimId = 4;
		Claim mockClaim1 = new Claim();
		mockClaim1.setClaimId(claimId);
		mockClaim1.setClaimStatus("Under Process");
		mockClaim1.setCustomerName("Rashi Jain");
		mockClaim1.setNomineeName("Sakshi Jain");
		mockClaim1.setCreatedDate(new Date());
		mockClaim1.setModifiedDate(new Date());
		mockClaim1.setNomineeId(45);
		mockClaim1.setUserId(57);
		mockClaim1.setOrderId(34);
		Mockito.when(claimDao.getClaimRecord(claimId)).thenReturn(mockClaim1);
		assertEquals(mockClaim1, adminServices.getClaimByClaimId(claimId));
	}

	@Test
	void testUpdateClaim() {
		Claim mockClaim1 = new Claim();
		mockClaim1.setClaimStatus("Finished");
		mockClaim1.setCustomerName("Rashi Jain");
		mockClaim1.setNomineeName("Sakshi Jain");
		mockClaim1.setCreatedDate(new Date());
		mockClaim1.setModifiedDate(new Date());
		mockClaim1.setNomineeId(45);
		mockClaim1.setUserId(57);
		mockClaim1.setOrderId(34);
		Mockito.when(claimDao.save(mockClaim1)).thenReturn(mockClaim1);
		assertEquals(mockClaim1, adminServices.updateClaim(mockClaim1));
	}

	@Test
	void checkProductIsDeletableTest() {
		logger.info("Inside checkProductIsDeletableTest");

		Product product1 = new Product();
		product1.setProductId(6l);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);

		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setClaimStatus("false");
		policyDetails1.setStatus("Active");
		policyDetails1.setProductId(6);
		policyDetails1.setPolicyId(1);

		CustomerPolicyDetails policyDetails2 = new CustomerPolicyDetails();
		policyDetails2.setOrderId(36L);
		policyDetails2.setUserId(30L);
		policyDetails2.setNumberOfDependent(2);
		policyDetails2.setPremiumAmount(700000);
		policyDetails2.setAmountPaid(50000);
		policyDetails2.setPaymentFrequency("Quaterly");
		policyDetails2.setClaimStatus("false");
		policyDetails2.setStatus("Active");
		policyDetails2.setProductId(6);
		policyDetails2.setPolicyId(2);

		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		policyDetails.add(policyDetails1);
		policyDetails.add(policyDetails2);

		when(this.productDao.getProductByProductId(product1.getProductId())).thenReturn(product1);
		when(this.productDao.getCustomersWithProductId(product1.getProductId())).thenReturn(policyDetails);

		boolean result = this.adminServices.checkProductIsDeletable(product1.getProductId());
		logger.info("Result : {}", result);
		assertEquals(false, result);
	}

	@Test
	void checkProductIsDeletableProductFalseTest() {
		logger.info("Inside checkProductIsDeletableTest");

		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setClaimStatus("false");
		policyDetails1.setStatus("Active");
		policyDetails1.setProductId(6);
		policyDetails1.setPolicyId(1);

		CustomerPolicyDetails policyDetails2 = new CustomerPolicyDetails();
		policyDetails2.setOrderId(36L);
		policyDetails2.setUserId(30L);
		policyDetails2.setNumberOfDependent(2);
		policyDetails2.setPremiumAmount(700000);
		policyDetails2.setAmountPaid(50000);
		policyDetails2.setPaymentFrequency("Quaterly");
		policyDetails2.setClaimStatus("false");
		policyDetails2.setStatus("Active");
		policyDetails2.setProductId(6);
		policyDetails2.setPolicyId(2);

		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		policyDetails.add(policyDetails1);
		policyDetails.add(policyDetails2);

		when(this.productDao.getProductByProductId(10l)).thenReturn(null);
//		when(this.productDao.getCustomersWithProductId(6l)).thenReturn(policyDetails);

		boolean result = this.adminServices.checkProductIsDeletable(10l);
		logger.info("Result : {}", result);
		assertEquals(false, result);
	}

	@Test
	void checkProductIsDeletableEmptyCustomerTest() {
		logger.info("Inside checkProductIsDeletableTest");

		ProductDTO product1 = new ProductDTO();
		product1.setProductId(6l);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		
		Product product = new Product(product1);
		
		List<CustomerPolicyDetails> listOfCustomers = new ArrayList<>();

		when(this.productDao.getProductByProductId(product1.getProductId())).thenReturn(product);
		when(this.productDao.getCustomersWithProductId(product1.getProductId())).thenReturn(listOfCustomers);

		boolean result = this.adminServices.checkProductIsDeletable(product1.getProductId());
		logger.info("Result : {}", result);
		assertEquals(true, result);
	}


//	@Test
//	void checkUnderwriterSuccess() {
//		logger.info("Inside checkUnderwriterSuccess");
//		User mockUnderwriter1 = new User();
//		mockUnderwriter1.setFirstName("Rashi");
//		mockUnderwriter1.setLastName("Jain");
//		mockUnderwriter1.setEmail("rashi123@gmail.com");
//		mockUnderwriter1.setGender("Female");
//		mockUnderwriter1.setEducation("Post Graduation");
//		mockUnderwriter1.setCreatedDate(new Date());
//		mockUnderwriter1.setModifiedDate(new Date());
//		mockUnderwriter1.setPhoneNo(123456789);
//		mockUnderwriter1.setRoleId(2);
//		mockUnderwriter1.setStatus(true);
//		when(this.userDao.findUnderwriterByEmail(mockUnderwriter1.getEmail(), 2)).thenReturn(mockUnderwriter1);
//
//		boolean result = this.adminServices.checkUnderwriter(mockUnderwriter1.getEmail());
//		logger.info("Result is true : {}", result);
//		assertEquals(true, result);
//	}

//	@Test
//	void checkUnderwriterFailTest() {
//		logger.info("Inside checkUnderwriterFailTest");
//
//		when(this.userDao.findUnderwriterByEmail("rashi123@gmail.com", 2)).thenReturn(null);
//
//		boolean result = this.adminServices.checkUnderwriter("rashi123@gmail.com");
//		logger.info("Result is true : {}", result);
//		assertEquals(false, result);
//	}
//
//	@Test
//	void checkUnderwriterByUserIdSuccessTest() {
//		logger.info("Inside checkUnderwriterByUserIdSuccessTest");
//		User mockUnderwriter1 = new User();
//		mockUnderwriter1.setUserId(10);
//		mockUnderwriter1.setFirstName("Rashi");
//		mockUnderwriter1.setLastName("Jain");
//		mockUnderwriter1.setEmail("rashi123@gmail.com");
//		mockUnderwriter1.setGender("Female");
//		mockUnderwriter1.setEducation("Post Graduation");
//		mockUnderwriter1.setCreatedDate(new Date());
//		mockUnderwriter1.setModifiedDate(new Date());
//		mockUnderwriter1.setPhoneNo(123456789);
//		mockUnderwriter1.setRoleId(2);
//		mockUnderwriter1.setStatus(true);
//		when(this.userDao.findUnderwriterById(mockUnderwriter1.getUserId(), 2)).thenReturn(mockUnderwriter1);
//		boolean result = this.adminServices.checkUnderwriterByUserId(mockUnderwriter1.getUserId());
//		logger.info("Result is true : {}", result);
//		assertEquals(true, result);
//	}

	@Test
	void checkUnderwriterSuccess() {
		logger.info("Inside checkUnderwriterSuccess");
		User mockUnderwriter1 = new User();
		mockUnderwriter1.setFirstName("Rashi");
		mockUnderwriter1.setLastName("Jain");
		mockUnderwriter1.setEmail("rashi123@gmail.com");
		mockUnderwriter1.setGender("Female");
		mockUnderwriter1.setEducation("Post Graduation");
		mockUnderwriter1.setCreatedDate(new Date());
		mockUnderwriter1.setModifiedDate(new Date());
		mockUnderwriter1.setPhoneNo(123456789);
		mockUnderwriter1.setRoleId(2);
		mockUnderwriter1.setStatus(true);
		when(this.userDao.findUnderwriterByEmail(mockUnderwriter1.getEmail(), 2)).thenReturn(mockUnderwriter1);

		boolean result = this.adminServices.checkUnderwriter(mockUnderwriter1.getEmail());
		logger.info("Result is true : {}", result);
		assertEquals(false, result);
	}

	@Test
	void checkUnderwriterFailTest() {
		logger.info("Inside checkUnderwriterFailTest");

		when(this.userDao.findUnderwriterByEmail("rashi123@gmail.com", 2)).thenReturn(null);

		boolean result = this.adminServices.checkUnderwriter("rashi123@gmail.com");
		logger.info("Result is true : {}", result);
		assertEquals(true, result);
	}

	@Test
	void checkUnderwriterByUserIdSuccessTest() {
		logger.info("Inside checkUnderwriterByUserIdSuccessTest");
		User mockUnderwriter1 = new User();
		mockUnderwriter1.setUserId(10);
		mockUnderwriter1.setFirstName("Rashi");
		mockUnderwriter1.setLastName("Jain");
		mockUnderwriter1.setEmail("rashi123@gmail.com");
		mockUnderwriter1.setGender("Female");
		mockUnderwriter1.setEducation("Post Graduation");
		mockUnderwriter1.setCreatedDate(new Date());
		mockUnderwriter1.setModifiedDate(new Date());
		mockUnderwriter1.setPhoneNo(123456789);
		mockUnderwriter1.setRoleId(2);
		mockUnderwriter1.setStatus(true);
		when(this.userDao.findUnderwriterById(mockUnderwriter1.getUserId(), 2)).thenReturn(mockUnderwriter1);
		boolean result = this.adminServices.checkUnderwriterByUserId(mockUnderwriter1.getUserId());
		logger.info("Result is true : {}", result);
		assertEquals(false, result);
	}

	@Test
	void totalSuccessTest() {
		logger.info("Inside totalSuccessTest");
		AdminDTO admin = new AdminDTO();
		admin.setTotalUsers(5);
		admin.setTotalUnderwriters(3);
		admin.setTotalCustomers(10);
		admin.setTotalPendingPolicies(4);
		admin.setTotalPolicies(9);
		admin.setTotalSuccessfulClaims(6);
		admin.setTotalTypeOfPolicies(3);

		AdminDTO result = this.adminServices.total();
		logger.info("Result : {}", result);
		assertNotNull(result);
	}

	@Test
	void userPolicyDetailsSuccessTest() {
		logger.info("Inside userPolicyDetailsSuccessTest ");

		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setClaimStatus("false");
		policyDetails1.setStatus("Active");
		policyDetails1.setProductId(6);
		policyDetails1.setPolicyId(1);

		CustomerPolicyDetails policyDetails2 = new CustomerPolicyDetails();
		policyDetails2.setOrderId(36L);
		policyDetails2.setUserId(30L);
		policyDetails2.setNumberOfDependent(2);
		policyDetails2.setPremiumAmount(700000);
		policyDetails2.setAmountPaid(50000);
		policyDetails2.setPaymentFrequency("Quaterly");
		policyDetails2.setClaimStatus("false");
		policyDetails2.setStatus("Active");
		policyDetails2.setProductId(6);
		policyDetails2.setPolicyId(2);

		List<CustomerPolicyDetails> policyDetails = new ArrayList<>();
		policyDetails.add(policyDetails1);
		policyDetails.add(policyDetails2);

		when(this.customerPolicyDetailsDao.fetchUserOrders(30L)).thenReturn(policyDetails);
		List<CustomerPolicyDetails> result = this.adminServices.userPolicyDetails(30L);
		logger.info("Result size : {}", result.size());
		assertEquals(2, result.size());
	}

	@Test
	void userPolicyDetailsFailTest() {
		logger.info("Inside userPolicyDetailsFailTest ");

		when(this.customerPolicyDetailsDao.fetchUserOrders(30L)).thenReturn(null);
		List<CustomerPolicyDetails> result = this.adminServices.userPolicyDetails(30L);
		logger.info("Result size : {}", result.size());
		assertEquals(0, result.size());
	}
}
