package com.theloungemembers.core.helper;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisHelper {

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonMapperHelper jsonMapperHelper;

    public void set(String key, Object value, Duration timeout) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(value);
        AssertUtil.notNull(timeout);

        String stringValue = (value instanceof String str)
                ? str
                : jsonMapperHelper.writeValueAsString(value);

        redisTemplate.opsForValue().set(key, stringValue, timeout);
    }

    public Optional<String> get(String key) {
        AssertUtil.notNull(key);

        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key, Class<T> targetClass) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(targetClass);

        String jsonValue = redisTemplate.opsForValue().get(key);

        if (jsonValue == null) {
            return Optional.empty();
        }

        if (String.class.equals(targetClass)) {
            return Optional.of((T) jsonValue);
        }

        return Optional.ofNullable(jsonMapperHelper.readValue(jsonValue, targetClass));
    }

    public <T> List<T> getList(String key, Class<T> targetClass) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(targetClass);

        String jsonValue = redisTemplate.opsForValue().get(key);

        if (jsonValue == null || jsonValue.isBlank()) {
            return List.of();
        }

        List<T> list = jsonMapperHelper.readListValue(jsonValue, targetClass);

        return list != null ? list : List.of();
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