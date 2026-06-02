package com.ssafy.trip.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.UserDao;
import com.ssafy.trip.model.dto.User;
import com.ssafy.trip.model.service.UserService;
import com.ssafy.trip.security.dto.CustomUserDetails;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
	
	private final @NonNull UserDao uDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = uDao.selectByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}else {
			return new CustomUserDetails(user);
		}
	}
}
