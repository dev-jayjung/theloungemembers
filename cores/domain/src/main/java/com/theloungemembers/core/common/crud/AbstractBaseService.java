package com.theloungemembers.core.common.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.util.AssertUtil;

/**
 * 기본 CRUD 공통 로직을 제공하는 추상 서비스
 *
 * @param <C> Command (등록/수정 DTO)
 * @param <Q> Query (검색 조건 DTO)
 * @param <R> Result (응답 DTO)
 * @param <ID> PK 타입 (Integer, Long 등)
 */
public abstract class AbstractBaseService<C, Q extends PageRequest, R, ID> implements BaseService<C, Q, R, ID> {

    protected AbstractBaseRepository<C, Q, R, ID, ?, ?, ?> repository;

    @Autowired
    public void setRepository(@Lazy AbstractBaseRepository<C, Q, R, ID, ?, ?, ?> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<R> getList(Q query) {
        AssertUtil.notNull(query);

        return repository.selectList(query);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<R> getPage(Q query) {
        AssertUtil.notNull(query);

        return repository.selectPage(query);
    }

    @Override
    @Transactional(readOnly = true)
    public R get(ID id) {
        AssertUtil.notNull(id);

        return repository.selectById(id)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional
    public R save(C command) {
        AssertUtil.notNull(command);

        return repository.save(command);
    }

    @Override
    @Transactional
    public void update(ID id, C command) {
        AssertUtil.notNull(id);
        AssertUtil.notNull(command);

        repository.update(id, command);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        AssertUtil.notNull(id);

        repository.delete(id);
    }
}