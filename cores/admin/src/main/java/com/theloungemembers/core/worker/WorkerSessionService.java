package com.theloungemembers.core.worker;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.theloungemembers.core.helper.RedisHelper;
import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerSessionService {

    private final RedisHelper redisHelper;
    private static final String SESSION_KEY_PREFIX = "AUTH:WORKER:SESSION:";

    public String createSession(String workerId, Duration timeout) {
        AssertUtil.notNull(workerId, "workerId must not be null");
        AssertUtil.notNull(timeout, "timeout must not be null");

        String sessionId = UUID.randomUUID().toString();

        redisHelper.set(SESSION_KEY_PREFIX + sessionId, workerId, timeout);

        return sessionId;
    }

    public Optional<String> getWorkerId(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return Optional.empty();
        }

        return redisHelper.get(SESSION_KEY_PREFIX + sessionId, String.class);
    }

    public void removeSession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return;
        }

        redisHelper.delete(SESSION_KEY_PREFIX + sessionId);
    }
}