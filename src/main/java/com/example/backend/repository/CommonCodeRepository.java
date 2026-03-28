package com.example.backend.repository;

import com.example.backend.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 공통코드 Repository
 */
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCode.CommonCodeId> {
    // 특정 그룹의 사용 가능한 코드 목록 조회
    List<CommonCode> findByIdCodeGroupAndUseYnTrue(String codeGroup);
}
