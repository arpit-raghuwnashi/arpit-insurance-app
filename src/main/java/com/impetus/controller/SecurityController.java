package com.impetus.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.entity.User;
import com.impetus.security.AuthenticationRequest;
import com.impetus.security.CustomUserDetailsService;
import com.impetus.security.JWTUtils;
import com.impetus.services.UnderWriterService;
import com.impetus.services.UserServices;
import com.impetus.utility.CustomResponse;
import com.impetus.utility.ExceptionUtils;

/**
 * Spring security controller class
 * @author arpit.raghuwanshi 
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class SecurityController {

	private static final Logger logger = Logger.getLogger(SecurityController.class);

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtils jwtToken;

	@Autowired
	private UnderWriterService underwriterService;
	
	@Autowired
	private UserServices userService;

	@PostMapping(value = "/token")
	public ResponseEntity<CustomResponse> generateToken(@Valid @RequestBody AuthenticationRequest request) {
		logger.info("Inside generateToken Method in SecurityRestImpl");
		try {
			
			logger.info("Username and Password :{} "+request.getUsername()+"   "+request.getPassword());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			logger.error("Bad Credentials Exception inside generateToken method in SecurityController");
			throw new ExceptionUtils("Invailid Username and password");
		}

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtToken.generateToken(userDetails);
		logger.debug(token);
		Object[] object = new Object[3];
		Long roleId = underwriterService.findUserRole(request.getUsername());
		User user = userService.getUserByEmail(request.getUsername());
		object[0] = roleId;
		object[1] = token;
		object[2] = user;
		CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(), "Token generated Sucessfully",
				object);
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

}
