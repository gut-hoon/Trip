package com.ssafy.trip.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.trip.security.service.CustomUserDetailService;
import com.ssafy.trip.security.util.JWTUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTVerificationFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;
	private final CustomUserDetailService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractToken(request);
		
		if(token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Claims claims = jwtUtil.getClaims(token);
		UserDetails details = userDetailsService.loadUserByUsername(claims.get("email").toString());
		System.out.println(details.toString());
		var authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
		
	}
	
	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		
		return null;
	}
}
