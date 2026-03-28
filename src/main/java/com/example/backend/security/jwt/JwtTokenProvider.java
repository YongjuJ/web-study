package com.example.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성 및 검증 유틸 클래스
 * - AccessToken: 실제 인증에 사용 (만료시간 짧음)
 * - RefreshToken: AccessToken 만료 시 재발급용 (만료시간 김)
 */
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration) {
        // application.yml의 jwt.secret 값을 Base64 인코딩 후 SecretKey로 변환
        this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.getBytes()));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    /**
     * AccessToken 생성
     * @param email 사용자 이메일 (토큰 subject)
     * @param role 사용자 권한 (토큰 claim에 포함)
     */
    public String createAccessToken(String email, String role) {
        return Jwts.builder()
                .subject(email)             // 토큰 주체 (이메일)
                .claim("role", role)        // 추가 정보 (권한)
                .issuedAt(new Date())       // 발급 시간
                .expiration(new Date(System.currentTimeMillis() + accessExpiration)) // 만료 시간
                .signWith(secretKey)        // 서명 (위변조 방지)
                .compact();
    }

    /**
     * RefreshToken 생성
     * AccessToken 만료 시 재발급에 사용 (role 정보 불필요)
     */
    public String createRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 토큰에서 이메일 추출
     * 요청마다 토큰을 파싱해서 사용자 식별에 사용
     */
    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰 유효성 검증
     * 만료, 위변조, 형식 오류 등을 체크
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 토큰 파싱 (내부 메서드)
     * secretKey로 서명 검증 후 Claims 반환
     */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
