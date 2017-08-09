package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AOPProxyAspect {

    @Before(value = "execution(public * com.example.aop.User*.sayHello(..)) && args(lastName, firstName)")
    public void before(String lastName, String firstName) {
        System.out.println(String.format("before %s %s sayHello", lastName, firstName));
    }

    @After(value = "execution(public * com.example.aop.User.sayHello(..)) && args(lastName, firstName)")
    public void after(String lastName, String firstName) {
        System.out.println(String.format("after %s %s sayHello", lastName, firstName));
    }

    @AfterReturning(value = "execution(public * com.example.aop.User.sayHello(..)) && args(lastName, firstName)", returning = "retVal")
    public void afterReturning(String lastName, String firstName, String retVal) {
        System.out.println(String.format("afterReturning %s %s sayHello, retVal %s", lastName, firstName, retVal));
    }

    @Around(value = "execution(public * com.example.aop.User.sayHello(..)) && args(lastName, firstName)")
    public Object around(ProceedingJoinPoint point, String lastName, String firstName) throws Throwable {
        System.out.println(String.format("around:before %s %s sayHello", lastName, firstName));
        Object retVal = point.proceed();
        System.out.println(String.format("around:after %s %s sayHello", lastName, firstName));
        return retVal;
    }

    @AfterThrowing(value = "execution(public * com.example.aop.User.sayHello(..)) && args(lastName, firstName)", throwing="exception")
    public void afterThrowing(String lastName, String firstName, Exception exception) {
        System.out.println(String.format("afterThrowing %s %s sayHello, exception %s", lastName, firstName, exception));
    }
}
