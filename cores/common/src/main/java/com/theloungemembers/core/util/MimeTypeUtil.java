package com.theloungemembers.core.util;

import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;

import com.theloungemembers.core.exception.BusinessException;


/**
 * Apache Tika 기반 파일 확장자/MIME 타입 분석 및 미디어 형식(Image, Excel, PDF 등) 검증 정적 유틸리티
 */
public class MimeTypeUtil {
    private static final Tika tika = new Tika();

    private MimeTypeUtil() {}

    public static String detectMimeType(String fileName) {
        try {
            return tika.detect(fileName);
        } catch (IllegalStateException e) {
            throw new BusinessException("detect error");
        }
    }

    public static boolean is(String fileName, String mimeType) {
        return detectMimeType(fileName).equals(mimeType);
    }

    public static boolean is(String fileName, List<String> mimeTypeList) {
        if (mimeTypeList == null) {
            mimeTypeList = List.of();
        }

        return mimeTypeList.contains(detectMimeType(fileName));
    }

    public static boolean isImage(String fileName) {
        return detectMimeType(fileName).startsWith("image");
    }

    public static boolean isExcel(String fileName) {
        return Arrays.asList("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-excel").contains(detectMimeType(fileName));
    }

    public static boolean isText(String fileName) {
        return "text/plain".equals(detectMimeType(fileName));
    }

    public static boolean isPdf(String fileName) {
        return "application/pdf".equals(detectMimeType(fileName));
    }

    public static boolean isWord(String fileName) {
        return "application/msword".equals(detectMimeType(fileName));
    }
}