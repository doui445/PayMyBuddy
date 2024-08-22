package com.paymybuddy.controller;

import com.paymybuddy.service.LoginServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoginController {
    private LoginServiceImpl loginService;

    @PostMapping("/login")
    public String getToken(Authentication authentication) {
        return loginService.generateToken(authentication);
    }
}
