package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 사용자 엔티티
 * - loginId: 로그인 아이디 (unique)
 * - username: 사용자명
 * - birth: 생년월일
 * - email: 비밀번호 찾기용
 * - userRole: 공통코드 USER_ROLE 참조
 * - loginType: 공통코드 LOGIN_TYPE 참조
 */
@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;             // 로그인 아이디

    @Column(nullable = false)
    private String username;            // 사용자명

    @Column(nullable = false)
    private LocalDate birth;            // 생년월일

    @Column
    private String password;            // 소셜 로그인은 비밀번호 없음

    @Column(unique = true)
    private String email;               // 비밀번호 찾기용

    @Column(name = "user_role", nullable = false)
    private String userRole = "USER";   // 공통코드 USER_ROLE 참조

    @Column(name = "login_type", nullable = false)
    private String loginType = "LOCAL"; // 공통코드 LOGIN_TYPE 참조

    @Column
    private String providerId;          // 소셜 로그인 고유 ID

    @Column(nullable = false)
    private boolean emailVerified = false;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
