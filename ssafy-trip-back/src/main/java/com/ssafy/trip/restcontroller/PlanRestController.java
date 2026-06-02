package com.ssafy.trip.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.PlaceCount;
import com.ssafy.trip.model.dto.Plan;
import com.ssafy.trip.model.dto.PlanDetail;
import com.ssafy.trip.model.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plan")
@RequiredArgsConstructor
@Tag(name="PlanRestController", description = "여행 계획 페이지 기능 제공")
public class PlanRestController implements RestControllerHelper {
	private final PlanService pService;
	private int planId = 0;
	@Operation(summary = "여행계획 등록", description = "새로운 계획을 등록한다.")
	@ApiResponses({@ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
		@ApiResponse(responseCode = "500", description = "계획 등록 실패")})
	@PostMapping("/insert")
	public ResponseEntity<?> registPlan(@RequestBody Plan plan) {
	    try {
	        // 1. Plan 저장
	        planId = pService.registPlan(plan);
	        
	        return handleSuccess(planId, HttpStatus.CREATED);
	    } catch(Exception e) {
	        return handleFail(e);
	    }
	}
	
	@PostMapping("/details")
	public ResponseEntity<?> registPlanDetails(@RequestBody List<PlanDetail> planDetails) {
	    try {
	    	for(PlanDetail p : planDetails) {
	    		p.setPlanId(planId);
	    	}
	        int result = pService.registplanDetail(planDetails);
	        return handleSuccess(result, HttpStatus.CREATED);
	    } catch(Exception e) {
	        return handleFail(e);
	    }
	}
	
	@Operation(summary = "여행계획 수정", description = "계획을 수정.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "게시글 수정 성공"), 
		@ApiResponse(responseCode = "500", description = "계획 수정 실패")})
	@PutMapping("/update")
	private ResponseEntity<?> updatePlan(@RequestBody Plan plan){
		try {
			pService.updatePlan(plan);
			return handleSuccess(plan, HttpStatus.OK); // 수정은 OK 상태코드
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@PutMapping("/details/update/{planId}")
	public ResponseEntity<?> updatePlanDetails(@PathVariable int planId, @RequestBody List<PlanDetail> details) {
	    try {
	    	pService.updateDetailsByPlanId(planId, details);
	        return handleSuccess("수정 완료", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return handleFail(e);
	    }
	}
	
	@Operation(summary = "여행계획 삭제", description = "계획을 삭제한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "계획 삭제 성공"), 
		@ApiResponse(responseCode = "500", description = "계획 삭제 실패")})
	@DeleteMapping("/delete/{no}")
	private ResponseEntity<?> deletePlan(@PathVariable int no){
		try {
			pService.removePlan(no);
			return handleSuccess(no, HttpStatus.OK); // 삭제는 OK 상태코드
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "여행계획 조회", description = "특정 계획을 불러온다")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "계획 조회 성공"), 
		@ApiResponse(responseCode = "500", description = "계획 조회 실패")})
	@GetMapping("/{no}")
	private ResponseEntity<?> selectOne(@PathVariable int no){
		try {
			Plan plan = pService.planDetail(no); // 반환값 받기
			return handleSuccess(plan, HttpStatus.OK); // 조회는 OK 상태코드
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "계획 전체 조회", description = "모든 계획을 불러온다")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "계획 전체조회 성공"), 
		@ApiResponse(responseCode = "500", description = "계획 전체조회 실패")})
	@GetMapping
	private ResponseEntity<?> selectAll(){
		try {
			List<Plan> plans = pService.planList(); // 반환값 받기
			return handleSuccess(plans, HttpStatus.OK); // 조회는 OK 상태코드
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@GetMapping("/details/{planId}")
    public ResponseEntity<?> getDetails(@PathVariable int planId) {
        try {
            List<PlanDetail> details = pService.getDetailsByPlanId(planId);
            return handleSuccess(details, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return handleFail(e);
        }
    }
	
	@GetMapping("/place-count")
    public ResponseEntity<?> getPlaceCount() {
        try {
            List<PlaceCount> placeCount = pService.getPlaceCount();
            return handleSuccess(placeCount, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return handleFail(e);
        }
    }
	
	@GetMapping("/myplan/{userNo}")
    public ResponseEntity<?> getMyPlan(@PathVariable int userNo) {
        try {
            List<Plan> myPlan = pService.getMyPlan(userNo);
            return handleSuccess(myPlan, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return handleFail(e);
        }
    }

}
