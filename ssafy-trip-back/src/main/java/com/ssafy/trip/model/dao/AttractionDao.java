package com.ssafy.trip.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Attraction;

@Mapper
public interface AttractionDao {
	
	public List<Attraction> selectBySido(int sidoCode);
	
	public List<Attraction> selectByContent(int contentType);
	
	public List<Attraction> selectByGugun(int gugunCode);
	
	public List<Attraction> selectBySidoAndGugun(Map<String, Integer> code);
}
