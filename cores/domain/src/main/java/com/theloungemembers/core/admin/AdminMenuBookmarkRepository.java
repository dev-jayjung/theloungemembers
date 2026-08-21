package com.theloungemembers.core.admin;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.admin.entity.AdminMenuBookmarkEntity;
import com.theloungemembers.core.admin.mapper.AdminMenuBookmarkMapper;
import com.theloungemembers.core.admin.repository.AdminMenuBookmarkJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;
import com.theloungemembers.core.helper.ModelMapperHelper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminMenuBookmarkRepository extends
        AbstractBaseRepository<AdminMenuBookmarkCommand, AdminMenuBookmarkQuery, AdminMenuBookmarkResult, Long, AdminMenuBookmarkEntity, AdminMenuBookmarkMapper, AdminMenuBookmarkJpaRepository> {

    private final ModelMapperHelper modelMapperHelper;

    public Optional<AdminMenuBookmarkResult> findByWorkerIdAndMenuCode(String workerId, String menuCode) {
        return repository.findByWorkerIdAndMenuCode(workerId, menuCode)
                .map(e -> modelMapperHelper.map(e, AdminMenuBookmarkResult.class));
    }

}