package com.theloungemembers.core.common.typehandler;

import com.theloungemembers.core.type.ServiceStatus;

public final class MyBatisEnumHandler {

    private MyBatisEnumHandler() {}

//    @MappedTypes(ServiceStatus.class)
    public static class ServiceStatusHandler extends AbstractEnumHandler<ServiceStatus> {
        public ServiceStatusHandler() { super(ServiceStatus.class); }
    }
}