package com.theloungemembers.core.converter;

import java.util.Arrays;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.theloungemembers.core.type.BaseCodeEnum;

public class StringToBaseCodeEnumConverterFactory implements ConverterFactory<String, BaseCodeEnum> {

    @Override
    public <T extends BaseCodeEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToBaseCodeEnumConverter<>(targetType);
    }

    private static class StringToBaseCodeEnumConverter<T extends BaseCodeEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToBaseCodeEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }

            return Arrays.stream(enumType.getEnumConstants())
                    .filter(e -> e.getCode().equals(source) || e.toString().equals(source))
                    .findFirst()
                    .orElse(null);
        }
    }
}