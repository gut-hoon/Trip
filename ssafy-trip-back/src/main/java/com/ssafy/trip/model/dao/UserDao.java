package com.ssafy.trip.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.User;

@Mapper
public interface UserDao {
	public User selectOne(String email, String password);
	
	public List<User> selectAll();
	
	public User selectByEmail(String email);
	
	public void insert(User user);
	
	public int update(User user);
	
	public void delete(String id);
	
	public void updateRefresh(String email, String refresh);
}
