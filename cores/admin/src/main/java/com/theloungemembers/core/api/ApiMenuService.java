package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.common.util.PageUtil;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.ErrorCode;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API 메뉴 관리 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMenuService extends AbstractBaseService<ApiMenuCommand, ApiMenuQuery, ApiMenuResult, Integer> {

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
        AssertUtil.notNull(command.getGroupCode(), "API 그룹을 선택 해주세요.");

        final boolean existsGroup = apiMenuGroupRepository.existsCode(command.getGroupCode());
        if (!existsGroup) {
            throw new BusinessException("API 그룹 정보가 없습니다.", ErrorCode.BAD_REQUEST);
        }

        final boolean useableCode = useableCode(command.getCode(), command.getUid());
        if (!useableCode) {
            throw new BusinessException("API 코드가 이미 존재합니다.", ErrorCode.BAD_REQUEST);
        }
    }

    private boolean useableCode(String code, Integer paramUid) {
        final Integer menuUid = apiMenuRepository.selectUidByCode(code);
        // 동일 코드가 없는 경우 사용 가능
        if (menuUid == null) {
            return true;
        }
        // 동일 코드가 있는경우, API 메뉴 키 동일 여부 확인
        if (paramUid == null) {
            return false;
        }
        return menuUid.equals(paramUid);
    }

}