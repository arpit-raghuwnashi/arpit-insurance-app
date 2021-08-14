package com.impetus.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author arpit.raghuwanshi Spring Security Configuration Class
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityJwtConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityJwtConfig.class);

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("Inside configure AuthenticationManagerBuilder method");
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Inside configire http security method");

		http.csrf().disable().cors().and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")

				.permitAll().antMatchers(HttpMethod.GET, "/sendOtp/{email}", "/verifyOtp/{confirmOtp}").permitAll()
				.antMatchers("/token", "/userRegistration", "/resetPasswrod", "/verifyClaimDetails",
						"/getProductByPolicyId", "/addClaim", "/checkNominee", "/checkUserById", "/checkOrder",
						"/contactUs")
				.permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(entryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder);
		return authProvider;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
