package com.theloungemembers.core.admin;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theloungemembers.core.admin.AdminMenuBookmarkCommand.AdminMenuBookmarkCommandBuilder;
import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.type.ServiceStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuBookmarkService
        extends AbstractBaseService<AdminMenuBookmarkCommand, AdminMenuBookmarkQuery, AdminMenuBookmarkResult, Long> {

    private final AdminMenuBookmarkRepository adminMenuBookmarkRepository;

    @Transactional
    public boolean toggleBookmark(String workerId, String menuCode) {
        // 기존 관리자 메뉴 즐겨찾기 이력 조회
        Optional<AdminMenuBookmarkResult> opt = adminMenuBookmarkRepository.findByWorkerIdAndMenuCode(workerId,
                menuCode);

        AdminMenuBookmarkCommandBuilder builder = AdminMenuBookmarkCommand.builder()
                .workerId(workerId)
                .menuCode(menuCode);

        if (opt.isPresent()) {
            // 기존 이력이 있는 경우: on_service 상태 변경 (1 -> 0, 0 -> 1)
            AdminMenuBookmarkResult bookmark = opt.get();

            boolean newStatus = !ServiceStatus.IN_SERVICE.equals(bookmark.getOnService());

            AdminMenuBookmarkCommand command = builder
                    .onService(newStatus ? ServiceStatus.IN_SERVICE : ServiceStatus.STOPPED)
                    .build();

            super.update(bookmark.getUid(), command);

            return newStatus;
        } else {
            // 최초 등록인 경우
            AdminMenuBookmarkCommand command = builder
                    .onService(ServiceStatus.IN_SERVICE)
                    .build();

            adminMenuBookmarkRepository.save(command);

            return true;
        }
    }
}