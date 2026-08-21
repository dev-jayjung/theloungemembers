package com.theloungemembers.core.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theloungemembers.core.annotation.ProcessS3File;
import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.helper.JsonMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMemberService extends AbstractBaseService<ApiMemberCommand, ApiMemberQuery, ApiMemberResult, Long> {

    private final ApiMemberRepository apiMemberRepository;
    private final ApiMenuPermissionRepository apiMenuPermissionRepository;
    private final JsonMapperHelper jsonMapperHelper;

    @Transactional(readOnly = true)
    public Optional<ApiMemberResult> getByAccountId(String accountId) {
        AssertUtil.notNull(accountId);

        return apiMemberRepository.selectByAccountId(accountId);
    }

    @Override
    @Transactional
    @ProcessS3File
    public ApiMemberResult save(ApiMemberCommand req) {
        AssertUtil.notNull(req);
        AssertUtil.notNull(req.getAccountId());

        processEncData(req);

        ApiMemberResult res = super.save(req);

        saveMenuPermissions(req.getAccountId(), req.getSelectedMenuCodes());

        return res;
    }

    @Override
    @Transactional
    public void update(Long uid, ApiMemberCommand req) {
        AssertUtil.notNull(uid);
        AssertUtil.notNull(req);
        AssertUtil.notNull(req.getAccountId());

        processEncData(req);

        super.update(uid, req);

        updateMenuPermissions(req.getAccountId(), req.getSelectedMenuCodes());
    }

    private void saveMenuPermissions(String accountId, List<String> selectedMenuCodes) {
        if (selectedMenuCodes == null || selectedMenuCodes.isEmpty()) {
            return;
        }

        for (String code : selectedMenuCodes) {
            ApiMenuPermissionCommand cmd = new ApiMenuPermissionCommand();
            cmd.setAccountId(accountId);
            cmd.setApiCode(code);
            cmd.setOnService(ServiceStatus.IN_SERVICE);
            apiMenuPermissionRepository.save(cmd);
        }
    }

    /**
     * 회원 정보 수정 시 메뉴 권한 상태 변경(1<->0) 및 신규 추가 동기화
     */
    private void updateMenuPermissions(String accountId, List<String> selectedCodes) {
        AssertUtil.notNull(accountId);

        List<ApiMenuPermissionResult> existingList = apiMenuPermissionRepository.selectApiMenuPermissionList(accountId);

        Map<String, ApiMenuPermissionResult> existingMap = existingList.stream()
                .collect(Collectors.toMap(ApiMenuPermissionResult::getApiCode, e -> e));

        Set<String> newSelectedCodes = selectedCodes != null ? new HashSet<>(selectedCodes) : Collections.emptySet();

        // 기존 권한들 중 선택 해제된 것은 onService="0", 재선택된 것은 onService="1"로 변경
        for (ApiMenuPermissionResult result : existingList) {
            String apiCode = result.getApiCode();
            ServiceStatus onService = newSelectedCodes.contains(apiCode) ? ServiceStatus.IN_SERVICE
                    : ServiceStatus.STOPPED;

            if (!onService.equals(result.getOnService())) {
                ApiMenuPermissionCommand cmd = new ApiMenuPermissionCommand();
                cmd.setAccountId(accountId);
                cmd.setApiCode(apiCode);
                cmd.setOnService(onService);

                apiMenuPermissionRepository.update(result.getUid(), cmd);
            }
        }

        // 기존에 없던 새로운 메뉴 권한은 신규 추가 (onService="1")
        for (String code : newSelectedCodes) {
            if (!existingMap.containsKey(code)) {
                ApiMenuPermissionCommand cmd = new ApiMenuPermissionCommand();
                cmd.setAccountId(accountId);
                cmd.setApiCode(code);
                cmd.setOnService(ServiceStatus.IN_SERVICE);
                apiMenuPermissionRepository.save(cmd);
            }
        }
    }

    private void processEncData(ApiMemberCommand req) {
        if (Objects.equals(req.getIsEncrypted(), (short) 1)) {
            Map<String, String> encMap = new HashMap<>();
            encMap.put("enc_info", req.getEncInfo());
            encMap.put("enc_key", req.getEncKey());
            encMap.put("enc_iv", req.getEncIv());

            req.setEncData(jsonMapperHelper.writeValueAsString(encMap));
        }
    }
}