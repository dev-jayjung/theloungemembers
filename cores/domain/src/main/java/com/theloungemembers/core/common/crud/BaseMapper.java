package com.theloungemembers.core.common.crud;

import java.util.List;
import java.util.Optional;

/**
 * 기본 Read 공통 메서드 제공하는 매퍼
 *
 * @param <Q> Query (검색 조건 DTO)
 * @param <R> Result (응답 DTO)
 * @param <ID> PK 타입 (Integer, Long 등)
 */
public interface BaseMapper<Q, R, ID> {
    Optional<R> selectById(ID id);

    List<R> selectList(Q q);

    int selectCount(Q q);
}