package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.helper.JsonMapperHelper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiUsageLogDpTempService extends AbstractBaseService<ApiUsageLogDpTempCommand, ApiUsageLogDpTempQuery, ApiUsageLogDpTempResult, Long> {

    // 동기화 대상: dragonpass 계정의 실패(F) 건, 1회 최대 2000건 (PHP api_usage_log_list_dp.data.php 이식)
    private static final String SYNC_ACCOUNT_ID = "dragonpass";
    private static final String INVALID_ARGUMENT_REASON = "The argument value is invalid.";
    private static final String RESPONSE_BODY_KEY = "response";
    private static final String ACTION_FAILURE_CODE_KEY = "action_failure_code";
    private static final String ACTION_FAILURE_REASON_KEY = "action_failure_reason";
    private static final String COUPON_NUM_KEY = "coupon_num";
    private static final String LOUNGE_CODE_KEY = "lounge_code";
    private static final String CARD_COUPON_PREFIX = "8574";

    private final ApiUsageLogDpTempRepository apiUsageLogDpTempRepository;
    private final ApiUsageLogRepository apiUsageLogRepository;
    private final JsonMapperHelper jsonMapperHelper;

    @Transactional
    public int sync() {
        // 마지막 로그 키 조회
        final Long latestUid = getLatestUid();
        // 동기화 대상 목록 조회
        final List<ApiUsageLogResult> logList = apiUsageLogRepository.selectDpFailList(latestUid);

        int syncedCount = 0;

        for (ApiUsageLogResult log : logList) {
            final ApiUsageLogDpTempCommand command = makeCommand(log);
            if (command == null) {
                continue;
            }
            save(command);
            syncedCount++;
        }

        return syncedCount;
    }

    private Long getLatestUid() {
        final Long latestUid = apiUsageLogDpTempRepository.selectLatestUid();
        return latestUid == null ? 0L : latestUid;
    }

    private ApiUsageLogDpTempCommand makeCommand(ApiUsageLogResult log) {
        final Map<String, Object> argumentMap = parseJsonToMap(log.getArgument());
        final Map<String, Object> responseMap = parseJsonToMap(log.getResponse());
        final Map<String, Object> responseBody = asMap(responseMap.get(RESPONSE_BODY_KEY));

        final String actionFailureReason = (String) responseBody.get(ACTION_FAILURE_REASON_KEY);
        if (INVALID_ARGUMENT_REASON.equals(actionFailureReason)) {
            return null;
        }

        final Object argumentCouponNum = argumentMap.get(COUPON_NUM_KEY);
        final String couponNum = argumentCouponNum == null ? "" : (String) argumentCouponNum;
        final Object loungeCode = argumentMap.get(LOUNGE_CODE_KEY);
        final Object actionFailureCode = responseBody.get(ACTION_FAILURE_CODE_KEY);

        final ApiUsageLogDpTempCommand command = new ApiUsageLogDpTempCommand();
        command.setUid(log.getUid());
        command.setCouponNum(couponNum);

        if (couponNum.isEmpty()) {
            command.setCouponType("");
        } else if (couponNum.startsWith(CARD_COUPON_PREFIX)) {
            command.setCouponType("card");
        } else {
            command.setCouponType("coupon");
        }

        command.setLoungeCode(loungeCode == null ? "" : (String) loungeCode);
        command.setApiCode(log.getApiCode());
        command.setAccountId(log.getAccountId());
        command.setTransactionId(log.getTransactionId());
        command.setIpAddress(log.getIpAddress());
        command.setLogRegDate(log.getRegDate());
        command.setActionFailureCode(actionFailureCode == null ? "" : (String) actionFailureCode);
        command.setActionFailureReason(actionFailureReason);

        return command;
    }

    private Map<String, Object> parseJsonToMap(String json) {
        final Map<String, Object> map = jsonMapperHelper.readMapValue(json, Object.class);
        return map == null ? Map.of() : map;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object obj) {
        return obj instanceof Map ? (Map<String, Object>) obj : Map.of();
    }
}
