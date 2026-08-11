package com.theloungemembers.core.common.config;

import org.springframework.stereotype.Component;

import com.theloungemembers.core.security.AuditorContextAdapter;

@Component
public class AuditorContextAdapterImpl implements AuditorContextAdapter {

    @Override
    public void setAuditorId(Long auditorId) {
        AuditorContextHolder.setAuditorId(auditorId);
    }

    @Override
    public void clear() {
        AuditorContextHolder.clear();
    }
}