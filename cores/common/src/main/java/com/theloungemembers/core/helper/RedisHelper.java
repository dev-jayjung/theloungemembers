package com.theloungemembers.core.helper;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;

@Component
@RequiredArgsConstructor
public class RedisHelper {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonMapperHelper jsonMapperHelper;

    public void set(String key, Object value, Duration timeout) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(value);
        AssertUtil.notNull(timeout);


        redisTemplate.opsForValue().set(key, jsonMapperHelper.writeValueAsString(value), timeout);
    }

    public Optional<String> get(String key) {
        AssertUtil.notNull(key);

        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public <T> Optional<T> get(String key, Class<T> targetClass) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(targetClass);

        String jsonValue = redisTemplate.opsForValue().get(key);

        if (jsonValue == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(jsonMapperHelper.readValue(jsonValue, targetClass));
    }

    public <T> Optional<T> get(String key, TypeReference<T> typeReference) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(typeReference);

        String jsonValue = redisTemplate.opsForValue().get(key);

        if (jsonValue == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(jsonMapperHelper.readValue(jsonValue, typeReference));
    }
    public boolean delete(String key) {
        AssertUtil.notNull(key);

        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        AssertUtil.notNull(key);

        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean expire(String key, Duration timeout) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(timeout);

        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout));
    }

    public Optional<Long> getExpire(String key) {
        AssertUtil.notNull(key);

        return Optional.ofNullable(redisTemplate.getExpire(key));
    }
}