package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 공통코드 그룹 엔티티
 * 예) USER_ROLE, LOGIN_TYPE 등 코드 그룹을 관리해요.
 */
@Entity
@Table(name = "common_code_group")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class CommonCodeGroup {

    @Id
    @Column(name = "code_group", length = 50)
    private String codeGroup;           // 코드 그룹명 (예: USER_ROLE)

    @Column(nullable = false, length = 100)
    private String codeGroupName;       // 코드 그룹 설명 (예: 사용자 권한)

    @Column(length = 200)
    private String description;         // 상세 설명

    @Column(nullable = false)
    private boolean useYn = true;       // 사용 여부

    @Column(nullable = false)
    private int sortOrder = 0;          // 정렬 순서

    // 코드 그룹에 속한 코드 목록 (양방향 관계)
    @OneToMany(mappedBy = "codeGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CommonCode> codes = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
