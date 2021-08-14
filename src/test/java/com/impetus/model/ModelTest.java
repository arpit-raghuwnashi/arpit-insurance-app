package com.impetus.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.impetus.dto.ClaimDTO;
import com.impetus.dto.ContactUsDTO;
import com.impetus.dto.CustomerPolicyDetailsDTO;
import com.impetus.dto.DependentDTO;
import com.impetus.dto.InvoiceDataDTO;
import com.impetus.dto.NomineeDTO;
import com.impetus.dto.ProductDTO;
import com.impetus.dto.UserDTO;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.Dependent;
import com.impetus.entity.Nominee;
import com.impetus.entity.Product;
import com.impetus.entity.User;
import com.impetus.utility.CustomResponse;
import com.impetus.utility.InsuranceConstant;

@RunWith(SpringRunner.class)
@SpringBootTest
class ModelTest {

	private static final Logger logger = LoggerFactory.getLogger(ModelTest.class);

	@Test
	void UserModelSetterGetterTest() {
		logger.info("Inside UserModelSetterGetterTest");

		Date date = new Date();
		UserDTO user = new UserDTO();
		user.setFirstName("Kunal");
		user.setLastName("sharma");
		user.setEmail("sharmakunal1198@gmail.com");
		user.setAadharNo("868768428");
		user.setAnnualIncome(100000);
		user.setCreatedDate(date);
		user.setModifiedDate(date);
		user.setDateOfBirth(date);
		user.setEducation("Graduate");
		user.setGender("Male");
		user.setHasBp(false);
		user.setOccupation("er");
		user.setPassword("1234");
		user.setPolicyCount(0);
		user.setRoleId(1);
		user.setUserId(12);
		user.setOtp(92723);
		user.setPhoneNo(9999111122l);

		assertEquals(9999111122l, user.getPhoneNo());
		assertEquals("sharma", user.getLastName());
		assertEquals("868768428", user.getAadharNo());
		assertEquals(100000, user.getAnnualIncome());
		assertEquals(date, user.getCreatedDate());
		assertEquals(date, user.getModifiedDate());
		assertEquals(date, user.getDateOfBirth());
		assertEquals("Graduate", user.getEducation());
		assertEquals("Male", user.getGender());
		assertEquals("sharmakunal1198@gmail.com", user.getEmail());
		assertEquals(12, user.getUserId());
		assertEquals("Kunal", user.getFirstName());
		assertEquals(92723, user.getOtp());
		assertEquals(1, user.getRoleId());
		assertEquals(false, user.isHasBp());
		assertEquals("er", user.getOccupation());
		assertEquals("1234", user.getPassword());
		assertEquals(0, user.getPolicyCount());
		assertNotNull(user.toString());

	}

	@Test
	void UserModelConstructorTest() {
		logger.info("Inside UserModelConstructorTest");

		Date date = new Date();
		UserDTO user = new UserDTO();
		user.setFirstName("Kunal");
		user.setLastName("sharma");
		user.setEmail("sharmakunal1198@gmail.com");
		user.setAadharNo("868768428");
		user.setAnnualIncome(100000);
		user.setCreatedDate(date);
		user.setModifiedDate(date);
		user.setDateOfBirth(date);
		user.setEducation("Graduate");
		user.setGender("Male");
		user.setHasBp(false);
		user.setOccupation("er");
		user.setPassword("1234");
		user.setPolicyCount(0);
		user.setRoleId(1);
		user.setUserId(12);
		user.setOtp(92723);
		user.setPhoneNo(9999111122l);
		UserDTO result = new UserDTO(user);

		assertNotNull(result);
	}

	@Test
	void customerPolicyDetailTest1() {
		logger.info("Inside customerPolicyDetailTest1");
		Date date = new Date();
		CustomerPolicyDetailsDTO policyDetails1 = new CustomerPolicyDetailsDTO();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setOrderDate(date);
		policyDetails1.setEndDate(date);
		policyDetails1.setModifiedDate(date);
		policyDetails1.setDiscount(10);
		policyDetails1.setStatus("active");
		policyDetails1.setProductId(12l);
		policyDetails1.setPolicyId(2l);
		policyDetails1.setStartDate(date);

		assertEquals(45L, policyDetails1.getOrderId());
		assertEquals(30L, policyDetails1.getUserId());
		assertEquals(2, policyDetails1.getNumberOfDependent());
		assertEquals(500000, policyDetails1.getPremiumAmount());
		assertEquals(50000, policyDetails1.getAmountPaid());
		assertEquals("Quaterly", policyDetails1.getPaymentFrequency());
		assertEquals(date, policyDetails1.getOrderDate());
		assertEquals(date, policyDetails1.getEndDate());
		assertEquals(date, policyDetails1.getModifiedDate());
		assertEquals(10, policyDetails1.getDiscount());
		assertEquals("active", policyDetails1.getStatus());
		assertEquals(12l, policyDetails1.getProductId());
		assertEquals(2l, policyDetails1.getPolicyId());
		assertEquals(date, policyDetails1.getStartDate());

		assertNotNull(policyDetails1.toString());

	}

	@Test
	void customerPolicyDetailsConstructorTest() {
		logger.info("Inside customerPolicyDetailsConstructorTest");
		Date date = new Date();
		CustomerPolicyDetailsDTO policyDetails1 = new CustomerPolicyDetailsDTO();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setOrderDate(date);
		policyDetails1.setEndDate(date);
		policyDetails1.setModifiedDate(date);
		policyDetails1.setDiscount(10);
		policyDetails1.setStatus("active");
		policyDetails1.setProductId(12l);
		policyDetails1.setPolicyId(2l);
		policyDetails1.setStartDate(date);

		CustomerPolicyDetailsDTO result = new CustomerPolicyDetailsDTO(policyDetails1);
		assertNotNull(result);
	}
	
	@Test
	void productSetterGetterTest() {
		logger.info("Inside productSetterGetterTest");
		Date date=new Date();
		ProductDTO product=new ProductDTO();
		product.setProductId(2);
		product.setProductName("PKMN");
		product.setPolicyId(12);
		product.setMaxAgeLimit(40);
		product.setMinAgeLimit(20);
		product.setNumberOfYearsCovered(20);
		product.setProductDescription("XXXX");
		product.setModifiedDate(date);
		product.setCreatedDate(date);
		product.setSumAssured(1000000);
		product.setMinNumDependents(1);
		product.setMaxNumDependents(4);
		
		assertEquals(2, product.getProductId());
		assertEquals("PKMN", product.getProductName());
		assertEquals(12, product.getPolicyId());
		assertEquals(40, product.getMaxAgeLimit());
		assertEquals(20, product.getMinAgeLimit());
		assertEquals(20, product.getNumberOfYearsCovered());
		assertEquals("XXXX", product.getProductDescription());
		assertEquals(date, product.getModifiedDate());
		assertEquals(date, product.getCreatedDate());
		assertEquals(1000000, product.getSumAssured());
		assertEquals(1, product.getMinNumDependents());
		assertEquals(4, product.getMaxNumDependents());
		
		assertNotNull(product.toString());
	}
	
	@Test
	void productConstructorTest() {
		logger.info("Inside productConstructorTest");
		Date date=new Date();
		ProductDTO product=new ProductDTO();
		product.setProductId(2);
		product.setProductName("PKMN");
		product.setPolicyId(12);
		product.setMaxAgeLimit(40);
		product.setMinAgeLimit(20);
		product.setNumberOfYearsCovered(20);
		product.setProductDescription("XXXX");
		product.setModifiedDate(date);
		product.setCreatedDate(date);
		product.setSumAssured(1000000);
		product.setMinNumDependents(1);
		product.setMaxNumDependents(4);
		
		ProductDTO result=new ProductDTO(product);
		assertNotNull(result);
	}

	@Test
	void dependentTest1() {
		logger.info("Inside dependentTest1");
	
		DependentDTO dependent=new DependentDTO();
		dependent.setUserId(12l);
		dependent.setDepenedentId(12313);
		dependent.setFirstName("Arpit");
		dependent.setLastName("Raghuwanshi");
		dependent.setMiddleName("Singh");
		dependent.setAge(23);
		dependent.setGender("M");
		dependent.setOrderId(123l);
		
		assertEquals(Long.valueOf(12l), dependent.getUserId());
		assertEquals(12313, dependent.getDepenedentId());
		assertEquals("Arpit", dependent.getFirstName());
		assertEquals("Raghuwanshi", dependent.getLastName());
		assertEquals("Singh", dependent.getMiddleName());
		assertEquals(23, dependent.getAge());
		assertEquals("M", dependent.getGender());
		assertEquals(Long.valueOf(123l), dependent.getOrderId());
		
		assertNotNull(dependent.toString());
	
		assertNotNull(new DependentDTO(dependent));
	}
	
	@Test
	void nomineeTest1() {
		logger.info("inside nomineeTest");
		
		NomineeDTO nominee=new NomineeDTO();
		nominee.setNomineeID(12l);
		nominee.setFirstName("X");
		nominee.setLastName("Y");
		nominee.setEmail("x@gmail.com");
		nominee.setPhone(9999888899l);
		nominee.setUserId(12l);
		nominee.setOrderId(14l);
		
		assertEquals(Long.valueOf(12l), nominee.getNomineeID());
		assertEquals("X", nominee.getFirstName());
		assertEquals("Y", nominee.getLastName());
		assertEquals("x@gmail.com", nominee.getEmail());
		assertEquals(Long.valueOf(9999888899l), nominee.getPhone());
		assertEquals(Long.valueOf(12l), nominee.getUserId());
		assertEquals(Long.valueOf(14l), nominee.getOrderId());
		
		assertNotNull(nominee.toString());
		assertNotNull(new NomineeDTO(nominee));
	}
	
	@Test
	void customResponseTest() {
		logger.info("Inside customResponseTest");
		CustomResponse obj=new CustomResponse();
		obj.setCode(200);
		obj.setData("asd");
		obj.setMessage("xxx");
		Integer hash= obj.hashCode();
		assertNotNull(hash);
		assertNotNull(obj);
	}
	
	
	@Test
	void productEntityTest() {
		logger.info("Inside productEntityTest");
		ProductDTO product=new ProductDTO();
		product.setProductId(12l);
		product.setPolicyId(2);
		product.setProductName("XXXX");
		
		Product result= new Product(product);
		assertNotNull(result.toString());
	}

	@Test
	void customerPolicyDetails() {
		logger.info("Inside customerPolicyDetails");
		
		CustomerPolicyDetailsDTO details=new CustomerPolicyDetailsDTO();
		details.setOrderId(12l);
		details.setDiscount(12);
		
		CustomerPolicyDetails result=new CustomerPolicyDetails(details);
		assertNotNull(result.toString());
	}
	
	@Test
	void claimDtoTest1() {
		logger.info("Inside claimDtoTest1");
		Date date=new Date();
		
		ClaimDTO dto=new ClaimDTO();
		dto.setClaimId(100);
		dto.setClaimStatus(InsuranceConstant.PENDING);
		dto.setUserId(12);
		dto.setOrderId(102);
		dto.setCreatedDate(date);
		dto.setModifiedDate(date);
		dto.setNomineeId(23);
		dto.setNomineeName("Arpit");
		dto.setCustomerName("XXX");
		
		ClaimDTO result = new ClaimDTO(dto);
		assertNotNull(dto.toString());
		
		assertEquals(100, result.getClaimId());
		assertEquals(InsuranceConstant.PENDING, result.getClaimStatus());
		assertEquals(dto.getCreatedDate(), result.getCreatedDate());
		assertEquals(dto.getCustomerName(), result.getCustomerName());
		assertEquals(dto.getModifiedDate(), result.getModifiedDate());
		assertEquals(dto.getNomineeId(), result.getNomineeId());
		assertEquals(dto.getNomineeName(), result.getNomineeName());
		assertEquals(dto.getOrderId(), result.getOrderId());
		assertEquals(dto.getUserId(), result.getUserId());
	}
	
	@Test
	void invoiceDataDTOTest1() {
		logger.info("Inside InvoiceDataDTOTest1");
		
		Date date = new Date();
		UserDTO user = new UserDTO();
		user.setFirstName("Kunal");
		user.setLastName("sharma");
		user.setEmail("sharmakunal1198@gmail.com");
		user.setAadharNo("868768428");
		user.setAnnualIncome(100000);
		user.setCreatedDate(date);
		user.setModifiedDate(date);
		user.setDateOfBirth(date);
		user.setEducation("Graduate");
		user.setGender("Male");
		user.setHasBp(false);
		user.setOccupation("er");
		user.setPassword("1234");
		user.setPolicyCount(0);
		user.setRoleId(1);
		user.setUserId(12);
		user.setOtp(92723);
		user.setPhoneNo(9999111122l);
		User user2=new User(user);
		
		ProductDTO product=new ProductDTO();
		product.setProductId(2);
		product.setProductName("PKMN");
		product.setPolicyId(12);
		product.setMaxAgeLimit(40);
		product.setMinAgeLimit(20);
		product.setNumberOfYearsCovered(20);
		product.setProductDescription("XXXX");
		product.setModifiedDate(date);
		product.setCreatedDate(date);
		product.setSumAssured(1000000);
		product.setMinNumDependents(1);
		product.setMaxNumDependents(4);
		Product product2=new Product(product);

		NomineeDTO nominee=new NomineeDTO();
		nominee.setNomineeID(12l);
		nominee.setFirstName("X");
		nominee.setLastName("Y");
		nominee.setEmail("x@gmail.com");
		nominee.setPhone(9999888899l);
		nominee.setUserId(12l);
		nominee.setOrderId(14l);
		Nominee nominee2=new Nominee(nominee);
		
		DependentDTO dependent=new DependentDTO();
		dependent.setUserId(12l);
		dependent.setDepenedentId(12313);
		dependent.setFirstName("Arpit");
		dependent.setLastName("Raghuwanshi");
		dependent.setMiddleName("Singh");
		dependent.setAge(23);
		dependent.setGender("M");
		dependent.setOrderId(123l);
		Dependent dependent2=new Dependent(dependent);
		
		CustomerPolicyDetailsDTO policyDetails1 = new CustomerPolicyDetailsDTO();
		policyDetails1.setOrderId(45L);
		policyDetails1.setUserId(30L);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(500000);
		policyDetails1.setAmountPaid(50000);
		policyDetails1.setPaymentFrequency("Quaterly");
		policyDetails1.setOrderDate(date);
		policyDetails1.setEndDate(date);
		policyDetails1.setModifiedDate(date);
		policyDetails1.setDiscount(10);
		policyDetails1.setStatus("active");
		policyDetails1.setProductId(12l);
		policyDetails1.setPolicyId(2l);
		policyDetails1.setStartDate(date);
		CustomerPolicyDetails details=new CustomerPolicyDetails(policyDetails1);
		
		List<Dependent> dependents=Arrays.asList(dependent2);
		
		InvoiceDataDTO dto=new InvoiceDataDTO();
		dto.setUser(user2);
		dto.setProduct(product2);
		dto.setCustomerPolicyDetails(details);
		dto.setNominee(nominee2);
		dto.setDependents(dependents);
		
		assertEquals(user2, dto.getUser());
		assertEquals(details, dto.getCustomerPolicyDetails());
		assertEquals(dependents, dto.getDependents());
		assertEquals(nominee2, dto.getNominee());
		assertEquals(product2, dto.getProduct());
		
		assertNotNull(dto.toString());
		assertNotNull(new InvoiceDataDTO(user2, product2, details, nominee2, dependents));
	
	}
	
	@Test
    void contactUsDtoTest1() {
    	logger.info("Inside contactUsDtoTest1");
    	ContactUsDTO dto=new ContactUsDTO();
    	dto.setEmail("aman@gamil.com");
    	dto.setMessage("Hello");
    	dto.setName("Aman");
    	dto.setPhone(9998887766l);
    	
    	assertEquals("aman@gamil.com", dto.getEmail());
    	assertEquals("Hello", dto.getMessage());
    	assertEquals("Aman", dto.getName());
    	assertEquals(9998887766l, dto.getPhone());
    	
    	assertNotNull(dto.toString());
    	assertNotNull(new ContactUsDTO("", "", 9999999888l, ""));
    	
    }
	
}
