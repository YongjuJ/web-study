package com.example.backend.dto.response;

import com.example.backend.entity.User;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

/**
 * 사용자 응답 DTO
 * 비밀번호 등 민감한 정보는 제외하고 반환해요.
 */
@Data
@Builder
public class UserResponse {
    private Long id;
    private String loginId;
    private String username;
    private LocalDate birth;
    private String email;
    private String userRole;
    private String loginType;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .username(user.getUsername())
                .birth(user.getBirth())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .loginType(user.getLoginType())
                .build();
    }
}
