package com.impetus.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.impetus.entity.User;
import com.impetus.utility.InsuranceUtils;

@Service
public class CustomeUserDetails implements UserDetails, Serializable {

	private static final long serialVersionUID = 5092817570866429177L;

	private static final Logger logger = LoggerFactory.getLogger(CustomeUserDetails.class);

	private transient User user;
	
	public CustomeUserDetails() {
	}

	public CustomeUserDetails(User user) {
		logger.info("Inside CustomerUserDetails constructor in CustomeUserDetails");
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		logger.info("Inside GraantedAuthority method in CustomeUserDetails");
		Long roleId = user.getRoleId();
		logger.debug("User RoleId : {} ", roleId);
		logger.debug("User UserFirstName : {} ", user.getFirstName());

		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+InsuranceUtils.getRoleName(roleId));
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(grantedAuthority);
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isStatus();
	}

}
