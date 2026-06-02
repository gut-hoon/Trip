package com.ssafy.trip.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.RestApiPjApplication;
import com.ssafy.trip.model.dto.LoginRequest;
import com.ssafy.trip.model.dto.User;
import com.ssafy.trip.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController implements RestControllerHelper {

	private final UserService uService;
    
	@Operation(summary = "회원 등록", description = "새로운 회원 정보를 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원 등록 성공"),
        @ApiResponse(responseCode = "500", description = "서버 에러")
    })
	
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
        	log.debug("registering user : {}", user.getEmail());
        	uService.insert(user);
            result.put("status", "SUCCESS");
            result.put("user", user);
            return ResponseEntity.ok(result);
        } catch (DataAccessException e) {
            log.error("User registration failed", e);
            result.put("status", "FAIL");
            result.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }
	
	@Operation(summary = "로그인", description = "ID와 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest user, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            User loginUser = uService.selectOne(user.getEmail(), user.getPassword());
            if (loginUser != null) {
            	session.setAttribute("loginUser", user);
                result.put("status", "SUCCESS");
                result.put("user", loginUser);
                return ResponseEntity.ok(result);
            } else {
                result.put("status", "FAIL");
                result.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(result);
            }
        } catch (DataAccessException e) {
            log.error("Login failed", e);
            result.put("status", "FAIL");
            result.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃 처리를 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
    	session.invalidate();
        Map<String, Object> result = Map.of("status", "SUCCESS", "message", "Logged out");
        return ResponseEntity.ok(result);
    }
    
    @Operation(summary = "회원 삭제", description = "회원을 삭제합니다.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        // 실제 세션 제거 로직은 없음
    	Map<String, Object> result = new HashMap<>();
        try {
            uService.delete(id);
            result.put("status", "SUCCESS");
            result.put("message", "User deleted");
            return ResponseEntity.ok(result);
        } catch (DataAccessException e) {
            log.error("Delete failed", e);
            result.put("status", "FAIL");
            result.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }
    
    @Operation(summary = "회원 정보 조회", description = "ID로 회원 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원 조회 성공"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @GetMapping("/{email}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String email) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = uService.selectByEmail(email);
            if (user != null) {
                result.put("status", "SUCCESS");
                result.put("user", user);
                return ResponseEntity.ok(result);
            } else {
                result.put("status", "FAIL");
                result.put("message", "User not found");
                return ResponseEntity.status(404).body(result);
            }
        } catch (DataAccessException e) {
            log.error("User retrieval failed", e);
            result.put("status", "FAIL");
            result.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @Operation(summary = "회원 정보 수정", description = "ID에 해당하는 회원의 개인정보를 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "회원 수정 성공"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @PutMapping("/update/{email}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable String email,
            @RequestBody User updatedUser) {

        Map<String, Object> result = new HashMap<>();
        try {
            User existingUser = uService.selectByEmail(email);
            if (existingUser != null) {
                updatedUser.setEmail(email); // ID 변경 불가
                uService.update(updatedUser);
                result.put("status", "SUCCESS");
                result.put("user", updatedUser);
                return ResponseEntity.ok(result);
            } else {
                result.put("status", "FAIL");
                result.put("message", "User not found");
                return ResponseEntity.status(404).body(result);
            }
        } catch (DataAccessException e) {
            log.error("User update failed", e);
            result.put("status", "FAIL");
            result.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }
}
