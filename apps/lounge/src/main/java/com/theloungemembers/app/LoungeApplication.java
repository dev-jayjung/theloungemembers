package com.theloungemembers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.theloungemembers.app", "com.theloungemembers.core"})
public class LoungeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoungeApplication.class, args);
    }
}