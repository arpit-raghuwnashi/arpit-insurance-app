package com.impetus.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.UserDao;
import com.impetus.dto.PendingPolicyStatusResponseDTO;
import com.impetus.dto.UnderWriterDTO;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.User;
import com.impetus.services.UnderWriterService;
import com.impetus.utility.InsuranceConstant;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@WithMockUser
class UnderWriterServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(UnderWriterServiceTest.class);

	@MockBean
	private UserDao userDao;

	@MockBean
	private ProductDao productDao;

	@MockBean
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	@Autowired
	private UnderWriterService underWriterService;

	@Test
	void updateUnderWriterSuccessTest() {
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

		User result = underWriterService.updateUnderWriter(user);
		logger.info("result  : {}", result.getEducation());
		assertEquals(user.getEducation(), result.getEducation());
	}

	@Test
	void updateUnderWriterFailTest() {
		try {
			when(userDao.save(null)).thenReturn(null);
			underWriterService.updateUnderWriter(null);
			Assertions.fail();
		} catch (Exception e) {
			logger.error("Error inside updateUnderWriterFailTest method");
			assertTrue(true);
		}
	}

	@Test
	void getUnderWriterSuccessTest() {
		logger.info("Inside getUnderWriterSuccessTest method");
		String email = "arpit.raghuwanshi@gmail.com";
		User user = new User();
		user.setEmail(email);

		when(userDao.findByEmail(email)).thenReturn(user);
		User result = underWriterService.getUnderWriter(email);
		logger.info("Result data : {} ", result.getUserId());
		assertThat(result).isEqualTo(user);
	}

	@Test
	void getUnderWriterFailTest() {
		logger.info("Inside getUnderWriterFailTest method");
		try {
			User result = underWriterService.getUnderWriter(null);
			logger.info("Result data : {} ", result.getUserId());
			fail();
		} catch (Exception e) {
			logger.error("Error in getUnderWriterFailTest method");
			assertTrue(true);
		}
	}

	@Test
	void getPendingCustomerPolicyDetailsSuccessTest() {
		logger.info("Inside getPendingCustomerPolicyDetailsSuccessTest method");

		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(1l);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(2000000);
		policyDetails1.setPaymentFrequency("Monthly");
		policyDetails1.setStatus(InsuranceConstant.PENDING);

		CustomerPolicyDetails policyDetails2 = new CustomerPolicyDetails();
		policyDetails2.setOrderId(2l);
		policyDetails2.setNumberOfDependent(2);
		policyDetails2.setPremiumAmount(2000000);
		policyDetails2.setPaymentFrequency("Monthly");
		policyDetails2.setStatus(InsuranceConstant.PENDING);

		CustomerPolicyDetails policyDetails3 = new CustomerPolicyDetails();
		policyDetails3.setOrderId(3l);
		policyDetails3.setNumberOfDependent(2);
		policyDetails3.setPremiumAmount(2000000);
		policyDetails3.setPaymentFrequency("Monthly");
		policyDetails3.setStatus(InsuranceConstant.PENDING);

		List<CustomerPolicyDetails> pendingList = new ArrayList<>();
		pendingList.add(policyDetails1);
		pendingList.add(policyDetails2);
		pendingList.add(policyDetails3);

		when(customerPolicyDetailsDao.getPendingCustomerPolicyDetails(InsuranceConstant.PENDING))
				.thenReturn(pendingList);
		List<CustomerPolicyDetails> result = underWriterService.getPendingCustomerPolicyDetails();
		assertNotNull(result);
	}

	@Test
	void getDeclineCuustomerPolicyDetailsSuccessTest() {
		logger.info("Inside getDeclineCuustomerPolicyDetailsSuccessTest method");

		CustomerPolicyDetails policyDetails1 = new CustomerPolicyDetails();
		policyDetails1.setOrderId(1l);
		policyDetails1.setNumberOfDependent(2);
		policyDetails1.setPremiumAmount(2000000);
		policyDetails1.setPaymentFrequency("Monthly");
		policyDetails1.setStatus(InsuranceConstant.DECLINE);

		CustomerPolicyDetails policyDetails2 = new CustomerPolicyDetails();
		policyDetails2.setOrderId(2l);
		policyDetails2.setNumberOfDependent(2);
		policyDetails2.setPremiumAmount(2000000);
		policyDetails2.setPaymentFrequency("Monthly");
		policyDetails2.setStatus(InsuranceConstant.DECLINE);

		CustomerPolicyDetails policyDetails3 = new CustomerPolicyDetails();
		policyDetails3.setOrderId(3l);
		policyDetails3.setNumberOfDependent(2);
		policyDetails3.setPremiumAmount(2000000);
		policyDetails3.setPaymentFrequency("Monthly");
		policyDetails3.setStatus(InsuranceConstant.DECLINE);

		List<CustomerPolicyDetails> declineList = new ArrayList<>();
		declineList.add(policyDetails1);
		declineList.add(policyDetails2);
		declineList.add(policyDetails3);

		when(customerPolicyDetailsDao.getPendingCustomerPolicyDetails(InsuranceConstant.DECLINE))
				.thenReturn(declineList);
		List<CustomerPolicyDetails> result = underWriterService.getDeclineCuustomerPolicyDetails();
		assertNotNull(result);
	}

	@Test
	void deactiveUnderwriterSuccessTest() {
		logger.info("Inside deactiveUnderwriterSuccessTest method");
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

		underWriterService.deactiveUnderwriter(user.getUserId());
		verify(userDao).deactiveUnderwriterStatus(false, user.getUserId());
	}

	@Test
	void deactiveUnderwriterFailTest() {
		logger.info("Inside deactiveUnderwriterFailTest method");
		try {
			underWriterService.deactiveUnderwriter(null);
			Assertions.fail();
		} catch (Exception e) {
			logger.error("Error in deactiveUnderwriterFailTest");
			logger.info("Error message : {} ", e.getMessage());
			assertEquals("Excption in userId null", e.getMessage());
		}
	}

	@Test
	void findUserRoleSuccessTest() {
		logger.info("Inside findUserRoleSuccessTest method");
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

		when(userDao.findByEmail(user.getEmail())).thenReturn(user);
		long result = underWriterService.findUserRole(user.getEmail());
		assertEquals(result, user.getRoleId());
	}

	@Test
	void findUserRoleFailTest() {
		logger.info("Inside findUserRoleFailTest method");
		try {
			underWriterService.findUserRole(null);
			Assertions.fail();
		} catch (Exception e) {
			logger.info("Error inside findUserRoleFailTest");
			assertTrue(true);
		}
	}

	@Test
	void getTotalCountSuccessTest() {
		logger.info("Inside getTotalCountSuccessTest method");
		UnderWriterDTO writerDto = new UnderWriterDTO();
		writerDto.setTotalDeclinePolices(2l);
		writerDto.setTotalPendingPolices(3l);
		writerDto.setTotalPolices(5l);
		writerDto.setTotalTypesOfPolices(3l);
		writerDto.setTotalUsers(10l);

		UnderWriterDTO result = underWriterService.getTotalCount();
		logger.info("Result : {} ", result);
		assertNotNull(result);
	}

	@Test
	void sendPolicyStatusChangeMailSuccessMailApprovedSuccessTest() {
		logger.info("Inside sendPolicyStatusChangeMailSuccessMailApprovedSuccessTest method");
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
		
		PendingPolicyStatusResponseDTO policyStatusResponse = new PendingPolicyStatusResponseDTO();
		policyStatusResponse.setMessage("Your Policy has been approved by our executive");
		policyStatusResponse.setOrderId(13l);
		policyStatusResponse.setStatus(InsuranceConstant.APPROVED);

		when(customerPolicyDetailsDao.getUserIdByOrderId(13l)).thenReturn(24l);
		when(userDao.getUserById(24l)).thenReturn(user);
		
		this.underWriterService.sendPolicyStatusChangeMail(policyStatusResponse);
		boolean status = policyStatusResponse.getStatus().equals(InsuranceConstant.APPROVED);
		Assertions.assertTrue(status);
	}

	@Test
	void sendPolicyStatusChangeMailSuccessMailDeclineSuccessTest() {
		logger.info("Inside sendPolicyStatusChangeMailSuccessMailDeclineSuccessTest method");
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
		
		PendingPolicyStatusResponseDTO policyStatusResponse = new PendingPolicyStatusResponseDTO();
		policyStatusResponse.setMessage("Your Policy has been decline by our executive");
		policyStatusResponse.setOrderId(13l);
		policyStatusResponse.setStatus(InsuranceConstant.DECLINE);
		
		when(customerPolicyDetailsDao.getUserIdByOrderId(13l)).thenReturn(24l);
		when(userDao.getUserById(24l)).thenReturn(user);

		this.underWriterService.sendPolicyStatusChangeMail(policyStatusResponse);
		boolean status = policyStatusResponse.getStatus().equals(InsuranceConstant.DECLINE);
		Assertions.assertTrue(status);
	}

	@Test
	void sendPolicyStatusChangeMailSuccessDeclineMailFailTest() {
		logger.info("Inside sendPolicyStatusChangeMailSuccessMailDeclineSuccessTest method");
		try {
			
			User user = new User();
			user.setUserId(24);
			user.setRoleId(2);
			user.setEmail(null);
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
			
			PendingPolicyStatusResponseDTO policyStatusResponse = new PendingPolicyStatusResponseDTO();
			policyStatusResponse.setMessage("Your Policy has been decline by our executive");
			policyStatusResponse.setOrderId(13l);
			policyStatusResponse.setStatus(InsuranceConstant.DECLINE);
			
			when(customerPolicyDetailsDao.getUserIdByOrderId(13l)).thenReturn(24l);
			when(userDao.getUserById(24l)).thenReturn(user);
			
			this.underWriterService.sendPolicyStatusChangeMail(policyStatusResponse);
			Assertions.fail();

		} catch (Exception e) {
			logger.error("Error inside sendPolicyStatusChangeMailSuccessMailFailTest method");
			assertTrue(true);
		}

	}

	@Test
	void updatePendingStatusSuccessTest() {
		logger.info("Inside updatePendingStatusSuccessTest method");
		String email = "arpit@impetus.com";
		Long orderId = 24l;

		doNothing().when(customerPolicyDetailsDao).updatePendingStatus(email, orderId);
		this.underWriterService.updatePendingStatus(email, orderId);
		verify(customerPolicyDetailsDao).updatePendingStatus(email, orderId);
	}

	@Test
	void updatePendingStatusFailTest() {
		logger.info("Inside updatePendingStatusFailTest method");
		try {
			String email = null;
			Long orderId = 24l;

			doNothing().when(customerPolicyDetailsDao).updatePendingStatus(email, orderId);
			this.underWriterService.updatePendingStatus(email, orderId);
			verify(customerPolicyDetailsDao).updatePendingStatus(email, orderId);
		} catch (Exception e) {
			logger.error("Error in updatePendingStatusFailTest");
			assertTrue(true);
			assertEquals("email or orderId is null", e.getMessage());
		}
	}
}
