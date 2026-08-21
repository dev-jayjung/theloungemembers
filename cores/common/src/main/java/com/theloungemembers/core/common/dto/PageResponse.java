package com.theloungemembers.core.common.dto;

import java.util.List;
import java.util.function.Function;

import lombok.Getter;

@Getter
public class PageResponse<T> {
    private List<T> content;

    private int page;

    private int size;

    private int totalPages;

    private long totalElements;

    public PageResponse(List<T> content, int page, int size, int totalPages, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public <R> PageResponse<R> map(Function<T, R> mapper) {
        List<R> convertedContent = this.content.stream()
                .map(mapper)
                .toList();

        return new PageResponse<>(
                convertedContent,
                this.page,
                this.size,
                this.totalPages,
                this.totalElements);
    }
}