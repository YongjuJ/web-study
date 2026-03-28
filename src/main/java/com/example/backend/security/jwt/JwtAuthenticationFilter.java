package com.example.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 필터
 * 모든 요청마다 한 번씩 실행되며 (OncePerRequestFilter)
 * Authorization 헤더에서 JWT 토큰을 추출하고 검증해요.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 요청 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰이 있고 유효하면 인증 처리
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 3. 토큰에서 이메일 추출
            String email = jwtTokenProvider.getEmail(token);

            // 4. 이메일로 사용자 정보 조회
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 5. 인증 객체 생성 후 SecurityContext에 저장
            // → 이후 Controller에서 @AuthenticationPrincipal로 사용자 정보 꺼낼 수 있어요
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 6. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     * "Bearer {token}" 형식에서 토큰 값만 꺼내요
     */
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
