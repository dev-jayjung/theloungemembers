package com.theloungemembers.core.security;

public interface AuditorContextAdapter {
    void setAuditorId(Long auditorId);

    void clear();
}