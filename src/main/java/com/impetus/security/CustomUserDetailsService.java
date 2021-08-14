package com.impetus.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.impetus.dao.UserDao;
import com.impetus.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserDao userDao;

	/**
	 * Retrieving user data with username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		logger.info("Inside loadUserByUsername method in CustomUserDetailsService");
		User user = userDao.findByEmail(username);
		if (user == null) {
			logger.error("Error inside loadUserByUsername method in CustomUserDetailsService");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		logger.debug("User name is valid");
		return new CustomeUserDetails(user);
	}

}
