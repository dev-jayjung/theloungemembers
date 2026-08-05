package com.theloungemembers.core.config;

import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        if (isPortInUse(redisPort)) {
            log.info("이미 레디스 실행중");
            return;
        }

        try {
            redisServer = RedisServer.newRedisServer()
                    .port(redisPort)
                    .setting("maxmemory 128M")
                    .build();

            redisServer.start();
            log.info("레디스 정상 실행");
        } catch (IOException e) {
            log.error("레디스 실행중 에러");
        }

    }

    @PreDestroy
    private void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            try {
                redisServer.stop();
                log.info("레디스 종료 완료");
            } catch (IOException e) {
                log.warn("레디스 종료중 에러");
            }
        }
    }

    private boolean isPortInUse(int port) {
        try (ServerSocket _ = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}