package com.example.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PersonAroundAdvice implements InvocationHandler {

    Object target;

    public PersonAroundAdvice(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before...");
        Object retVal = method.invoke(target, args);
        System.out.println("after...");
        return retVal;
    }
}
