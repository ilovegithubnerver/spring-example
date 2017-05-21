package com.example.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.regex.Pattern;

@Aspect
public class RoutingDataSourceAspect {

    private static String regex = "^(get|list|count|exist).*";

    @Around("execution(* com.example.mapper.*Mapper.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        String methodName = point.getSignature().getName();
        if (Pattern.matches(regex, methodName)) {
            RoutingDataSourceHolder.set(RoutingDataSourceType.READ);
        } else {
            RoutingDataSourceHolder.set(RoutingDataSourceType.WRITE);
        }

        Object res = point.proceed();

        RoutingDataSourceHolder.clear();

        return res;
    }

}
