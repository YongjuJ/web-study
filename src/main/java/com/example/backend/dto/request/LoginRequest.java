package com.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 로그인 요청 DTO
 */
@Data
public class LoginRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String loginId;             // 로그인 아이디

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;            // 비밀번호
}
