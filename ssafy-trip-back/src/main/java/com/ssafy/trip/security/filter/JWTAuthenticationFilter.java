package com.ssafy.trip.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.model.dto.LoginRequest;
import com.ssafy.trip.model.dto.User;
import com.ssafy.trip.model.service.UserService;
import com.ssafy.trip.restcontroller.RestControllerHelper;
import com.ssafy.trip.security.dto.CustomUserDetails;
import com.ssafy.trip.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements RestControllerHelper {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    // 조상의 생성자를 호출하기 위해 @RequeredArgsConstructor를 사용하지 않고 @Autowired를 사용
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/auth/login");
        
        this.setUsernameParameter("email");
        this.setPasswordParameter("password");
        System.out.println(this);
    }

    // 로그인 성공 시 실행하는 메소드 (JWT 발급)
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) {
        System.out.println(">>>>> successfulAuthentication 진입");

        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        User user = details.getUser();

        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);
        user.setRefresh(refreshToken);
        try {
            userService.updateRefresh(user);
            System.out.println("유저는 " + user.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("userService.update 에러: " + e.getMessage());
        }

        Map<String, String> result = Map.of("status", "SUCCESS", "accessToken", accessToken, "refreshToken", refreshToken);

        // 응답 처리 try-catch로 감싸기
        try {
            handleResult(response, result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("응답 중 에러: " + e.getMessage());
        }
    }


    // 로그인 실패 시 실행하는 메소드
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {
        // 예외 처리는 한 곳에서 처리하도록 전달
        throw failed;
    }

    // 결과 전송 helper 메소드
    public void handleResult(HttpServletResponse response, Object result, HttpStatus status) {
        try {
            response.setStatus(status.value());
            response.setContentType("application/json;charset=UTF-8");

            String json = new ObjectMapper().writeValueAsString(result);
            System.out.println("응답 JSON: " + json);

            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("handleResult 에러: " + e.getMessage());
        }
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // JSON 요청 본문에서 email/password 추출
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest credentials = objectMapper.readValue(request.getInputStream(), LoginRequest.class);



            log.debug("로그 Attempting login with email: {}, password: {}", credentials.getEmail(), credentials.getPassword());
            System.out.println(credentials.toString());
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new RuntimeException("Request input parsing failed", e);
        }
    }


}
