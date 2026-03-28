package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 공통코드 엔티티
 * 예) USER_ROLE 그룹 안에 ADMIN, USER, MANAGER 코드를 관리해요.
 */
@Entity
@Table(name = "common_code")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class CommonCode {

    @EmbeddedId
    private CommonCodeId id;            // 복합키 (code_group + code)

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codeGroup")
    @JoinColumn(name = "code_group")
    private CommonCodeGroup codeGroup;  // 코드 그룹 참조

    @Column(nullable = false, length = 100)
    private String codeName;            // 코드명 (예: 관리자)

    @Column(length = 200)
    private String description;         // 상세 설명

    @Column(nullable = false)
    private boolean useYn = true;       // 사용 여부

    @Column(nullable = false)
    private int sortOrder = 0;          // 정렬 순서

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 복합키 클래스
     * code_group + code 조합으로 unique하게 관리해요.
     */
    @Embeddable
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class CommonCodeId implements java.io.Serializable {
        @Column(name = "code_group", length = 50)
        private String codeGroup;       // 코드 그룹 (예: USER_ROLE)

        @Column(name = "code", length = 50)
        private String code;            // 코드 (예: ADMIN)
    }
}
