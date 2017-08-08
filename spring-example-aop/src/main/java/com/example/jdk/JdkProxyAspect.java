package com.example.jdk;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class JdkProxyAspect {

    @Before("execution(public * com.example.jdk.Person.*(..))")
    public void before() {
        System.out.println("before sayHello");
    }
}
