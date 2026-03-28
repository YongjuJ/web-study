package com.example.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * 회원가입 요청 DTO
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, 언더스코어만 사용 가능합니다.")
    private String loginId;             // 로그인 아이디

    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 2, max = 20, message = "사용자명은 2~20자 사이여야 합니다.")
    private String username;            // 사용자명

    @NotNull(message = "생년월일은 필수입니다.")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birth;            // 생년월일

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;            // 비밀번호

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;               // 비밀번호 찾기용 이메일
}
