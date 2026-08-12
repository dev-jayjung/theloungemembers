package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * API 메뉴 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class ApiMenuService extends AbstractBaseService<ApiMenuCommand, ApiMenuQuery, ApiMenuResult, Long> {

    private final ApiMenuRepository apiMenuRepository;
    private final ApiMenuGroupRepository apiMenuGroupRepository;

    /**
     * API 메뉴 목록 조회
     *
     * @param query
     * @return
     */
    @Transactional(readOnly = true)
    public PageResponse<ApiMenuSearchResult> getSearchPage(ApiMenuQuery query) {
        final int count = apiMenuRepository.selectSearchCount(query);
        final List<ApiMenuSearchResult> list = apiMenuRepository.selectSearchList(query);
        return PageUtil.getPage(query, () -> list, () -> count);
    }

    @Transactional(readOnly = true)
    public void validate(ApiMenuCommand command) {
        verifyExistGroupCode(command.getGroupCode());
        verifyUseableCode(command.getCode(), command.getUid());
    }

    private void verifyUseableCode(String code, Long paramUid) {
        final Long menuUid = apiMenuRepository.selectUidByCode(code);
        // 동일 코드가 없는 경우 사용 가능
        if (menuUid == null) {
            return;
        }
        // 동일 코드가 있는경우, API 메뉴 키 동일 여부 확인
        if (!menuUid.equals(paramUid)) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST, "API 코드가 이미 존재합니다.");
        }
    }

    private void verifyExistGroupCode(String groupCode) {
        AssertUtil.notNull(groupCode, "API 그룹을 선택 해주세요.");
        final boolean exists = apiMenuGroupRepository.existsCode(groupCode);
        if (!exists) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST, "API 그룹 정보가 없습니다.");
        }
    }

}
