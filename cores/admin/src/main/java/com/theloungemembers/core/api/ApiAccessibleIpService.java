package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

/**
 * API 접속 허용 IP 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class ApiAccessibleIpService extends AbstractBaseService<ApiAccessibleIpCommand, ApiAccessibleIpQuery, ApiAccessibleIpResult, Long> {

    private static final Pattern IPV4_PATTERN = Pattern.compile("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$");

    private final ApiAccessibleIpRepository apiAccessibleIpRepository;


    @Transactional(readOnly = true)
    public void validate(ApiAccessibleIpCommand command) {
        if (!StringUtils.hasText(command.getIpAddress()) || !IPV4_PATTERN.matcher(command.getIpAddress()).matches()) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST, "IP 주소 형식이 올바르지 않습니다.");
        }

        final Long uid = apiAccessibleIpRepository.selectUidByIpAddress(command.getIpAddress());
        if (uid == null) {
            return;
        }
        if (!uid.equals(command.getUid())) {
            throw new BusinessException(CommonErrorCode.BAD_REQUEST, "IP 주소가 이미 존재합니다.");
        }
    }

}
