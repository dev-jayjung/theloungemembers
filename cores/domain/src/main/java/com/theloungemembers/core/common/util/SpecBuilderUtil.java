package com.theloungemembers.core.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class SpecBuilderUtil<T> {

    private SpecBuilderUtil() {}

    private final List<Specification<T>> specs = new ArrayList<>();

    public static <T> SpecBuilderUtil<T> builder() {
        return new SpecBuilderUtil<>();
    }

    // = (Equals)
    public SpecBuilderUtil<T> eq(String fieldName, Object value) {
        if (value != null) {
            specs.add((root, _, cb) -> cb.equal(root.get(fieldName), value));
        }
        return this;
    }

    // LIKE (%value%)
    public SpecBuilderUtil<T> like(String fieldName, String value) {
        if (value != null && !value.isBlank()) {
            specs.add((root, _, cb) -> cb.like(root.get(fieldName), "%" + value + "%"));
        }
        return this;
    }

    // IN
    public SpecBuilderUtil<T> in(String fieldName, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            specs.add((root, _, _) -> root.get(fieldName).in(values));
        }
        return this;
    }

    // 날짜/숫자 범위 (BETWEEN 또는 >=, <=)
    public <G extends Comparable<? super G>> SpecBuilderUtil<T> gte(String fieldName, G value) {
        if (value != null) {
            specs.add((root, _, cb) -> cb.greaterThanOrEqualTo(root.get(fieldName), value));
        }
        return this;
    }

    public <G extends Comparable<? super G>> SpecBuilderUtil<T> lte(String fieldName, G value) {
        if (value != null) {
            specs.add((root, _, cb) -> cb.lessThanOrEqualTo(root.get(fieldName), value));
        }
        return this;
    }

    // 최종 Specification 결합 (AND)
    public Specification<T> build() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Specification<T> spec : specs) {
                Predicate p = spec.toPredicate(root, query, cb);
                if (p != null) {
                    predicates.add(p);
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}