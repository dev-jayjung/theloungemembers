package com.theloungemembers.core.auth;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.security.PasswordValidator;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.worker.WorkerResult;
import com.theloungemembers.core.worker.WorkerService;
import com.theloungemembers.core.worker.WorkerSessionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final WorkerService workerService;
    private final PasswordValidator passwordValidator;
    private final KeycloakClientService keycloakClientService;
    private final WorkerSessionService workerSessionService;

    @Transactional(readOnly = true)
    public LoginResult login(String workerId, String password) {
        AssertUtil.notNull(workerId, "workerId is not null");
        AssertUtil.notNull(password, "password is not null");

        // 사용자 조회
        WorkerResult worker = workerService.getByWorkerId(workerId);

        if (!passwordValidator.matches(password, worker.getPassword())) {
            log.warn("비밀번호 불일치 - ID: {}", workerId);
            throw new BusinessException(CommonErrorCode.UNAUTHORIZED);
        }

        // TODO 추후 패스워드 암호화 방식 변경시 아래 로직 개방
        if (passwordValidator.isLegacyPassword(worker.getPassword())) {
//            String newBcryptPassword = passwordValidator.encode(password);
//            WorkerCommand command = new WorkerCommand();
//            command.setUid(worker.getUid());
//            command.setPassword(newBcryptPassword);
//
//            workerRepository.update(command);
//            log.info("비밀번호 BCrypt 마이그레이션 완료 - ID: {}", workerId);
        }

        // TODO 자체 OTP 2차 검증 로직 필요하다 함. 추후 필요
        // checkOtpSecret(worker, request.otpCode());

        // TODO Keycloak 토큰 발급 - 현재는 그냥 JWT 발급 용도로만 사용. 추후 어떻게 할지에 따라 변경 필요
        TokenResult keycloakToken = keycloakClientService.getServiceAccountToken();

        String sessionId = workerSessionService.createSession(workerId, Duration.ofSeconds(keycloakToken.getExpiresIn()));

        log.info("로그인 성공 - User: {}, Role: {}", worker.getWorkerId(), worker.getWorkerName());

        // 클라이언트에 전달할 최종 인증 객체 리턴
        return new LoginResult(keycloakToken, sessionId);
    }

    public void logout(String sessionId) {
        if (sessionId != null) {
            workerSessionService.removeSession(sessionId);
        }
    }
}