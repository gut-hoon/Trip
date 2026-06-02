package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.PlanDao;
import com.ssafy.trip.model.dto.PlaceCount;
import com.ssafy.trip.model.dto.Plan;
import com.ssafy.trip.model.dto.PlanDetail;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PlanService {
	private final PlanDao pDao;
	
	public int registPlan(Plan plan) {
		pDao.insert(plan);
		int planid = plan.getNo();
		return planid;
	}
	
	public int registplanDetail(List<PlanDetail> detail) throws Exception {
		try {
			return pDao.insertDetail(detail);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public int removePlan(int no) {
		pDao.deleteDetailsByPlanId(no);
		return pDao.delete(no);
	}
	
	public int updatePlan(Plan plan) {
		return pDao.update(plan);
	}
	
	public Plan planDetail(int no) {
		return pDao.selectOne(no);
	}
	
	public List<Plan> planList(){
		return pDao.selectAll();
	}
	
	public List<PlanDetail> getDetailsByPlanId(int planId) {
        return pDao.selectDetailsByPlanId(planId);
    }
	
	public void updateDetailsByPlanId(int planId, List<PlanDetail> details) throws Exception {
		pDao.deleteDetailsByPlanId(planId);
		pDao.insertDetail(details);
	}
	
	public List<PlaceCount> getPlaceCount(){
		return pDao.selectchart();
	}
	
	public List<Plan> getMyPlan(int userNo){
		return pDao.selectMyPlan(userNo);
	}
}
