package com.theloungemembers.web.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkerPrincipal {
    private final String workerId;
    private final String workerName;
    private final Collection<? extends GrantedAuthority> authorities;
}