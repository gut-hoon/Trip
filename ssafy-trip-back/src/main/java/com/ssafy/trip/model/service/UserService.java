package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.UserDao;
import com.ssafy.trip.model.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserDao uDao;
	
	public User selectOne(String email, String password) {
		return uDao.selectOne(email, password);
	}
	
	public List<User> selectAll(){
		return uDao.selectAll();
	}
	
	public User selectByEmail(String email) {
		return uDao.selectByEmail(email);
	}
	
	public void insert(User user) {
		uDao.insert(user);
	}
	
	
	public int update(User user) {
		System.out.println("유저 서비스" +user.toString());
		return uDao.update(user);
	}
	
	public void delete(String id) {
		uDao.delete(id);
	}
	
	public void updateRefresh(User user) {
		uDao.updateRefresh(user.getEmail(), user.getRefresh());
	}
}
