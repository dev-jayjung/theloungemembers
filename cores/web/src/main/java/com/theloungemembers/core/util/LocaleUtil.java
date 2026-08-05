package com.theloungemembers.core.util;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleUtil {

    private LocaleUtil() {}

    /**
     * 현재 요청의 Locale을 반환 LocaleContextHolder가 null일 경우 fallback으로 KOREA를 반환
     */
    public static Locale getCurrentLocale() {
        Locale locale = LocaleContextHolder.getLocale();

        return locale == null ? Locale.KOREA : locale;
    }

    /**
     * 현재 Locale의 언어 코드를 반환 (예: "ko", "en", "ja")
     */
    public static String getLanguage() {
        return getCurrentLocale().getLanguage();
    }

    /**
     * 현재 Locale의 국가 코드를 반환 (예: "KR", "US", "JP")
     */
    public static String getCountry() {
        return getCurrentLocale().getCountry();
    }
}