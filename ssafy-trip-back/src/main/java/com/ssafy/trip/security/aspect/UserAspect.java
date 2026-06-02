package com.ssafy.trip.security.aspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ssafy.trip.model.dto.User;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserAspect {
    @Autowired
    PasswordEncoder encoder;

    @Before("execution( * com.ssafy..dao.*.insert(com.ssafy.trip.model.dto.User)) && args(user)")
    public void encodeMemberPassword(User user) {
    	user.setPassword(encoder.encode(user.getPassword()));
    }
    
    @Before("execution( * com.ssafy..dao.*.update(com.ssafy.trip.model.dto.User)) && args(user)")
    public void encodeUpdatePassword(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        System.out.println(user.getPassword());
    }
}
