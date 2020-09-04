package com.thinkitive.cruddemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    @Before("allControllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        log.info("method - {}", joinPoint.getSignature().toShortString());
    }

    @Pointcut("execution(* com.thinkitive.cruddemo.ui.controller.*.*(..))")
    public void allControllerMethods() {
    }
}
