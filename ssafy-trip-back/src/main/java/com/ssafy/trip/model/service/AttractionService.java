package com.ssafy.trip.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.AttractionDao;
import com.ssafy.trip.model.dto.Attraction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionDao aDao;
	
	public List<Attraction> selectBySido(int sidoCode){
		return aDao.selectBySido(sidoCode);
	}
	
	public List<Attraction> selectByGugun(int gugunCode){
		return aDao.selectByGugun(gugunCode);
	}
	
	public List<Attraction> selectByContent(int contentType){
		return aDao.selectByContent(contentType);
	}
	
	public List<Attraction> selectBySidoAndGugun(Map<String, Integer> codes){
		return aDao.selectBySidoAndGugun(codes);
	}
}
