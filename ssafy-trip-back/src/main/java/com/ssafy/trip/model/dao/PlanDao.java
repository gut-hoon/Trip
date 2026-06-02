package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.model.dto.PlaceCount;
import com.ssafy.trip.model.dto.Plan;
import com.ssafy.trip.model.dto.PlanDetail;

@Mapper
public interface PlanDao {
	public int insert(Plan plan);
	
	int insertDetail(List<PlanDetail> planDetailList);

	public int delete(int no);
	
	public int update(Plan plan);
	
	public Plan selectOne(int no);
	
	public List<Plan> selectAll();
	
	public List<PlanDetail> selectDetailsByPlanId(int planId);
	
	public void deleteDetailsByPlanId(int planId);
	
	public List<PlaceCount> selectchart();
	
	public List<Plan> selectMyPlan(int userNo);
}
