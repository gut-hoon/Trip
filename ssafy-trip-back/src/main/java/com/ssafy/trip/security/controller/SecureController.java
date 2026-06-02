package com.ssafy.trip.security.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@ResponseBody
@RequestMapping("/secured")
@RequiredArgsConstructor
public class SecureController {
	
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserDetails details) {
        return "secured/admin/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "secured/user/user";
    }
}
