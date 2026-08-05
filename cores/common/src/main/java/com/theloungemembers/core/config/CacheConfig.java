package com.theloungemembers.core.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, JsonMapper jsonMapper) {
        // Redis 역직렬화 시 DTO 클래스 타입을 인식할 수 있도록 검증기 생성
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfBaseType(Object.class)
            .build();

        JsonMapper redisJsonMapper = jsonMapper.rebuild()
            .activateDefaultTyping(ptv, DefaultTyping.NON_FINAL)
            .build();

        GenericJacksonJsonRedisSerializer jsonSerializer = new GenericJacksonJsonRedisSerializer(redisJsonMapper);

        // Redis 캐시 기본 설정
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(30))
            .disableCachingNullValues() // null 캐싱 x
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GzipRedisSerializer<>(jsonSerializer)));

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }

    private class GzipRedisSerializer<T> implements RedisSerializer<T> {

        private final RedisSerializer<T> innerSerializer;

        public GzipRedisSerializer(RedisSerializer<T> innerSerializer) {
            this.innerSerializer = innerSerializer;
        }

        @Override
        public byte[] serialize(T value) throws SerializationException {
            byte[] rawData = innerSerializer.serialize(value);
            if (rawData == null || rawData.length == 0) {
                return rawData;
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 GZIPOutputStream gzos = new GZIPOutputStream(baos)) {
                gzos.write(rawData);
                gzos.finish();
                return baos.toByteArray(); // GZIP 압축된 바이트 배열 반환
            } catch (IOException e) {
                throw new SerializationException("GZIP serialization failed", e);
            }
        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length == 0) {
                return null;
            }

            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                 GZIPInputStream gzis = new GZIPInputStream(bais);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int len;
                while ((len = gzis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                byte[] decompressed = baos.toByteArray(); // GZIP 압축 해제된 바이트 배열
                return innerSerializer.deserialize(decompressed);
            } catch (IOException e) {
                throw new SerializationException("GZIP deserialization failed", e);
            }
        }
    }
    // 예시
//    @Cacheable(value = "lounge", key = "#id")
//    public LoungeResult getLounge(Long id) {
          // 캐시 등록
//    }

//    @CacheEvict(value = "lounge", key = "#idNo")
//    public void deleteLoungeCache(Long id) {
          // 캐시 삭제
//    }

//    @CacheEvict(value = "loungeList", allEntries = true)
//    public void delteLoungeAll() {
          // 해당 키 그룹 캐시 전체 삭제
//    }
}