package com.theloungemembers.core.common.converter;

import java.util.EnumSet;

import com.theloungemembers.core.common.type.BaseCodeEnum;

import jakarta.persistence.AttributeConverter;

public abstract class AbstractEnumConverter<E extends Enum<E> & BaseCodeEnum>
        implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    public AbstractEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return EnumSet.allOf(enumClass)
                .stream()
                .filter(e -> e.getCode().equals(dbData))
                .findFirst()
                .orElse(null);
    }
}