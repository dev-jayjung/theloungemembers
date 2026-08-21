package com.theloungemembers.core.util;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import com.theloungemembers.core.common.dto.PageRequest;
import com.theloungemembers.core.common.dto.PageResponse;

public class PageUtil {

    private PageUtil() {
    }

    private static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        return new PageResponse<>(
                content,
                page,
                size,
                size == 0 ? 0 : (int) Math.ceil((double) totalElements / size),
                totalElements);
    }

    /**
     * custom paging 처리, 복잡 쿼리에 사용
     * 
     * @param <T>
     * @param pageRequest   - 검색조건
     * @param supplier      - 목록 조회 쿼리
     * @param countSupplier - 카운트 조회 쿼리
     * @return PageResponse<T>
     */
    public static <T> PageResponse<T> getPage(PageRequest pageRequest, Supplier<List<T>> supplier,
            Supplier<Integer> countSupplier) {
        AssertUtil.notNull(pageRequest, "pageRequest must not be null");
        AssertUtil.notNull(supplier, "supplier must not be null");
        AssertUtil.notNull(countSupplier, "countSupplier must not be null");

        List<T> list = supplier.get();

        if (list == null || list.isEmpty()) {
            return of(Collections.emptyList(), pageRequest.getPage(), pageRequest.getSize(), 0);
        }

        if (pageRequest.getPage() == 0 && list.size() < pageRequest.getSize()) {
            return of(list, pageRequest.getPage(), pageRequest.getSize(), list.size());
        }

        int total = countSupplier.get();

        return of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
}