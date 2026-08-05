package com.theloungemembers.core.common.converter;

import com.theloungemembers.core.type.ServiceStatus;

import jakarta.persistence.Converter;

public final class JpaEnumConverter {

    private JpaEnumConverter() {}

    @Converter(autoApply = true)
    public static class ServiceStatusConverter extends AbstractEnumConverter<ServiceStatus> {
        public ServiceStatusConverter() {
            super(ServiceStatus.class);
        }
    }
}