package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiUsageLogV3WebhookEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUsageLogV3WebhookJpaRepository extends JpaRepository<ApiUsageLogV3WebhookEntity, Long> {
}
