package com.theloungemembers.core.helper;

import java.util.List;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelMapperHelper {

    private final ModelMapper modelMapper;

    public <S, T> T map(S source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }

        return modelMapper.map(source, targetClass);
    }

    public <S, D> Function<S, D> map(Class<D> targetClass) {
        return source -> map(source, targetClass);
    }

    public void map(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        modelMapper.map(source, target);
    }

    public <T, R> List<R> mapList(List<T> list, Class<R> targetClass) {
        return mapList(list, map(targetClass));
    }

    public <T, R> Function<List<T>, List<R>> mapList(Class<R> targetClass) {
        return list -> mapList(list, targetClass);
    }

    public <T, R> List<R> mapList(List<T> list, Function<T, R> mapper) {
        if (list == null) {
            return List.of();
        }

        AssertUtil.notNull(mapper, "mapper must not be null");

        return list.stream().map(mapper).toList();
    }
}