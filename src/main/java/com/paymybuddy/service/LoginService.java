package com.paymybuddy.service;

import org.springframework.security.core.Authentication;

public interface LoginService {
    String generateToken(Authentication authentication);
}
