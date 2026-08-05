package com.theloungemembers.core.common.config;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EntityScan(basePackages = "com.theloungemembers.core.**.entity")
@EnableJpaRepositories(basePackages = "com.theloungemembers.core.**.repository")
@MapperScan(value = { "com.theloungemembers.core.**.mapper" }, annotationClass = Mapper.class)
public class DatabaseConfig {

    @Value("${mybatis.config-location}")
    private String configLocation;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Bean
    @Primary
    DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now(Clock.systemDefaultZone()));
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(configLocation));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
//        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);

        return sqlSessionFactoryBean.getObject();
    }

//    @Bean
//    AuditorAware<Long> auditorAware() {
//        return new AuditorAware<Long>() {
//
//            @Override
//            public Optional<Long> getCurrentAuditor() {
//                try {
//                    return Optional.ofNullable(SecurityUtil.getMbrNo());
//                } catch (Exception e) {
//                    return Optional.of(0L);
//                }
//            }
//        };
//    }
}