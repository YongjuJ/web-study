package com.example.backend.service.auth.impl;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring Security UserDetailsService 구현체
 * loginId로 DB에서 사용자를 조회하고 UserDetails 형태로 반환해요.
 * JwtAuthenticationFilter에서 토큰 검증 시 호출돼요.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * loginId로 사용자 조회
     * Spring Security는 username 파라미터를 사용하지만
     * 우리 프로젝트에서는 loginId를 username으로 사용해요.
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPassword() != null ? user.getPassword() : "")
                // ROLE_USER, ROLE_ADMIN 형태로 권한 설정
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole())))
                .build();
    }
}
