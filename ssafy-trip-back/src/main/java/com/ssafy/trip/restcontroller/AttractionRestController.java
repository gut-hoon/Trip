package com.ssafy.trip.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/attraction")
@RequiredArgsConstructor
@Tag(name = "AttractionRestController", description = "지도에 관한 기능 제공")
public class AttractionRestController implements RestControllerHelper{
	
	private final AttractionService aService;
	
	@Operation(summary = "컨텐츠 별 조회", description = "컨텐츠 타입으로 검색한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "컨텐츠 별 조회 성공"),
		@ApiResponse(responseCode = "500", description = "컨텐츠 별 조회 실패")})
	@GetMapping("/search-content/{contentType}")
	public ResponseEntity<?> selectByContent(@PathVariable int contentType){
		try {
			List<Attraction> res = aService.selectByContent(contentType);
			return handleSuccess(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "시도 별 조회", description = "시도 별 관광지 정보를 검색한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "시도 별 조회 성공"),
		@ApiResponse(responseCode = "500", description = "시도 별 조회 실패")})
	@GetMapping("/search-sido/{sidoCode}")
	public ResponseEntity<?> selectBySido(@PathVariable int sidoCode){
		try {
			List<Attraction> res = aService.selectBySido(sidoCode);
			return handleSuccess(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "구군 별 조회", description = "구군 별 관광지 정보를 검색한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "구군 별 조회 성공"),
		@ApiResponse(responseCode = "500", description = "구군 별 조회 실패")})
	@GetMapping("/search-gugun/{gugunCode}")
	public ResponseEntity<?> selectByGugun(@PathVariable int gugunCode){
		try {
			List<Attraction> res = aService.selectByGugun(gugunCode);
			return handleSuccess(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
	
	@Operation(summary = "시도, 컨텐츠 별 조회", description = "시도와 컨텐츠 별 관광지 정보를 검색한다.")
	@ApiResponses({@ApiResponse(responseCode = "200", description = "시도, 컨텐츠 별 조회 성공"),
		@ApiResponse(responseCode = "500", description = "시도, 컨텐츠 별 조회 실패")})
	@GetMapping("/search-sido-content/{contentType}/{sidoCode}")
	public ResponseEntity<?> selectBySidoAndGugun(@PathVariable int contentType, @PathVariable int sidoCode){
		Map<String, Integer> result = new HashMap<>();
		result.put("contentType", contentType);
		result.put("sidoCode", sidoCode);	
		try {
			List<Attraction> res = aService.selectBySidoAndGugun(result);
			return handleSuccess(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return handleFail(e);
		}
	}
}
