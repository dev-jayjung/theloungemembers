package com.theloungemembers.core.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper(Condition<Object, Object> skipEntityIdCondition) {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                // .setPropertyCondition(Conditions.isNotNull())
                .setPropertyCondition(skipEntityIdCondition)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper;
    }

    @Bean
    public Condition<Object, Object> skipEntityIdCondition() {
        Map<String, Boolean> idFieldCache = new ConcurrentHashMap<>();

        return context -> {
            if (context.getMapping() != null && context.getMapping().getLastDestinationProperty() != null) {
                Class<?> clazz = context.getMapping().getLastDestinationProperty().getInitialType();
                String propertyName = context.getMapping().getLastDestinationProperty().getName();
                String cacheKey = clazz.getName() + "#" + propertyName;

                boolean isIdField = idFieldCache.computeIfAbsent(cacheKey,
                        key -> isAnnotatedWithId(clazz, propertyName));
                if (isIdField) {
                    return false;
                }
            }
            return true;
        };
    }

    private boolean isAnnotatedWithId(Class<?> clazz, String propertyName) {
        String lowerName = propertyName.toLowerCase();
        boolean matchesIdPattern = lowerName.equals("uid");

        if (!matchesIdPattern) {
            return false; // name, email, updateDate 같은 일반 필드는 리플렉션 없이 즉시 통과
        }

        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            try {
                Field field = current.getDeclaredField(propertyName);
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    String annotationName = annotation.annotationType().getSimpleName();
                    if ("Id".equals(annotationName) || "EmbeddedId".equals(annotationName)) {
                        return true;
                    }
                }
            } catch (NoSuchFieldException ignored) {
            }
            current = current.getSuperclass();
        }
        return false;
    }
}