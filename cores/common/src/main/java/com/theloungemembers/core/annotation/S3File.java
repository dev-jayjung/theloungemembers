package com.theloungemembers.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.theloungemembers.core.helper.S3FilePath;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface S3File {
    /**
     * 최종 이동시킬 S3 Path (기본값: BOARD)
     */
    S3FilePath path() default S3FilePath.BOARD;
}