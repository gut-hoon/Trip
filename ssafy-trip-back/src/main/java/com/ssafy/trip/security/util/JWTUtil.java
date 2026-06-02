package com.ssafy.trip.security.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.trip.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	private final SecretKey key;

	public JWTUtil() {
		key = Jwts.SIG.HS256.key().build();
	}

	@Value("${ssafy.jwt.access-expmin}")
	private long accessExpMin;

	@Value("${ssafy.jwt.refresh-expmin}")
	private long refreshExpMin;

	public String createAccessToken(User user) {
		if (user.getRole() == null) {
			user.setRole("USER");
		}

		System.out.println(user.getPassword());
		System.out.println("username: " + user.getName());
		System.out.println("role: " + user.getRole());

		return create("accessToken", accessExpMin,

				Map.of("email", user.getEmail(), "name", user.getName(), "role", user.getRole(), "no", user.getNo()));
	}

	public String createRefreshToken(User user) {
		return create("refreshToken", refreshExpMin, Map.of("email", user.getEmail()));
	}

	public String create(String subject, long expireMin, Map<String, Object> claims) {

		Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * expireMin);
		String token = Jwts.builder().subject(subject).claims(claims).expiration(expireDate).signWith(key).compact();
		System.out.println(token);
		return token;
	}

	public Claims getClaims(String jwt) {

		var parser = Jwts.parser().verifyWith(key).build();
		var jws = parser.parseSignedClaims(jwt);
		return jws.getPayload();
	}
}