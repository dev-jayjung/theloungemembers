package com.theloungemembers.core.config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.theloungemembers.core.exception.BusinessException;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.OffsetDateTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {

    @Bean
    JsonMapper jsonMapper() {
        String format = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(format);

        SimpleModule simpleModule = new SimpleModule("CustomDateModule");
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern));
        simpleModule.addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE.withFormatter(ofPattern));
        simpleModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        simpleModule.addDeserializer(OffsetDateTime.class, new CustomOffsetDateTimeDeserializer());
        simpleModule.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());
        simpleModule.addDeserializer(LocalTime.class, new CustomLocalTimeDeserializer());
        simpleModule.addDeserializer(String.class, new EmptyStringToNullDeserializer());

        return JsonMapper.builder()
                .addModule(simpleModule)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .changeDefaultPropertyInclusion(e -> e
                    .withValueInclusion(JsonInclude.Include.NON_NULL)
                    .withContentInclusion(JsonInclude.Include.NON_NULL)
                )
                .defaultDateFormat(new SimpleDateFormat(format))
                .defaultTimeZone(TimeZone.getDefault())
                .defaultLocale(Locale.getDefault())
                .build();
    }

    private class EmptyStringToNullDeserializer extends StdDeserializer<String> {
        public EmptyStringToNullDeserializer() {
            super(String.class);
        }

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String value = p.getValueAsString();
            return (value == null || value.trim().isEmpty()) ? null : value.trim();
        }
    }

    private class CustomOffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {
        private static final List<DateTimeFormatter> FORMATTERS = List.of(
                DateTimeFormatter.ofPattern("yyyyMMdd"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
        );

        public CustomOffsetDateTimeDeserializer() {
            super(OffsetDateTime.class);
        }

        @Override
        public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String value = p.getString().trim();
            if (value.isEmpty()) {
                return null;
            }

            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    if (formatter.equals(FORMATTERS.get(0)) || formatter.equals(FORMATTERS.get(1))) {
                        // 1️ yyyyMMdd / yyyy-MM-dd 날짜만 있을 때 00:00:00 으로 설정
                        LocalDate date = LocalDate.parse(value, formatter);

                        return date.atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
                    } else if (formatter.equals(FORMATTERS.get(2)) || formatter.equals(FORMATTERS.get(3))) {
                        // 2️ yyyy-MM-dd HH:mm:ss 로컬시간 기준 OffsetDateTime 변환
                        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);

                        return dateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
                    } else {
                        // 3️ ISO 형식 (기존 Offset 포함)
                        return OffsetDateTime.parse(value, formatter);
                    }
                // 에러시 다음 포맷으로
                } catch (Exception ignored) {}
            }

            throw new BusinessException("지원하지 않는 날짜 형식입니다: " + value);
        }
    }

    private class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
        private static final List<DateTimeFormatter> FORMATTERS = List.of(
                DateTimeFormatter.ofPattern("yyyyMMdd"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
        );

        public CustomLocalDateTimeDeserializer() {
            super(LocalDateTime.class);
        }

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String value = p.getString().trim();
            if (value.isEmpty()) {
                return null;
            }

            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    if (formatter.equals(FORMATTERS.get(0)) || formatter.equals(FORMATTERS.get(1))) {
                        // 1️ yyyyMMdd / yyyy-MM-dd 날짜만 있을 때 00:00:00 으로 설정
                        LocalDate date = LocalDate.parse(value, formatter);

                        return date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
                    } else if (formatter.equals(FORMATTERS.get(2)) || formatter.equals(FORMATTERS.get(3))) {
                        // 2️ yyyy-MM-dd HH:mm:ss 로컬시간 기준 OffsetDateTime 변환
                        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);

                        return dateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    } else {
                        // 3️ ISO 형식 (기존 Offset 포함)
                        return LocalDateTime.parse(value, formatter);
                    }
                    // 에러시 다음 포맷으로
                } catch (Exception ignored) {}
            }

            throw new BusinessException("지원하지 않는 날짜 형식입니다: " + value);
        }
    }

    private class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {
        private static final List<DateTimeFormatter> FORMATTERS = List.of(
                DateTimeFormatter.ofPattern("yyyyMMdd"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ISO_LOCAL_DATE
        );

        public CustomLocalDateDeserializer() {
            super(LocalDate.class);
        }

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String value = p.getString().trim();
            if (value.isEmpty()) {
                return null;
            }

            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    if (formatter.equals(FORMATTERS.get(0)) || formatter.equals(FORMATTERS.get(1))) {
                        // 1️ yyyyMMdd / yyyy-MM-dd 날짜만
                        LocalDate date = LocalDate.parse(value, formatter);

                        return date.atStartOfDay(ZoneId.systemDefault()).toLocalDate();
                    } else {
                        // 2 ISO 형식
                        return LocalDate.parse(value, formatter);
                    }
                    // 에러시 다음 포맷으로
                } catch (Exception ignored) {}
            }

            throw new BusinessException("지원하지 않는 날짜 형식입니다: " + value);
        }
    }

    private class CustomLocalTimeDeserializer extends StdDeserializer<LocalTime> {
        private static final List<DateTimeFormatter> FORMATTERS = List.of(
                DateTimeFormatter.ofPattern("HHmmss"),
                DateTimeFormatter.ofPattern("HH:mm:ss"),
                DateTimeFormatter.ofPattern("HHmm"),
                DateTimeFormatter.ofPattern("HH:mm"),
                DateTimeFormatter.ISO_LOCAL_TIME
        );

        public CustomLocalTimeDeserializer() {
            super(LocalTime.class);
        }

        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String value = p.getString().trim();
            if (value.isEmpty()) {
                return null;
            }

            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    if (formatter.equals(FORMATTERS.get(0)) || formatter.equals(FORMATTERS.get(1))
                           || formatter.equals(FORMATTERS.get(2)) || formatter.equals(FORMATTERS.get(3))) {
                        // 1️ 위에 정해진 포맷
                        LocalTime time = LocalTime.parse(value, formatter);

                        return time;
                    } else {
                        // 2 ISO 형식
                        return LocalTime.parse(value, formatter);
                    }
                    // 에러시 다음 포맷으로
                } catch (Exception ignored) {}
            }

            throw new BusinessException("지원하지 않는 날짜 형식입니다: " + value);
        }
    }
}