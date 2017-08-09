package com.example.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

public class FilteredAdvisor implements PointcutAdvisor {

    private Pointcut pointcut;
    private Advice advice;

    public FilteredAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
