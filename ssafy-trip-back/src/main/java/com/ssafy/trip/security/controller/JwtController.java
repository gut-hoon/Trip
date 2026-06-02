package com.ssafy.trip.security.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.User;
import com.ssafy.trip.model.service.UserService;
import com.ssafy.trip.restcontroller.RestControllerHelper;
import com.ssafy.trip.security.util.JWTUtil;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class JwtController implements RestControllerHelper {
	private final JWTUtil jwtUtil;
	private final UserService userService;
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken){
		if(refreshToken == null || refreshToken.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is requird"));
		}
		
		Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
		String email = (String) claims.get("email");
		
		//예외 처리들은 서비스에서 다시 구현해야 함
        if (email == null) {
            throw new JwtException("Invalid refresh token: email claim missing");
        }
        
		User user = userService.selectByEmail(email);
		
		if(user == null || user.getRefresh()==null || !user.getRefresh().equals(refreshToken)) {
			return handleFail(new JwtException("Invalid refresh token"), HttpStatus.UNAUTHORIZED);
		}
		
		String newAccessToken = jwtUtil.createAccessToken(user);
		
		String newRefreshToken = jwtUtil.createRefreshToken(user);
		user.setRefresh(newRefreshToken);
		userService.updateRefresh(user);
		
		return handleSuccess(Map.of("accessToken" ,newAccessToken, "refreshToken", newRefreshToken));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        return ResponseEntity.badRequest().body(Map.of("error", "Invalid token format"));
	    }
	    
	    System.out.println(authHeader);

	    String token = authHeader.substring(7); // "Bearer " 이후 토큰 추출
	    String email = (String) jwtUtil.getClaims(token).get("email");

	    User user = userService.selectByEmail(email);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid user"));
	    }

	    // Refresh 토큰 제거
	    user.setRefresh(null);
	    
	    userService.updateRefresh(user); // DB에서 Refresh 토큰 삭제

	    return ResponseEntity.ok(Map.of("message", "로그아웃 되었습니다"));
	}

}
