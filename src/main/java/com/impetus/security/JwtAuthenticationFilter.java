package com.impetus.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter class
 * 
 * @author arpit.raghuwanshi
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JWTUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Inside doFilterInternal method in JwtAuthenticationFilter");
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(6);

			try {
				username = this.jwtUtils.extractUsername(jwtToken);
			} catch (Exception e) {
				logger.error("Exception while extractUsername in JwtAuthenticationFilter : {}", e);
			}

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, "", userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} else {
				logger.error("Token is not validated...");
			}
		}
		logger.info("Before doFilter method in JwtAuthenticationFilter");
		filterChain.doFilter(request, response);
	}
}
