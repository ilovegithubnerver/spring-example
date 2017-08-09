package com.example.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class EnglishAroundAdvice implements MethodInterceptor {
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before...");
        Object retVal = proxy.invokeSuper(target, args);
        System.out.println("after...");
        return retVal;
    }
}
