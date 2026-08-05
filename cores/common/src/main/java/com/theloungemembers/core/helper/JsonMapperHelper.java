package com.theloungemembers.core.helper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.theloungemembers.core.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class JsonMapperHelper {

    private final JsonMapper jsonMapper;

    public <T> T convert(Object obj, Class<T> clazz) {
        try {
            return jsonMapper.convertValue(obj, clazz);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public <T> T convert(Object obj, TypeReference<T> typeReference) {
        try {
            return jsonMapper.convertValue(obj, typeReference);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public <T> List<T> convertToList(Object obj, Class<T> clazz) {
        try {
            return jsonMapper.convertValue(obj, jsonMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public <T> Map<String, T> convertToMap(Object obj, Class<T> clazz) {
        try {
            return jsonMapper.convertValue(obj, jsonMapper.getTypeFactory().constructMapType(Map.class, String.class, clazz));
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public String writeValueAsString(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return obj.toString();
        }

        try {
            return jsonMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public <T> T readValue(String str, Class<T> clazz) {
        if (str == null || str.isBlank()) {
            return null;
        }

        try {
            return jsonMapper.readValue(str, clazz);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public <T> T readValue(String str, TypeReference<T> typeReference) {
        if (str == null || str.isBlank()) {
            return null;
        }

        try {
            return jsonMapper.readValue(str, typeReference);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}