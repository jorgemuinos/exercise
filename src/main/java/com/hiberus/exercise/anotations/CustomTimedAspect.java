package com.hiberus.exercise.anotations;

import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
public class CustomTimedAspect {
    private static final Logger logger = LogManager.getLogger();

    @Around("@annotation(CustomTimed)")
    public Object executionTimeLog(ProceedingJoinPoint point) throws Throwable
    {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        logger.info("class={} - method={} - time={}",
            ((MethodSignature) point.getSignature()).getDeclaringTypeName(),
            ((MethodSignature) point.getSignature()).getMethod().getName(),
            System.currentTimeMillis() - start
        );
        return result;
    }
}
