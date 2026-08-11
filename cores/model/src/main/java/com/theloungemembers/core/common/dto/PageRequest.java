package com.theloungemembers.core.common.dto;

import java.time.LocalDateTime;

import com.google.common.base.CaseFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {
    private int page = 0;

    private int size = 15;

    private String sortBy;

    private String direction = "DESC";

//    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime startDtm;

//    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime endDtm;

    private String searchTarget;

    private String searchValue;

    public int getPage() {
        return Math.max(page, 0);
    }

    public int getOffset() {
        return this.getPage() * size;
    }

    /**
     * MyBatis용 정렬 SQL 문자열 생성 (ex: sample_id DESC)
     */
    public String getOrderBy() {
        if (sortBy == null) {
            return null;
        }

        String cleanSortBy = sortBy.replaceAll("[^a-zA-Z0-9]", "");
        if (cleanSortBy.isBlank()) {
            return null;
        }

        String dir = (direction != null && "ASC".equalsIgnoreCase(direction.trim())) ? "ASC" : "DESC";

        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, cleanSortBy) + " " + dir;
    }
}