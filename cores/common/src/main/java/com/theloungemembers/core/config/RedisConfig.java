package com.theloungemembers.core.config;

import java.time.Duration;

import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;

@Configuration
public class RedisConfig {

    @Bean
    @Profile("local")
    RedisConnectionFactory localRedisConnectionFactory(DataRedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    @Profile("!local")
    RedisConnectionFactory redisConnectionFactory(DataRedisProperties redisProperties) {
        RedisClusterConfiguration config = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));

        SocketOptions socketOptions = SocketOptions.builder()
                .connectTimeout(Duration.ofMillis(100L))
                .keepAlive(true)
                .build();

        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .dynamicRefreshSources(true)
                .enablePeriodicRefresh(Duration.ofSeconds(30L))
                .build();

        ClientOptions clientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(clusterTopologyRefreshOptions)
            .socketOptions(socketOptions)
            .build();

        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofMillis(3000L))
                .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfiguration);
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}