package com.impetus.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.impetus.dao.ClaimDao;
import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.DependentsDao;
import com.impetus.dao.NomineeDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.UserDao;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.services.UserServices;
import com.impetus.services.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
class UserServiceTest {

	@Autowired
	private UserServices userServices;

	@MockBean
	private UserDao userDao;

	@MockBean
	private NomineeDao nomineeDao;

	@MockBean
	private DependentsDao dependentDao;

	@MockBean
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	@MockBean
	private ProductDao productDao;

	@MockBean
	private ClaimDao claimDao;

	@Test
	void testAddUser() {
		User user = new User();
		user.setFirstName("Kunal");
		user.setLastName("sharma");
		user.setEmail("sharmakunal1198@gmail.com");
		user.setAadharNo("868768428");
		user.setAnnualIncome(100000);
		user.setCreatedDate(new Date());
		user.setModifiedDate(new Date());
		user.setDateOfBirth(new Date());
		user.setEducation("Graduate");
		user.setGender("Male");
		user.setHasBp(false);
		user.setIfDiabetic(false);
		user.setIfSmoker(false);
		user.setOccupation("er");
		user.setPassword("1234");
		user.setPhoneNo(898261395);
		user.setPolicyCount(0);
		user.setRoleId(1);
		user.setUserId(0);
		Mockito.when(userDao.save(user)).thenReturn(user);
		assertEquals(user, userServices.addUser(user));

	}

	@Test
	void testAddUserNegative() {
		User user = new User();
		Mockito.when(userDao.save(user)).thenThrow(new NullPointerException());
		assertEquals(user, userServices.addUser(user));

	}

	@Test
	void testcheckUser() {
		User user = new User();
		String email = "kunal@gmail.com";
		Mockito.when(userDao.findUserByEmail(email)).thenReturn(user);
		assertEquals(user, userServices.checkUser(email));
	}

	@Test
	void testcheckUserNegative() {
		User user = new User();
		Mockito.when(userDao.findUserByEmail(user.getEmail())).thenThrow(new NullPointerException());
		assertEquals(null, userServices.checkUser(user.getEmail()));
	}

	@Test
	void testlogin() {
		User user = new User();
		String email = "kunal@gmail.com";
		String password = "1234";
		Mockito.when(userDao.login(email, password)).thenReturn(user);
		assertEquals(user, userServices.login(email, password));
	}

	@Test
	void testLoginNegative() {
		User user = new User();
		Mockito.when(userDao.login(user.getEmail(), user.getPassword())).thenThrow(new NullPointerException());
		assertEquals(null, userServices.login(user.getEmail(), user.getPassword()));
	}

	@Test
	void testcheckNominee() {
		Nominee nominee = new Nominee();
		long userId = 123;
		Mockito.when(nomineeDao.findNomineeByUserId(userId)).thenReturn(nominee);
		assertEquals(nominee, userServices.checkNominee(userId));
	}

	@Test
	void testcheckNomineeNominee() {
//		Nominee nominee = new Nominee();
		long userId = 123;
		Mockito.when(nomineeDao.findNomineeByUserId(userId)).thenThrow(new NullPointerException());
		assertEquals(null, userServices.checkNominee(userId));
	}

	@Test
	void testAddNominee() {
		Nominee nominee = new Nominee();
		nominee.setEmail("kunal@gmail.com");
		nominee.setFirstName("rakesh");
		nominee.setLastName("kumar");
		nominee.setPhone(8982613957L);
		nominee.setOrderId(1234L);
		nominee.setUserId(123L);
		nominee.setNomineeID(123L);
		Mockito.when(nomineeDao.save(nominee)).thenReturn(nominee);
		assertEquals(nominee, userServices.addNominee(nominee));
	}

	@Test
	void testAddNomineeNegative() {
		Nominee nominee = new Nominee();
		Mockito.when(nomineeDao.save(nominee)).thenThrow(new NullPointerException());
		assertEquals(nominee, userServices.addNominee(nominee));
	}

	@Test
	void testAddDependent() {
		Dependent dependent = new Dependent();
		dependent.setFirstName("Kunal");
		dependent.setLastName("Sharma");
		dependent.setEmail("sharmakunal1198@gmail.com");
		dependent.setAge(34);
		dependent.setGender("Male");
		dependent.setMiddleName("Kumar");
		dependent.setDepenedentId(124);
		dependent.setPhone(8984567332L);
		dependent.setOrderId(14L);
		Mockito.when(dependentDao.save(dependent)).thenReturn(dependent);
		assertEquals(dependent, userServices.addDependents(dependent));

	}

	@Test
	void testAddDependentNegative() {
		Dependent dependent = new Dependent();
		Mockito.when(dependentDao.save(dependent)).thenThrow(new NullPointerException());
		assertEquals(dependent, userServices.addDependents(dependent));
	}

	@Test
	void testGetUserByEmail() {
		User user = new User();
		String email = "kunal@gmail.com";
		Mockito.when(userDao.findUserByEmail(email)).thenReturn(user);
		assertEquals(user, userServices.getUserByEmail(email));
	}

	@Test
	void testGetUserByEmailNegative() {
//		User user = new User();
		String email = "kunal@gmail.com";
		Mockito.when(userDao.findUserByEmail(email)).thenThrow(new NullPointerException());
		assertEquals(null, userServices.getUserByEmail(email));
	}

	@Test
	void testaddCustomerPolicyDetails() {
		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
		customerPolicyDetails.setAmountPaid(10000);
		customerPolicyDetails.setClaimStatus("Claimed");
		customerPolicyDetails.setDiscount(20);
		customerPolicyDetails.setEndDate(new Date());
		customerPolicyDetails.setStartDate(new Date());
		customerPolicyDetails.setModifiedDate(new Date());
		customerPolicyDetails.setNumberOfDependent(4);
		customerPolicyDetails.setPaymentFrequency("Monthly");
		customerPolicyDetails.setOrderDate(new Date());
		customerPolicyDetails.setOrderId(123L);
		customerPolicyDetails.setPolicyId(0);
		Mockito.when(customerPolicyDetailsDao.save(customerPolicyDetails)).thenReturn(customerPolicyDetails);
		assertEquals(customerPolicyDetails, userServices.addCustomerPolicyDetails(customerPolicyDetails));
	}

	@Test
	void testaddCustomerPolicyDetailsNegative() {
		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
		Mockito.when(customerPolicyDetailsDao.save(customerPolicyDetails)).thenThrow(new NullPointerException());
		assertEquals(customerPolicyDetails, userServices.addCustomerPolicyDetails(customerPolicyDetails));
	}

	@Test
	void testGetCustomerPolicyDetails() {
		List<CustomerPolicyDetails> listCustomerPolicyDetails = null;
		Mockito.when(customerPolicyDetailsDao.findAll()).thenReturn(listCustomerPolicyDetails);
		assertEquals(listCustomerPolicyDetails, userServices.getCustomerPolicyDetails());
	}

	@Test
	void testGetCustomerPolicyDetailsNegative() {
//		List<CustomerPolicyDetails> listCustomerPolicyDetails=null;
		Mockito.when(customerPolicyDetailsDao.findAll()).thenThrow(new NullPointerException());
		assertEquals(null, userServices.getCustomerPolicyDetails());
	}

	@Test
	void testgetProductByPolicyId() {
		long policyId = 2;
		List<Product> products = null;
		Mockito.when(productDao.getProductByPolicyId(policyId)).thenReturn(products);
		assertEquals(products, userServices.getProductByPolicyId(policyId));
	}

	@Test
	void testgetProductByPolicyIdNegative() {
		long policyId = 2;
//		List<Product> products = null;
		Mockito.when(productDao.getProductByPolicyId(policyId)).thenThrow(new NullPointerException());
		assertEquals(null, userServices.getProductByPolicyId(policyId));
	}

	@Test
	void testHealthRule() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		User user = new User();
		user.setHasBp(false);
		user.setIfDiabetic(false);
		user.setIfSmoker(false);
		assertEquals(true, userServiceImpl.healthRule(user));

//		User user1 = new User();
//		user1.setHasBp(true);
//		user1.setIfDiabetic(true);
//		user1.setIfSmoker(false);
//		Mockito.when(user.isHasBp()).thenReturn(true);
//		Mockito.when(user.isIfDiabetic()).thenReturn(true);
//		Mockito.when(user.isIfSmoker()).thenReturn(true);
//		assertEquals(false, userServiceImpl.healthRule(user1));

	}

	@Test
	void testCaluculateAgeFromDob() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		int year = 1998;
		assertEquals(23, userServiceImpl.caluculateAgeFromDob(year));
	}

	@Test
	void testCheckAge() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		Product product = new Product();
		product.setMaxAgeLimit(45);
		product.setMinAgeLimit(18);
		int currentAge = 34;
		assertEquals(true, userServiceImpl.checkAge(currentAge, product));
	}

	@Test
	void testCheckDependents() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		Product product = new Product();
		CustomerPolicyDetails customerPolicyDetails = new CustomerPolicyDetails();
		customerPolicyDetails.setNumberOfDependent(2);
		product.setMaxNumDependents(5);
		product.setMinNumDependents(1);
		assertEquals(true, userServiceImpl.checkDependent(product, customerPolicyDetails));
	}

	@Test
	void testUpdateUser() {
		User user = new User();
		user.setUserId(24);
		user.setRoleId(2);
		user.setEmail("aman@impetus.com");
		user.setPassword("1234");
		user.setStatus(true);
		user.setEducation("Engineer");
		user.setFirstName("Aman");
		user.setLastName("Tiwari");
		user.setAadharNo("868768428");
		user.setAnnualIncome(100000);
		user.setCreatedDate(new Date());
		user.setModifiedDate(new Date());
		user.setDateOfBirth(new Date());
		user.setGender("M");
		user.setPhoneNo(898261395);

		when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
		when(userDao.save(user)).thenReturn(user);

		User result = userServices.updateUser(user);
		assertEquals(user.getEducation(), result.getEducation());
	}

	@Test
	void testUpdateUserNegative() {
		User user = new User();
		when(userDao.findById(user.getUserId())).thenThrow(new NullPointerException());
		assertEquals(null, userServices.updateUser(user));
	}

	@Test
	void getProductByProductId() {
		Product product = new Product();
		product.setProductId(1L);
		Mockito.when(productDao.getProductByProductId(product.getProductId())).thenReturn(product);
		assertEquals(product, userServices.getProductByProductId(product.getProductId()));
	}

	@Test
	void getProductByProductIdNegative() {
		Product product = new Product();
		product.setProductId(1L);
		Mockito.when(productDao.getProductByProductId(product.getProductId())).thenThrow(new NullPointerException());
		assertEquals(null, userServices.getProductByProductId(product.getProductId()));
	}

	@Test
	void checkUserWithProduct() {
//		Product product = new Product();
		long productId = 1L;
		long userId = 11L;
		List<CustomerPolicyDetails> customerPolicyDetails = null;
		Mockito.when(customerPolicyDetailsDao.checkUserWithOrder(productId, userId)).thenReturn(customerPolicyDetails);
		assertEquals(customerPolicyDetails, userServices.checkUserWithProduct(productId, userId));
	}

	@Test
	void checkUserWithProductNegative() {
//		Product product = new Product();
		long productId = 1L;
		long userId = 11L;
//		List<CustomerPolicyDetails> customerPolicyDetails = null;
		Mockito.when(customerPolicyDetailsDao.checkUserWithOrder(productId, userId))
				.thenThrow(new NullPointerException());
		assertEquals(null, userServices.checkUserWithProduct(productId, userId));
	}

	@Test
	void testAddClaim() {
		Claim claim = new Claim();
		claim.setClaimStatus("Not Claimed");
		claim.setCustomerName("Abhishek");
		claim.setNomineeName("Rahul");
		Mockito.when(claimDao.save(claim)).thenReturn(claim);
		assertEquals(claim, userServices.addClaim(claim));

	}

	@Test
	void testAddClaimNegative() {
		Claim claim = new Claim();
		Mockito.when(claimDao.save(claim)).thenThrow(new NullPointerException());
		assertEquals(null, userServices.addClaim(claim));

	}

//	@Test
//	void testPayAmount() {
//		long orderId = 169L;
//		int amount = 1000;
//		CustomerPolicyDetails orderDetails = customerPolicyDetailsDao.getOne(orderId);
//		Mockito.when(customerPolicyDetailsDao.payAmount(amount, orderId, new Date())).thenReturn(orderDetails);
//		assertEquals(orderDetails, userServices.payAmount(amount, orderId));
//	}

//	@Test
//	void testPayAmountNegative() {
//		long orderId = 169L;
//		int amount = 1000;
////		CustomerPolicyDetails orderDetails = customerPolicyDetailsDao.getOne(orderId);
//		Mockito.when(customerPolicyDetailsDao.payAmount(amount, orderId, new Date()))
//				.thenThrow(new NullPointerException());
//		assertEquals(null, userServices.payAmount(amount, orderId));
//	}

}
