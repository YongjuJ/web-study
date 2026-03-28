package com.example.backend.service.auth.impl;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.LoginResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.jwt.JwtTokenProvider;
import com.example.backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증 서비스 구현체
 * 회원가입, 로그인 비즈니스 로직을 담당해요.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     * 1. 아이디/이메일 중복 체크
     * 2. 비밀번호 암호화
     * 3. 사용자 저장
     */
    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        // 아이디 중복 체크
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .loginId(request.getLoginId())
                .username(request.getUsername())
                .birth(request.getBirth())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userRole("USER")       // 기본 권한
                .loginType("LOCAL")     // 일반 로그인
                .emailVerified(false)
                .build();

        return UserResponse.from(userRepository.save(user));
    }

    /**
     * 로그인
     * 1. 아이디로 사용자 조회
     * 2. 비밀번호 검증
     * 3. JWT AccessToken 발급
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        // 아이디로 사용자 조회
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // JWT AccessToken 발급
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getLoginId(), user.getUserRole());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .user(UserResponse.from(user))
                .build();
    }
}
