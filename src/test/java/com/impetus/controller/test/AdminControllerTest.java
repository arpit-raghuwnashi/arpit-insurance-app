package com.impetus.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//package com.impetus.controller.test;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impetus.controller.AdminController;
import com.impetus.dao.ClaimDao;
import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.DependentsDao;
import com.impetus.dao.NomineeDao;
import com.impetus.dao.PolicyDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.RoleRepository;
import com.impetus.dao.UserDao;
import com.impetus.dto.AdminDTO;
import com.impetus.dto.ProductDTO;
import com.impetus.dto.UserDTO;
import com.impetus.entity.Claim;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.security.CustomUserDetailsService;
import com.impetus.security.JWTUtils;
import com.impetus.security.JwtAuthenticationEntryPoint;
import com.impetus.services.AdminServices;
import com.impetus.services.UserServices;
import com.impetus.utility.CustomResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@WithMockUser
class AdminControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AdminServices adminServices;
	@MockBean
	private UserServices userServices;
	@MockBean
	private UserDao userDao;
	@MockBean
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;
	@MockBean
	private ProductDao productDao;
	@MockBean
	private PolicyDao policyDao;
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	@MockBean
	private JWTUtils jwtUtils;
	@MockBean
	private JwtAuthenticationEntryPoint jwt;
	@MockBean
	private RoleRepository rolerepo;
	@MockBean
	private NomineeDao nomineeDao;
	@MockBean
	private DependentsDao dependentsDao;
	@MockBean
	private ClaimDao claimDao;
	@Autowired
	AdminController adminController;

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	@Test
	void testHome() throws Exception
	{
		mockMvc.perform(get("/home"))
		.andExpect(status().isOk())
		.andExpect(content().string("Welcome Home"));
	}

//	@Test
//	void testRegister() throws MessagingException {
//		
//		User underwriter = new User();
//		underwriter.setFirstName("Rashi");
//		underwriter.setLastName("Jain");
//		underwriter.setEmail("rashi123");
//		underwriter.setGender("Female");
//		underwriter.setEducation("Post Graduation");
//		underwriter.setCreatedDate(new Date());
//		underwriter.setModifiedDate(new Date());
//		underwriter.setPhoneNo(123456789);
//		Mockito.when(adminServices.checkUnderwriter(underwriter.getEmail())).thenReturn(true);
//		when(userServices.addUser(underwriter)).thenReturn(underwriter);
//		
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
//				"Underwriter Registered and Mail Sent Sucessfully", underwriter);
//		ResponseEntity<CustomResponse> result = this.adminController.register(underwriter);
//		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);	
//		
//	}
	@Test
	void testRegisterElse() throws Exception {
		User mockUnderwriter = new User();
		mockUnderwriter.setFirstName("Rashi");
		mockUnderwriter.setLastName("Jain");
		mockUnderwriter.setEmail("rashi123@gmail.com");
		mockUnderwriter.setGender("Female");
		mockUnderwriter.setEducation("Post Graduation");
		mockUnderwriter.setCreatedDate(new Date());
		mockUnderwriter.setModifiedDate(new Date());
		mockUnderwriter.setPhoneNo(123456789);
		String inputInJson = this.mapToJson(mockUnderwriter);
		Mockito.when(adminServices.checkUnderwriter(mockUnderwriter.getEmail())).thenReturn(false);
		CustomResponse customResponse = new CustomResponse(208, "Underwriter Exists Already");
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc
				.perform(post("/underwriterregister").contentType("application/json").content(inputInJson).with(csrf()))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson)).andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);

	}

	@Test
	void testView() throws Exception {
		long roleId = 2;
		UserDTO mockUnderwriter1 = new UserDTO();
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
		UserDTO mockUnderwriter2 = new UserDTO();
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
		underwriters.add(new User(mockUnderwriter1));
		underwriters.add(new User(mockUnderwriter2));
		Mockito.when(adminServices.getAllUnderWriters(roleId)).thenReturn(underwriters);
		CustomResponse customResponse = new CustomResponse(302, "Underwriters Are Present", underwriters);
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/viewunderwriters/2").contentType("application/json").with(csrf()))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson)).andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);

	}

	@Test
	void testViewElse() throws Exception {
		long roleId = 2;
		List<User> underwriters = new ArrayList<User>();
		Mockito.when(adminServices.getAllUnderWriters(roleId)).thenReturn(underwriters);
		CustomResponse customResponse = new CustomResponse(404, "Underwriters Are Not Present", underwriters);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/viewunderwriters/2").contentType("application/json").with(csrf()))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testViewException() throws Exception {
		long roleId = 2;
		List<User> underwriters = null;
		Mockito.when(adminServices.getAllUnderWriters(roleId)).thenReturn(underwriters);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch underwriters");
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/viewunderwriters/2").contentType("application/json").with(csrf()))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testFetchCustomers() throws Exception {
		UserDTO mockCustomer1 = new UserDTO();
		mockCustomer1.setFirstName("Rashi");
		mockCustomer1.setLastName("Jain");
		mockCustomer1.setEmail("rashi123@gmail.com");
		mockCustomer1.setGender("Female");
		mockCustomer1.setEducation("Post Graduation");
		mockCustomer1.setCreatedDate(new Date());
		mockCustomer1.setModifiedDate(new Date());
		mockCustomer1.setPhoneNo(123456789);
		mockCustomer1.setRoleId(3);
		mockCustomer1.setPolicyCount(2);
		UserDTO mockCustomer2 = new UserDTO();
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
		UserDTO mockCustomer3 = new UserDTO();
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
		customers.add(new User(mockCustomer1));
		customers.add(new User(mockCustomer2));
		customers.add(new User(mockCustomer3));
		Mockito.when(adminServices.customers()).thenReturn(customers);
		CustomResponse customResponse = new CustomResponse(302, "Customers Are Present", customers);
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/fetchCustomers").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson)).andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);

	}

	@Test
	void testFetchCustomersElse() throws Exception {
		List<User> customers = new ArrayList<User>();
		Mockito.when(adminServices.customers()).thenReturn(customers);
		CustomResponse customResponse = new CustomResponse(404, "Customers Are Not Present", customers);
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/fetchCustomers").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson)).andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);
	}

	@Test
	void testFetchCustomersException() throws Exception {
		List<User> customers = null;
		Mockito.when(adminServices.customers()).thenReturn(customers);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch customers");
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/fetchCustomers").contentType("application/json"))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson))
				.andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);
	}

	@Test
	void testFetchDependents() throws Exception {
		long orderId = 45;
		long userId = 30;
		Dependent dependent1 = new Dependent();
		dependent1.setFirstName("Priya");
		dependent1.setMiddleName("");
		dependent1.setLastName("Yadav");
		dependent1.setGender("Female");
		dependent1.setAge(32);
		dependent1.setOrderId(orderId);
		dependent1.setUserId(userId);
		Dependent dependent2 = new Dependent();
		dependent2.setFirstName("Pawan");
		dependent2.setMiddleName("");
		dependent2.setLastName("Yadav");
		dependent2.setGender("Male");
		dependent2.setAge(28);
		dependent2.setOrderId(orderId);
		dependent2.setUserId(userId);
		List<Dependent> dependents = new ArrayList<Dependent>();
		dependents.add(dependent1);
		dependents.add(dependent2);
		Mockito.when(adminServices.dependents(orderId)).thenReturn(dependents);
		CustomResponse customResponse = new CustomResponse(302, "Dependents Are Present", dependents);
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/fetchDependents?orderId=45").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson)).andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);

	}

	@Test
	void testFetchDependentsElse() throws Exception {
		long orderId = 45;
		List<Dependent> dependents = new ArrayList<Dependent>();
		Mockito.when(adminServices.dependents(orderId)).thenReturn(dependents);
		CustomResponse customResponse = new CustomResponse(404, "Dependents Are Not Present", dependents);
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/fetchDependents?orderId=45").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson)).andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);

	}

	@Test
	void testFetchDependentsException() throws Exception {
		long orderId = 45;
		List<Dependent> dependents = null;
		Mockito.when(adminServices.dependents(orderId)).thenReturn(dependents);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch dependents");
		String customResponseInJson = this.mapToJson(customResponse);
		MvcResult result = mockMvc.perform(get("/fetchDependents?orderId=45").contentType("application/json"))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson))
				.andReturn();

		JSONAssert.assertEquals(customResponseInJson, result.getResponse().getContentAsString(), false);
	}

	@Test
	void testFetchNominee() throws Exception {
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
		Mockito.when(adminServices.getNominee(orderId)).thenReturn(nominee);
		CustomResponse customResponse = new CustomResponse(302, "Nominee Is Present", nominee);
		String customResponseInJson = this.mapToJson(customResponse);

		mockMvc.perform(get("/fetchNominee?orderId=45").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));

	}

	@Test
	void testFetchNomineeElse() throws Exception {
		long orderId = 45;
		List<Nominee> nominee = new ArrayList<Nominee>();
		Mockito.when(adminServices.getNominee(orderId)).thenReturn(nominee);
		CustomResponse customResponse = new CustomResponse(404, "Nominee Is Not Present", nominee);
		String customResponseInJson = this.mapToJson(customResponse);

		mockMvc.perform(get("/fetchNominee?orderId=45").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));
	}

	@Test
	void testFetchNomineeException() throws Exception {
		long orderId = 45;
		List<Nominee> nominee = null;
		Mockito.when(adminServices.getNominee(orderId)).thenReturn(nominee);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch nominee");
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchNominee?orderId=45").contentType("application/json"))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testCustomerPolicyDetails() throws Exception {
		long orderId = 45;
		long userId = 30;
		long productId = 4;
		long policyId = 1;
		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(orderId);
		policyDetails1.setUserId(userId);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setClaimStatus("false");
		policyDetails1.setStatus("Active");
		policyDetails1.setProductId(productId);
		policyDetails1.setPolicyId(policyId);
		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		policyDetails.add(policyDetails1);
		Mockito.when(adminServices.userPolicyDetails(userId)).thenReturn(policyDetails);
		CustomResponse customResponse = new CustomResponse(302, "Customer Policy Details Are Present", policyDetails);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/customerPolicyDetails?userId=30").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson));

	}

	@Test
	void testCustomerPolicyDetailsElse() throws Exception {
		long userId = 30;
		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		Mockito.when(adminServices.userPolicyDetails(userId)).thenReturn(policyDetails);
		CustomResponse customResponse = new CustomResponse(404, "Customer Policy Details Are Not Present",
				policyDetails);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/customerPolicyDetails?userId=30").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testCustomerPolicyDetailsException() throws Exception {
		long userId = 30;
		List<CustomerPolicyDetails> policyDetails = null;
		Mockito.when(adminServices.userPolicyDetails(userId)).thenReturn(policyDetails);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch customer policy details");
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/customerPolicyDetails?userId=30").contentType("application/json"))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testFetchProduct() throws Exception {
		long policyId = 1;
		long productId = 4;
		Product product1 = new Product();
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(18);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(policyId);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		List<Product> product = new ArrayList<Product>();
		product.add(product1);
		Mockito.when(adminServices.getProduct(productId)).thenReturn(product);
		CustomResponse customResponse = new CustomResponse(302, "Product Is Present", product);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchProduct?productId=4").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));

	}

	@Test
	void testFetchProductElse() throws Exception {
		long productId = 4;
		List<Product> product = new ArrayList<Product>();
		Mockito.when(adminServices.getProduct(productId)).thenReturn(product);
		CustomResponse customResponse = new CustomResponse(404, "Product Is Not Present", product);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchProduct?productId=4").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));
	}

	@Test
	void testFetchProductException() throws Exception {
		long productId = 4;
		List<Product> product = null;
		Mockito.when(adminServices.getProduct(productId)).thenReturn(product);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch product");
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchProduct?productId=4").contentType("application/json"))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testTotalCount() throws Exception {
		AdminDTO admin = new AdminDTO();
		admin.setTotalCustomers(500);
		admin.setTotalPolicies(12);
		admin.setTotalTypeOfPolicies(3);
		admin.setTotalPendingPolicies(5);
		admin.setTotalSuccessfulClaims(50);
		admin.setTotalUnderwriters(4);
		admin.setTotalUsers(750);
		Mockito.when(adminServices.total()).thenReturn(admin);
		CustomResponse customResponse = new CustomResponse(200, "Counting has been done successfully", admin);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/total").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));

		Mockito.when(adminServices.total()).thenThrow(new NullPointerException());
		CustomResponse customResponse2 = new CustomResponse(500, "Could not count");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.INTERNAL_SERVER_ERROR),
				adminController.totalCount());
	}

	@Test
	void testGetProducts() throws Exception {
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

		Product product2 = new Product();
		product2.setProductName("Eyes Safety");
		product2.setCreatedDate(new Date());
		product2.setModifiedDate(new Date());
		product2.setMinAgeLimit(18);
		product2.setMaxAgeLimit(45);
		product2.setMinNumDependents(0);
		product2.setMaxNumDependents(2);
		product2.setPolicyId(2);
		product2.setSumAssured(300000);
		product2.setNumberOfYearsCovered(7);
		List<Product> products = new ArrayList<Product>();
		products.add(product1);
		products.add(product2);
		Mockito.when(adminServices.getProducts()).thenReturn(products);
		String responseInJson = this.mapToJson(products);
		mockMvc.perform(get("/products").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(responseInJson));

	}

//	@Test
//	void testaddProduct() throws Exception {
//		ProductDTO product = new ProductDTO();
//		product.setProductName("Life Safety");
//		product.setCreatedDate(new Date());
//		product.setModifiedDate(new Date());
//		product.setMinAgeLimit(18);
//		product.setMaxAgeLimit(45);
//		product.setMinNumDependents(0);
//		product.setMaxNumDependents(3);
//		product.setPolicyId(3);
//		product.setSumAssured(700000);
//		product.setNumberOfYearsCovered(15);
//		product.setProductDescription("it is good");
//		
//		Product product2 =  new Product(product);
//		
//		Mockito.when(adminServices.addProduct(product2)).thenReturn(product2);
//		CustomResponse customResponse = new CustomResponse(201, "Product added Successfully", product2);
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse, HttpStatus.CREATED),
//				adminController.addProduct(product));
//
//		ProductDTO exceptionProduct = null;
//		Mockito.when(adminServices.addProduct(product2)).thenReturn(product2);
//		CustomResponse customResponse2 = new CustomResponse(500, "Could not Add Product");
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.INTERNAL_SERVER_ERROR),
//				adminController.addProduct(exceptionProduct));
//	}

	@Test
	void testupdateProduct() throws Exception {
		ProductDTO product1 = new ProductDTO();
		product1.setProductId(1L);
		product1.setProductName("Life Safety");
		product1.setCreatedDate(new Date());
		product1.setModifiedDate(new Date());
		product1.setMinAgeLimit(16);
		product1.setMaxAgeLimit(45);
		product1.setMinNumDependents(0);
		product1.setMaxNumDependents(3);
		product1.setPolicyId(3);
		product1.setSumAssured(700000);
		product1.setNumberOfYearsCovered(15);
		product1.setProductDescription("it is good");
		
//		Product productEntity =  new Product(product1);
		
//		Mockito.when(productDao.getProductByProductId(productEntity.getProductId())).thenReturn(productEntity);
//		Mockito.when(adminServices.updateProduct(productEntity)).thenReturn(productEntity);
//		CustomResponse customResponse1 = new CustomResponse(200, "Updated Product", productEntity);
//		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
//				adminController.updateProduct(product1));

		ProductDTO product2 = new ProductDTO();
		product2.setProductId(1L);
		product2.setProductName("Life Safety");
		product2.setCreatedDate(new Date());
		product2.setModifiedDate(new Date());
		product2.setMinAgeLimit(16);
		product2.setMaxAgeLimit(45);
		product2.setMinNumDependents(0);
		product2.setMaxNumDependents(3);
		product2.setPolicyId(3);
		product2.setSumAssured(700000);
		product2.setNumberOfYearsCovered(15);
		product2.setProductDescription("it is good");
		Mockito.when(productDao.getProductByProductId(product2.getProductId())).thenReturn(null);
//		Mockito.when(adminServices.updateProduct(product2)).thenReturn(product2);
		CustomResponse customResponse2 = new CustomResponse(400, "Product With Given Id not found");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.BAD_REQUEST),
				adminController.updateProduct(product2));
	}

	@Test
	void testdeleteProduct() throws Exception {
		Product product1 = new Product();
		product1.setProductId(1L);
		Mockito.when(adminServices.checkProductIsDeletable(product1.getProductId())).thenReturn(true);
		Mockito.when(adminServices.deleteProduct(product1.getProductId())).thenReturn(product1);
		CustomResponse customResponse1 = new CustomResponse(200, "Deleted Product", product1);
		assertEquals(new ResponseEntity<CustomResponse>(customResponse1, HttpStatus.OK),
				adminController.deleteProduct(product1.getProductId()));

		Product product2 = new Product();
		product2.setProductId(2L);
		Mockito.when(adminServices.checkProductIsDeletable(product2.getProductId())).thenReturn(false);
//		Mockito.when(adminServices.deleteProduct(product1.getProductId())).thenReturn(product2);
		CustomResponse customResponse2 = new CustomResponse(409, "Product Cannot be Deleted");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.CONFLICT),
				adminController.deleteProduct(product2.getProductId()));
	}

	@Test
	void testFetchClaims() throws Exception {
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
		mockClaim2.setClaimStatus("Under Process");
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
		Mockito.when(adminServices.getClaims()).thenReturn(claims);
		CustomResponse customResponse = new CustomResponse(302, "Claims Are Present", claims);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchClaims").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));

	}

	@Test
	void testFetchClaimsElse() throws Exception {
		List<Claim> claims = new ArrayList<Claim>();
		Mockito.when(adminServices.getClaims()).thenReturn(claims);
		CustomResponse customResponse = new CustomResponse(404, "Claims Are Not Present", claims);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchClaims").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));
	}

	@Test
	void testFetchClaimsException() throws Exception {
		List<Claim> claims = null;
		Mockito.when(adminServices.getClaims()).thenReturn(claims);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch Claim Detatails");
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/fetchClaims").contentType("application/json")).andExpect(status().isInternalServerError())
				.andExpect(content().string(customResponseInJson));
	}

	@Test
	void testGetCustomerPolicyDetailsByOrderId() throws Exception {
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
		Mockito.when(adminServices.userPolicyDetailByOrderId(orderId)).thenReturn(policyDetails);
		CustomResponse customResponse = new CustomResponse(302, "Customer Policy Detail Is Present", policyDetails);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/customerPolicyDetailByOrderId?orderId=45").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testGetCustomerPolicyDetailsByOrderIdElse() throws Exception {
		long orderId = 45;
		List<CustomerPolicyDetails> policyDetails = new ArrayList<CustomerPolicyDetails>();
		Mockito.when(adminServices.userPolicyDetailByOrderId(orderId)).thenReturn(policyDetails);
		CustomResponse customResponse = new CustomResponse(404, "Customer Policy Detail Is Not Present", policyDetails);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/customerPolicyDetailByOrderId?orderId=45").contentType("application/json"))
				.andExpect(status().isOk()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testGetCustomerPolicyDetailsByOrderIdException() throws Exception {
		long orderId = 45;
		List<CustomerPolicyDetails> policyDetails = null;
		Mockito.when(adminServices.userPolicyDetailByOrderId(orderId)).thenReturn(policyDetails);
		CustomResponse customResponse = new CustomResponse(500, "Could not fetch customer policy detail");
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/customerPolicyDetailByOrderId?orderId=45").contentType("application/json"))
				.andExpect(status().isInternalServerError()).andExpect(content().string(customResponseInJson));
	}

	@Test
	void testUpdateClaimStatus() throws Exception {
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
		Claim mockClaim2 = new Claim();
		mockClaim2.setClaimId(claimId);
		mockClaim2.setClaimStatus("Finished");
		mockClaim2.setCustomerName("Rashi Jain");
		mockClaim2.setNomineeName("Sakshi Jain");
		mockClaim2.setCreatedDate(new Date());
		mockClaim2.setModifiedDate(new Date());
		mockClaim2.setNomineeId(45);
		mockClaim2.setUserId(57);
		mockClaim2.setOrderId(34);
		Mockito.when(adminServices.getClaimByClaimId(claimId)).thenReturn(mockClaim1);
		Mockito.when(adminServices.updateClaim(mockClaim1)).thenReturn(mockClaim2);
		CustomResponse customResponse = new CustomResponse(302, "Claim Record Is Updated Succesfully", mockClaim2);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/updateClaimStatus?claimId=4").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));
	}

	@Test
	void UpdateClaimStatusElse() throws Exception {
		long claimId = 4;
		Claim claim = null;
		Mockito.when(adminServices.getClaimByClaimId(claimId)).thenReturn(claim);
		CustomResponse customResponse = new CustomResponse(404, "Claim Record Is Not Present", claim);
		String customResponseInJson = this.mapToJson(customResponse);
		mockMvc.perform(get("/updateClaimStatus?claimId=4").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(customResponseInJson));

		Mockito.when(adminServices.getClaimByClaimId(claimId)).thenThrow(new NullPointerException());
		CustomResponse customResponse2 = new CustomResponse(500, "Could not fetch claim record");
		assertEquals(new ResponseEntity<CustomResponse>(customResponse2, HttpStatus.INTERNAL_SERVER_ERROR),
				adminController.updateClaimStatus(claimId));

	}
}
