package com.theloungemembers.web.common.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfig {

    @Value("${spring.thymeleaf.cache:true}")
    private boolean isCacheable;

    private static final String VIEW_PREFIX = "classpath:/templates/views/";
    private static final String ROOT_PREFIX = "classpath:/templates/";
    private static final String DEFAULT_SUFFIX = ".html";

    @Bean
    SpringResourceTemplateResolver viewTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix(VIEW_PREFIX);
        resolver.setSuffix(DEFAULT_SUFFIX);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(isCacheable);
        resolver.setOrder(1);
        resolver.setCheckExistence(true); // 파일이 없을 경우 다음 리졸버로 넘김

        return resolver;
    }

    @Bean
    SpringResourceTemplateResolver fallbackTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix(ROOT_PREFIX);
        resolver.setSuffix(DEFAULT_SUFFIX);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(isCacheable);
        resolver.setOrder(2);

        return resolver;
    }
}