package com.ssafy.trip.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.trip.model.service.UserService;
import com.ssafy.trip.security.filter.JWTAuthenticationFilter;
import com.ssafy.trip.security.filter.JWTVerificationFilter;
import com.ssafy.trip.security.filter.SecurityExceptionHandlingFilter;
import com.ssafy.trip.security.service.CustomUserDetailService;
import com.ssafy.trip.security.util.JWTUtil;

@Configuration
public class APISecurityConfig {

	
    @Bean
    PasswordEncoder passEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
    
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http,
	        @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig,
	        CustomUserDetailService userDetailsService, AuthenticationManager authManager,
	        UserService userService, JWTUtil jwtUtil,
	        JWTVerificationFilter jwtVerifyFilter, SecurityExceptionHandlingFilter exceptionFilter) throws Exception {

	    JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter(authManager, userService, jwtUtil);
	    authFilter.setFilterProcessesUrl("/api/auth/login");

	    http.securityMatcher("/api/**")
	        .cors(t -> t.configurationSource(corsConfig))
	        .userDetailsService(userDetailsService)
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	    http.authorizeHttpRequests(auth -> auth
	        .requestMatchers("/api/v1/**", "/api/auth/**", "/v3/api-docs/", "/swagger-ui/", "/swagger-resources/", "/webjars/")
	        .permitAll()
	        .anyRequest().authenticated());

	    http.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)
	        .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
	        .addFilterBefore(exceptionFilter, JWTVerificationFilter.class);

	    return http.build();
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// CORS 허용 출처
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

		// 허용할 HTTP 메서드
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

		// 허용할 헤더
		configuration.setAllowedHeaders(Arrays.asList("*"));

		// 자격 증명 허용 (Authorization, Cookie 등)
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration); // API 경로에만 적용

		return source;
	}
}
