package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API 접속 허용 IP 관리 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiAccessibleIpService extends AbstractBaseService<ApiAccessibleIpCommand, ApiAccessibleIpQuery, ApiAccessibleIpResult, Integer> {

    private final ApiAccessibleIpRepository apiAccessibleIpRepository;


    @Transactional(readOnly = true)
    public void validate(ApiAccessibleIpCommand command) {
        final List<Integer> uids = apiAccessibleIpRepository.selectUidsByIpAddress(command.getIpAddress());
        if (CollectionUtils.isEmpty(uids)) {
            return;
        }
        if (command.getIpAddress() == null || !uids.contains(command.getUid())) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST, "IP 주소가 이미 존재합니다.");
        }
    }

}
