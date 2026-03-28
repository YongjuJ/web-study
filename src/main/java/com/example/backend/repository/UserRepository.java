package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * 사용자 Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);           // 로그인 아이디로 조회
    Optional<User> findByEmail(String email);               // 이메일로 조회
    boolean existsByLoginId(String loginId);                // 로그인 아이디 중복 체크
    boolean existsByEmail(String email);                    // 이메일 중복 체크
    Optional<User> findByLoginTypeAndProviderId(String loginType, String providerId); // 소셜 로그인 조회
}
