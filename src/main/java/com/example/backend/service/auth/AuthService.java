package com.example.backend.service.auth;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.LoginResponse;
import com.example.backend.dto.response.UserResponse;

/**
 * 인증 서비스 인터페이스
 */
public interface AuthService {
    UserResponse register(RegisterRequest request);   // 회원가입
    LoginResponse login(LoginRequest request);        // 로그인
}
