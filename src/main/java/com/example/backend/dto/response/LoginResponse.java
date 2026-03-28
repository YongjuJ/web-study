package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 로그인 응답 DTO
 * 로그인 성공 시 AccessToken과 사용자 정보를 반환해요.
 */
@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;         // JWT AccessToken
    private UserResponse user;          // 사용자 기본 정보
}
