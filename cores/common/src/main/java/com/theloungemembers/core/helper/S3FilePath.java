package com.theloungemembers.core.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum S3FilePath {

    PUBLIC("public", "공개"),
    TEMP("temp", "임시"),
    COMMON("common", "공통"),
    BOARD("board", "게시판"),
    EDITOR("editor", "에디터"),

    ;

    private final String code;
    private final String description;
}