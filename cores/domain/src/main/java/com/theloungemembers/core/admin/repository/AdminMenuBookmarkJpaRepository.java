package com.theloungemembers.core.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.admin.entity.AdminMenuBookmarkEntity;

public interface AdminMenuBookmarkJpaRepository
        extends JpaRepository<AdminMenuBookmarkEntity, Long>, JpaSpecificationExecutor<AdminMenuBookmarkEntity> {

    Optional<AdminMenuBookmarkEntity> findByWorkerIdAndMenuCode(String workerId, String menuCode);
}