package com.theloungemembers.core.common.crud;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.common.entity.BaseEntity;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

/**
 * 기본 CRUD 공통 로직을 제공하는 추상 레포지토리
 *
 * @param <C>  Command (등록/수정 DTO)
 * @param <Q>  Query (검색 조건 DTO)
 * @param <R>  Result (응답 DTO)
 * @param <ID> PK 타입 (Integer, Long 등)
 * @param <E>  Entity (Entity)
 * @param <M>  Mapper (Mybatis Mapper)
 * @param <J>  Repository (JPA Repository)
 */
public abstract class AbstractBaseRepository<C, Q extends PageRequest, R, ID, E, M extends BaseMapper<Q, R, ID>, J extends JpaRepository<E, ID> & JpaSpecificationExecutor<E>>
        implements BaseRepository<C, Q, R, ID> {

    protected M mapper; // MyBatis Mapper
    protected J repository; // JPA Repository
    protected ModelMapperHelper modelMapperHelper;

    private final Class<R> resultClass;
    private final Class<E> entityClass;

    @Autowired
    public void setDependencies(M mapper, J repository, ModelMapperHelper modelMapperHelper) {
        this.mapper = mapper;
        this.repository = repository;
        this.modelMapperHelper = modelMapperHelper;
    }

    @SuppressWarnings("unchecked")
    protected AbstractBaseRepository() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        this.resultClass = (Class<R>) parameterizedType.getActualTypeArguments()[2];
        this.entityClass = (Class<E>) parameterizedType.getActualTypeArguments()[4];
    }

    @Override
    public Optional<R> selectById(ID id) {
        AssertUtil.notNull(id);

        return mapper.selectById(id);
    }

    @Override
    public List<R> selectList(Q q) {
        AssertUtil.notNull(q);

        return mapper.selectList(q);
    }

    public int selectCount(Q q) {
        AssertUtil.notNull(q);

        return mapper.selectCount(q);
    }

    @Override
    public PageResponse<R> selectPage(Q q) {
        AssertUtil.notNull(q);

        return PageUtil.getPage(q, () -> this.selectList(q), () -> this.selectCount(q));
    }

    public Optional<R> findById(ID id) {
        AssertUtil.notNull(id);

        return repository.findById(id)
                .map(e -> modelMapperHelper.map(e, resultClass));
    }

    @Override
    public R save(C command) {
        AssertUtil.notNull(command);

        E entity = modelMapperHelper.map(command, entityClass);

        E e = repository.save(entity);

        return modelMapperHelper.map(e, resultClass);
    }

    @Override
    public void update(ID id, C command) {
        AssertUtil.notNull(id);
        AssertUtil.notNull(command);

        E e = repository.findById(id)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND));

        modelMapperHelper.map(command, e);

        // e.setId();
    }

    @Override
    public void delete(ID id) {
        AssertUtil.notNull(id);

        E e = repository.findById(id)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND));

        if (e instanceof BaseEntity baseEntity) {
            baseEntity.delete();
        } else {
            repository.delete(e);
        }
    }
}