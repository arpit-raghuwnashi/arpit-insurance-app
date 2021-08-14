package com.impetus.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.dao.ProductDao;
import com.impetus.dao.UserDao;
import com.impetus.dto.PendingPolicyStatusResponseDTO;
import com.impetus.dto.UnderWriterDTO;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.entity.User;
import com.impetus.services.UnderWriterService;
import com.impetus.utility.ExceptionUtils;
import com.impetus.utility.InsuranceConstant;
import com.impetus.utility.SendEmailUtilis;

/**
 * Underwriter Services
 * 
 * @author arpit.raghuvanshi
 *
 */
@Service
public class UnderWriterServiceImpl implements UnderWriterService {

	private static final Logger logger = LoggerFactory.getLogger(UnderWriterServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	/**
	 * Update underwriter data
	 * 
	 * @throws ExceptionUtils
	 */
	@Override
	public User updateUnderWriter(User user) {
		logger.info("inside updateUnderWriter method in service");
		User user2 = null;
		try {
			if (user != null) {
				Optional<User> optional = userDao.findById(user.getUserId());
				if (optional.isPresent()) {
					User user3 = optional.get();
					user.setCreatedDate(user3.getCreatedDate());
					user.setModifiedDate(new Date());
					user2 = userDao.save(user);
					logger.info("UnderWriter data has been updated");
				}
			} else {
				throw new ExceptionUtils("User Not Found ");
			}
		} catch (Exception e) {
			logger.error("Error in updateUnderWriter in service impl ");
			throw new ExceptionUtils("Excption in updateUnderWriter in service");
		}
		return user2;
	}

	/**
	 * Retrieving underwriter details by emailId
	 * 
	 * @throws ExceptionUtils
	 */
	@Override
	public User getUnderWriter(String email) {
		logger.info("inside getUnderWriter method in service");
		User underwriter = null;
		try {
			if (email != null) {
				underwriter = userDao.findByEmail(email);
				logger.info("UnderWriter data retrive successfully ");
			} else {
				throw new ExceptionUtils("Email is null");
			}
		} catch (Exception e) {
			logger.error("Error in getUnderWriter in service ");
			throw new ExceptionUtils("Excption in getUnderWriter in service");
		}
		return underwriter;
	}

	/**
	 * Retrieving Pending Customer plicies
	 * 
	 * @throws ExceptionUtils
	 */
	@Override
	public List<CustomerPolicyDetails> getPendingCustomerPolicyDetails() {
		logger.info("inside getPendingCustomerPolicyDetails method in service");
		List<CustomerPolicyDetails> policyOrderDetails = null;
		try {
			policyOrderDetails = this.customerPolicyDetailsDao
					.getPendingCustomerPolicyDetails(InsuranceConstant.PENDING);
			logger.info("Pending ordered policy data retrive successfully ");
		} catch (Exception e) {
			logger.error("Error in getPendingCustomerPolicyDetails in service ");
			throw new ExceptionUtils("Excption in getPendingCustomerPolicyDetails in service");
		}
		return policyOrderDetails;
	}

	/**
	 * Retrieving declined Customer policies
	 * 
	 * @throws ExceptionUtils
	 */
	@Override
	public List<CustomerPolicyDetails> getDeclineCuustomerPolicyDetails() {
		logger.info("inside getDeclineCuustomerPolicyDetails method in service");
		List<CustomerPolicyDetails> policyOrderDetails = null;
		try {
			policyOrderDetails = this.customerPolicyDetailsDao
					.getPendingCustomerPolicyDetails(InsuranceConstant.DECLINE);
			logger.info("Decline ordered policy data retrive successfully ");
		} catch (Exception e) {
			logger.error("Error in getDeclineCuustomerPolicyDetails in service ");
			throw new ExceptionUtils("Excption in getDeclineCuustomerPolicyDetails in service");
		}
		return policyOrderDetails;
	}

	/**
	 * Deactivates underwriter
	 * 
	 * @param userId of underwriter
	 */
	@Override
	public void deactiveUnderwriter(Long userid) {
		logger.info("inside deactiveUnderwriter method in service");
		try {
			if (userid != null) {
				userDao.deactiveUnderwriterStatus(false, userid);
				logger.info("Underwriter Status successfully changed");
			} else {
				throw new ExceptionUtils("userid is null");
			}
		} catch (Exception e) {
			logger.error("Error in deactiveUnderwriter in service ", e);
			throw new ExceptionUtils("Excption in userId null");
		}

	}

	/**
	 * Approves or Declines a policy
	 * 
	 * @throws ExceptionUtils
	 */
	@Override
	public void updatePendingStatus(String status, Long orderId) {
		logger.info("inside updatePendingStatus method in service");
		try {
			if (status != null && orderId != null) {
				this.customerPolicyDetailsDao.updatePendingStatus(status, orderId);
				logger.info("Pending status updated successfully");
			} else {
				throw new ExceptionUtils("userid or orderId is null");
			}
		} catch (Exception e) {
			logger.error("Error in updatePendingStatus in service ");
			throw new ExceptionUtils("email or orderId is null");
		}
	}

	/**
	 * Retrieving user role through user email
	 * 
	 * @param emailId of user
	 */
	@Override
	public Long findUserRole(String email) {
		logger.info("inside findUserRole method in service");
		try {
			if (email != null) {
				Long roleId = userDao.findByEmail(email).getRoleId();
				logger.info("inside findUserRole method before return in service");
				return roleId;
			} else {
				throw new ExceptionUtils("Email is null");
			}
		} catch (Exception e) {
			logger.error("Error in findUserRole in service ");
			throw new ExceptionUtils("Exception in findUserRole in service");
		}
	}

	/**
	 * Fetching Underwriter DashBoard Data from Database
	 */
	@Override
	public UnderWriterDTO getTotalCount() {
		logger.info("inside getTotalCount method in UnderWriterServiceImpl");

		UnderWriterDTO writerDto = new UnderWriterDTO();
		try {
			writerDto.setTotalPolices(productDao.totalProducts());
			writerDto.setTotalUsers(userDao.totalUsers(3));
			writerDto.setTotalTypesOfPolices(3l);
			writerDto.setTotalPendingPolices(customerPolicyDetailsDao.getTotalPolices(InsuranceConstant.PENDING));
			writerDto.setTotalDeclinePolices(customerPolicyDetailsDao.getTotalPolices(InsuranceConstant.DECLINE));

			logger.info("Before return in getTotalCount method in UnderWriterServiceImpl");
			return writerDto;
		} catch (Exception e) {
			logger.error("Error in getTotalCount in underwriterController");
			throw new ExceptionUtils("Exception in getTotalCount in service : " + e);
		}
	}

	/**
	 * Sending policy status change mail to user
	 */

	@Override
	public void sendPolicyStatusChangeMail(PendingPolicyStatusResponseDTO statusResponse) {
		logger.info("Inside sendPolicyStatusChangeMail in UnderwriterService");
		try {
			Long userId=this.customerPolicyDetailsDao.getUserIdByOrderId(statusResponse.getOrderId());	
			String userMail= userDao.getUserById(userId).getEmail();
			if (statusResponse.getStatus().equals(InsuranceConstant.APPROVED)) {
				SendEmailUtilis.policyApprovedMail(userMail, statusResponse.getMessage());
			} else {
				SendEmailUtilis.policyDeclineMail(userMail, statusResponse.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error inside sendPolicyStatusChangeMail in UnderwriterService");
			throw new ExceptionUtils("Exception in sendPolicyStatusChangeMail in service: " + e);
		}
	}

}
