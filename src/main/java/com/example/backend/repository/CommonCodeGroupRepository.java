package com.example.backend.repository;

import com.example.backend.entity.CommonCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 공통코드 그룹 Repository
 */
public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroup, String> {
    List<CommonCodeGroup> findByUseYnTrue();
}
