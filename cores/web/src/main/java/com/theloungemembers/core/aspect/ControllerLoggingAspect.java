package com.theloungemembers.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.theloungemembers.core.helper.JsonMapperHelper;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerLoggingAspect {

    private final JsonMapperHelper jsonMapperHelper;

    // Controller 및 RestController 하위 모든 메서드 대상
    @Pointcut("within(@org.springframework.stereotype.Controller *) || within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            return;

        HttpServletRequest request = attributes.getRequest();

        log.info("================ [REQUEST LOG] ================");
        log.info("URI         : [{}] {}", request.getMethod(), request.getRequestURI());
        log.info("Controller  : {}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());

        // 컨트롤러 메서드로 전달된 파라미터/바디 객체들 출력
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }

            if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof Model) {
                continue;
            }

            try {
                // DTO/Command 객체를 JSON 문자열로 변환하여 출력
                String jsonValue = jsonMapperHelper.writeValueAsString(arg);
                log.info("Parameter   : ({}) {}", arg.getClass().getSimpleName(), jsonValue);
            } catch (Exception e) {
                log.info("Parameter   : ({}) {}", arg.getClass().getSimpleName(), arg);
            }
        }
        log.info("===============================================");
    }
}
