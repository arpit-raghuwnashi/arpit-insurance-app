package com.impetus.controller;

import static com.impetus.utility.InsuranceConstant.UI_MAPPING_URL;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.dto.PendingPolicyStatusResponseDTO;
import com.impetus.dto.UnderWriterDTO;
import com.impetus.dto.UserDTO;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.User;
import com.impetus.services.UnderWriterService;
import com.impetus.utility.CustomResponse;
import com.impetus.utility.ExceptionUtils;
import com.impetus.utility.InsuranceConstant;

/**
 * UnderWriter controller
 * @author arpit.raghuwanshi
 */
@CrossOrigin(origins = UI_MAPPING_URL, maxAge = 3600)
@RestController
public class UnderWriterController {

	private static final Logger logger = LoggerFactory.getLogger(UnderWriterController.class);

	@Autowired
	private UnderWriterService underWriterService;

	/**
	 * Updates Underwriter
	 * 
	 * @return Updating under writer data
	 * 
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@PutMapping("/updateUnderWriter")
	public ResponseEntity<CustomResponse> updateUnderWriter(@Valid @RequestBody UserDTO userDto) {
		logger.info("Inside updateUnderWriter method inside controller");

		try {
			if (userDto != null) {
				User user = new User(userDto);

				User userObject = this.underWriterService.updateUnderWriter(user);
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"UnderWriter Data updated Sucessfully", userObject);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("User is null");
			}
		} catch (Exception e) {
			logger.info("Inside updateUnderWriter method inside controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(), "Not Updated..");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieving Underwriter table through email
	 * 
	 * @param email
	 * @return underwriter having that email registered
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@GetMapping("/getUnderWriter/{email}")
	public ResponseEntity<CustomResponse> getUnderWriter(@PathVariable("email") String email) {
		logger.info("inside method getUnderWriter");
		try {
			if (email != null) {
				User underwriter = this.underWriterService.getUnderWriter(email);
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"UnderWriter Data retrive successfully", underwriter);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("Email is null");
			}
		} catch (Exception e) {
			logger.error("Error getUnderWriter method in controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
					InsuranceConstant.NOT_AVAILABLE);
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieving pending status order policies
	 * 
	 * @return Pending order policies
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@GetMapping("/getPendingCustomerPolicyDetails")
	public ResponseEntity<CustomResponse> getPendingCustomerPolicyDetails() {
		logger.info("inside method getPendingCustomerPolicyDetails");
		try {
			List<CustomerPolicyDetails> pendingPolicyList = this.underWriterService.getPendingCustomerPolicyDetails();
			if (pendingPolicyList != null) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"PendingCustomerPolicyDetails pending status data retrive successfully", pendingPolicyList);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("Pending Policies not found");
			}
		} catch (Exception e) {
			logger.error("Inside getPendingCustomerPolicyDetails method in controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
					"Pending Policy not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieving declined order policies
	 * 
	 * @return Declined order policies
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@GetMapping("/getDeclineCustomerPolicyDetails")
	public ResponseEntity<CustomResponse> getDeclineCustomerPolicyDetails() {
		logger.info("inside method getDeclineCustomerPolicyDetails");
		try {
			List<CustomerPolicyDetails> declinePolicyList = this.underWriterService.getDeclineCuustomerPolicyDetails();
			if (declinePolicyList != null) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"getDeclineCustomerPolicyDetails decline status data retrive successfully", declinePolicyList);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("Decline Policies not found");
			}
		} catch (Exception e) {
			logger.error("Inside getDeclineCustomerPolicyDetails method in controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
					"Decline policise not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Updating the status of policy from pending to Approved or Decline
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@PutMapping("/updatePendingStatus")
	public ResponseEntity<CustomResponse> updatePendingStatus(
			@RequestBody PendingPolicyStatusResponseDTO statusResponse) {
		logger.info("inside method updatePendingStatus");
		try {
			if (statusResponse != null) {
				this.underWriterService.updatePendingStatus(statusResponse.getStatus(), statusResponse.getOrderId());
				this.underWriterService.sendPolicyStatusChangeMail(statusResponse);
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"Pending status updated successfully");
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("PendingPolicyStatusResponse object is null");
			}
		} catch (Exception e) {
			logger.error("Error inside updatePendingStatus method in controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					InsuranceConstant.NOT_AVAILABLE);
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deactivating underwriter
	 * 
	 * @param userId
	 * @throws Exception Change the status of underwriter to deactivate
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@DeleteMapping("/deactiveUnderwriter/{userId}")
	public ResponseEntity<CustomResponse> deactiveUnderwriter(@PathVariable Long userId) {
		logger.info("Inside deactiveUnderwriter method in UnderWriterController");
		try {
			if (userId != null) {
				underWriterService.deactiveUnderwriter(userId);
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"Underwriter successfully deactivated");
				logger.info("Underwriter deactive successfully");
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("UserId is null");
			}
		} catch (Exception e) {
			logger.error("Exception while deactivate status ", e);
			CustomResponse customResponse = new CustomResponse(HttpStatus.BAD_REQUEST.value(),
					"Exception occured while deactivated status");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Fetching Underwriter DashBoard Data
	 * 
	 * @return Underwriter DashBoard Data
	 */
	@PreAuthorize("hasAuthority('[ROLE_UNDERWRITER]')")
	@GetMapping("/dashboradCounts")
	public ResponseEntity<CustomResponse> getUnderwriterDashboardCount() {
		logger.info("inside method getUnderwriterDashboardCount");
		try {
			UnderWriterDTO underWriterDto = underWriterService.getTotalCount();
			if (underWriterDto != null) {
				CustomResponse customResponse = new CustomResponse(HttpStatus.OK.value(),
						"UnderWriterDto counts retrieve successfully", underWriterDto);
				return new ResponseEntity<>(customResponse, HttpStatus.OK);
			} else {
				throw new ExceptionUtils("Counts not found");
			}
		} catch (Exception e) {
			logger.error("Error inside getUnderwriterDashboardCount method in controller");
			CustomResponse customResponse = new CustomResponse(HttpStatus.NOT_FOUND.value(),
					InsuranceConstant.NOT_AVAILABLE);
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}
	}

}
