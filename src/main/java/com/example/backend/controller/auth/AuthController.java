package com.example.backend.controller.auth;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.LoginResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 컨트롤러
 * 회원가입, 로그인 API를 담당해요.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 API
     * POST /api/auth/register
     */
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "아이디, 사용자명, 생년월일, 비밀번호, 이메일로 회원가입해요.")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * 로그인 API
     * POST /api/auth/login
     * 성공 시 JWT AccessToken 반환
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인 후 JWT AccessToken을 반환해요.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
