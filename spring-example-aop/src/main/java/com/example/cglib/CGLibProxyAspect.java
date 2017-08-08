package com.example.cglib;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class CGLibProxyAspect {

    @Before("execution(public * com.example.cglib.English.*(..))")
    public void before() {
        System.out.println("before sayHello");
    }
}
