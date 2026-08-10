package com.theloungemembers.core.helper;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.theloungemembers.core.exception.CommonErrorCode;
import com.theloungemembers.core.util.LocaleUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageHelper {

    private final MessageSource messageSource;

    public String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, key, LocaleUtil.getCurrentLocale());
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(CommonErrorCode code) {
        return getMessage(code.getKey());
    }
}