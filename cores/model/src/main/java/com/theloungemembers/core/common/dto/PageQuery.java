package com.theloungemembers.core.common.dto;

import java.time.LocalDateTime;

import com.google.common.base.CaseFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQuery {
    private int page = 0;

    private int size = 15;

    private String sortBy;

    private String direction = "DESC";

//    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime startDtm;

//    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime endDtm;

    private String searchKeyword;

    public int getPage() {
//        return Math.max(page, 0);
        return page <= 0 ? 0 : page - 1;
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

        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortBy) + " " + direction;
    }
}