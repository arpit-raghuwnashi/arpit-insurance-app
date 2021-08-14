package com.impetus.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.impetus.controller.SecurityController;
import com.impetus.entity.User;
import com.impetus.security.AuthenticationRequest;
import com.impetus.security.CustomUserDetailsService;
import com.impetus.security.CustomeUserDetails;
import com.impetus.security.JWTUtils;
import com.impetus.services.UnderWriterService;
import com.impetus.services.UserServices;
import com.impetus.utility.CustomResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
class SecurityControllerTest {

	private static final Logger logger = Logger.getLogger(SecurityControllerTest.class);

	@Autowired
	private SecurityController securityController;

	@MockBean
	private CustomUserDetailsService userDetailsService;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private JWTUtils jwtToken;

	@MockBean
	private UnderWriterService underwriterService;

	@MockBean
	private UserServices userService;

	@Test
	void generateTokenSuccessTest() {
		logger.info("Inside generateTokenSuccessTest method");
		AuthenticationRequest request = new AuthenticationRequest("aman@impetus.com", "1234");
		request.setUsername("aman@impetus.com");
		request.setPassword("1234");

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

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWpkZWVwQGltcGV0dXMuY29tIiwiZXhwIjoxNjE3OTM5MDgxLCJpYXQiOjE2MTc5MDMwODF9.FZGQ6rjsJFRtVXwJLT3paLy5HvD_DcOYmlEjO736Qsg";

		CustomeUserDetails userDetails = new CustomeUserDetails(user);

		when(userDetailsService.loadUserByUsername(request.getUsername())).thenReturn(userDetails);
		when(jwtToken.generateToken(userDetails)).thenReturn(token);
		when(underwriterService.findUserRole(request.getUsername())).thenReturn(user.getRoleId());
		when(userService.login(request.getUsername(), request.getPassword())).thenReturn(user);

		ResponseEntity<CustomResponse> result = securityController.generateToken(request);
		assertNotNull(result);
	}

	
	
}
