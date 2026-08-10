package com.theloungemembers.core.common.config;

import java.util.Optional;

public interface CurrentUserProvider {
    Optional<Long> getCurrentUserId();
}