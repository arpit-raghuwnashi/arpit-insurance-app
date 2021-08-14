package com.impetus.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.impetus.controller.UnderWriterController;
import com.impetus.dto.PendingPolicyStatusResponseDTO;
import com.impetus.dto.UnderWriterDTO;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.User;
import com.impetus.services.UnderWriterService;
import com.impetus.utility.CustomResponse;
import com.impetus.utility.InsuranceConstant;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
class UnderWriterControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(UnderWriterControllerTest.class);

	@MockBean
	private UnderWriterService underWriterService;

	@Autowired
	private UnderWriterController underWriterController;

//	@Test
//	void updateUnderWriterSuccessTest() {
//		logger.info("Inside updateUnderWriterSuccessTest method");
//		UserDTO user = new UserDTO();
//		user.setUserId(24);
//		user.setRoleId(2);
//		user.setEmail("aman@impetus.com");
//		user.setPassword("1234");
//		user.setStatus(true);
//		user.setEducation("Engineer");
//		user.setFirstName("Aman");
//		user.setLastName("Tiwari");
//		user.setAadharNo("868768428");
//		user.setAnnualIncome(100000);
//		user.setCreatedDate(new Date());
//		user.setModifiedDate(new Date());
//		user.setDateOfBirth(new Date());
//		user.setGender("M");
//		user.setPhoneNo(898261395);
//		
//		User user1 = new User(user);
//		
//		when(underWriterService.updateUnderWriter(user1)).thenReturn(user1);
//		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
//				"UnderWriter Data updated Sucessfully", user1);
//		ResponseEntity<CustomResponse> result = underWriterController.updateUnderWriter(user);
//		logger.info("Response data : {}", result);
//		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);
//	}
	
	@Test
	void updateUnderWriterFailTest() {
		logger.info("Inside updateUnderWriterFailTest method");
		when(underWriterService.updateUnderWriter(null)).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(), "Not Updated..");
		ResponseEntity<CustomResponse> result = underWriterController.updateUnderWriter(null);
		logger.info("Response data : {}", result);
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND), result);

	}


	@Test
	void getUnderWriterSuccessTest() {
		logger.info("Inside getUnderWriterSuccessTest method");
		User user = new User();
		user.setEmail("arpit.raghuwanshi@impetus.com");
		when(this.underWriterService.getUnderWriter(user.getEmail())).thenReturn(user);

		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"UnderWriter Data retrive successfully", user);
		ResponseEntity<CustomResponse> result = underWriterController.getUnderWriter(user.getEmail());
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);
	}
	
	@Test
	void getUnderWriterFailTest() {
		logger.info("Inside getUnderWriterFailTest method");
		User user = new User();
		user.setEmail(null);
		when(this.underWriterService.getUnderWriter(user.getEmail())).thenReturn(user);
		CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
				InsuranceConstant.NOT_AVAILABLE);
		ResponseEntity<CustomResponse> result = underWriterController.getUnderWriter(user.getEmail());
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND), result);
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

		when(this.underWriterService.getPendingCustomerPolicyDetails()).thenReturn(pendingList);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"PendingCustomerPolicyDetails pending status data retrive successfully", pendingList);
		ResponseEntity<CustomResponse> result = underWriterController.getPendingCustomerPolicyDetails();
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);

	}

	@Test
	void getPendingCustomerPolicyDetailsNullListTest() {
		logger.info("Inside getPendingCustomerPolicyDetailsSuccessTest method");
		when(this.underWriterService.getPendingCustomerPolicyDetails()).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
				"Pending Policy not found");
		ResponseEntity<CustomResponse> result = underWriterController.getPendingCustomerPolicyDetails();
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND), result);

	}

	@Test
	void getDeclineCustomerPolicyDetailsSuccessTest() {
		logger.info("Inside getDeclineCustomerPolicyDetailsSuccessTest");
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

		when(this.underWriterService.getDeclineCuustomerPolicyDetails()).thenReturn(declineList);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"getDeclineCustomerPolicyDetails decline status data retrive successfully", declineList);
		ResponseEntity<CustomResponse> result = underWriterController.getDeclineCustomerPolicyDetails();
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);

	}

	@Test
	void getDeclineCustomerPolicyDetailsNullListTest() {
		logger.info("Inside getDeclineCustomerPolicyDetailsNullListTest method");
		when(this.underWriterService.getDeclineCuustomerPolicyDetails()).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
				"Decline policise not found");
		ResponseEntity<CustomResponse> result = underWriterController.getDeclineCustomerPolicyDetails();
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND), result);

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

		doNothing().when(underWriterService).deactiveUnderwriter(user.getUserId());
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"Underwriter successfully deactivated");
		ResponseEntity<CustomResponse> result = underWriterController.deactiveUnderwriter(user.getUserId());
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);
	}
	
	@Test
	void deactiveUnderwriterFailTest() {
		logger.info("Inside deactiveUnderwriterFailTest method");

		doNothing().when(underWriterService).deactiveUnderwriter(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				"Exception occured while deactivated status");
		ResponseEntity<CustomResponse> result = underWriterController.deactiveUnderwriter(null);
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST), result);
	}
	
	
	@Test
	void updatePendingStatusSuccessTest() {
		logger.info("Inside updatePendingStatusSuccessTest method");
		PendingPolicyStatusResponseDTO policyStatusResponse = new PendingPolicyStatusResponseDTO();
		policyStatusResponse.setMessage("Your Policy has been approved by our executive");
		policyStatusResponse.setOrderId(13l);
		policyStatusResponse.setStatus(InsuranceConstant.APPROVED);
		
		doNothing().when(underWriterService).updatePendingStatus(policyStatusResponse.getStatus(), policyStatusResponse.getOrderId());
		doNothing().when(underWriterService).sendPolicyStatusChangeMail(policyStatusResponse);
		
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"Pending status updated successfully");
		ResponseEntity<CustomResponse> result = underWriterController.updatePendingStatus(policyStatusResponse);
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);

	}
	
	@Test
	void updatePendingStatusFailTest() {
		logger.info("Inside updatePendingStatusFailTest method");

		doNothing().when(underWriterService).updatePendingStatus(null, null);
		doNothing().when(underWriterService).sendPolicyStatusChangeMail(null);
		
		CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
				InsuranceConstant.NOT_AVAILABLE);
		ResponseEntity<CustomResponse> result = underWriterController.updatePendingStatus(null);
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST), result);

	}
	
	@Test
	void getUnderwriterDashboardCountSuccessTest() {
		logger.info("Inside getUnderwriterDashboardCountSuccessTest method");
		UnderWriterDTO writerDto = new UnderWriterDTO();
		writerDto.setTotalDeclinePolices(2l);
		writerDto.setTotalPendingPolices(3l);
		writerDto.setTotalPolices(5l);
		writerDto.setTotalTypesOfPolices(3l);
		writerDto.setTotalUsers(10l);
		
		when(underWriterService.getTotalCount()).thenReturn(writerDto);
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
				"UnderWriterDto counts retrieve successfully", writerDto);
		ResponseEntity<CustomResponse> result = underWriterController.getUnderwriterDashboardCount();
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.OK), result);
		
	}
	
	@Test
	void getUnderwriterDashboardCountFailTest() {
		logger.info("Inside getUnderwriterDashboardCountSuccessTest method");
		
		when(underWriterService.getTotalCount()).thenReturn(null);
		CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
				InsuranceConstant.NOT_AVAILABLE);
		ResponseEntity<CustomResponse> result = underWriterController.getUnderwriterDashboardCount();
		logger.info("Response : {} ", result.getBody());
		assertEquals(new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND), result);
		
	}
	
}
