package com.example.quartz;

import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

public class QuartzJobFactory extends AdaptableJobFactory {

    private AutowireCapableBeanFactory beanFactory;

    public QuartzJobFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();
        Object job = beanFactory != null && jobClass.isAnnotationPresent(Component.class)
                ? beanFactory.createBean(jobClass)
                : jobClass.newInstance();
        return Proxy.newProxyInstance(jobClass.getClassLoader(), jobClass.getInterfaces(), new QuartzJobHandler(job));
    }
}
