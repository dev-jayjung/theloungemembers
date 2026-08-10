package com.theloungemembers.core.common.crud;

import java.util.List;
import java.util.Optional;

import com.theloungemembers.core.common.dto.PageResponse;

/**
 * 기본 CRUD 공통 메서드 제공하는 레포지토리
 *
 * @param <C> Command (등록/수정 DTO)
 * @param <Q> Query (검색 조건 DTO)
 * @param <R> Result (응답 DTO)
 * @param <ID> PK 타입 (Integer, Long 등)
 */
interface BaseRepository<C, Q, R, ID> {
    Optional<R> selectById(ID id);

    List<R> selectList(Q q);

    PageResponse<R> selectPage(Q q);

    R save(C c);

    void update(ID id, C c);

    void delete(ID id);
}