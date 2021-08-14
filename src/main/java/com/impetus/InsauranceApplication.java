package com.impetus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.impetus.dao.CustomerPolicyDetailsDao;
import com.impetus.entity.CustomerPolicyDetails;
import com.impetus.utility.ExceptionUtils;
import com.impetus.utility.InsuranceConstant;
import com.impetus.utility.InsuranceUtils;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class InsauranceApplication {

	private static final Logger logger = LoggerFactory.getLogger(InsauranceApplication.class);

	@Autowired
	private CustomerPolicyDetailsDao customerPolicyDetailsDao;

	public static void main(String[] args) {
		SpringApplication.run(InsauranceApplication.class, args);
	}

	/**
	 * Implementing scheduler for setting status DECLINE, if request time is more
	 * then 4 Hours
	 */
	@Scheduled(fixedRateString = "${scheduler.time.minutes}")
	public void schedulingEveryRequest() {
		logger.info("Inside scheduler method");
		List<CustomerPolicyDetails> policyOrderDetails = null;
		try {
			policyOrderDetails = this.customerPolicyDetailsDao
					.getPendingCustomerPolicyDetails(InsuranceConstant.PENDING);
			policyOrderDetails.forEach(pod -> {
				LocalDateTime orderTime = InsuranceUtils.getLocalDateTime(pod.getOrderDate());
				LocalDateTime currentTime = LocalDateTime.now();
				if (Duration.between(orderTime, currentTime).toHours() >= InsuranceConstant.HOURS_4) {
					pod.setStatus(InsuranceConstant.DECLINE);
					pod.setModifiedDate(new Date());
					customerPolicyDetailsDao.save(pod);
				}
			});
			logger.info("Pending ordered policy data retrive successfully ");
		} catch (Exception e) {
			logger.error("Error in getPendingCustomerPolicyDetails in service ");
			throw new ExceptionUtils("Excption in schedulingEveryRequest method");
		}
	}

	@Bean
	public PasswordEncoder encoder() {
		logger.debug("Inside BCryptPasswordEncoder method");
		return new BCryptPasswordEncoder();
	}

}
