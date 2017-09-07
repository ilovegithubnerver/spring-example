package com.example.quartz;

import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

public class QuartzJobFactory extends AdaptableJobFactory {

    private AutowireCapableBeanFactory beanFactory;

    public QuartzJobFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();
        return beanFactory != null && (jobClass.isAnnotationPresent(Component.class) || jobClass.isAnnotationPresent(Configurable.class))
                ? beanFactory.createBean(jobClass)
                : jobClass.newInstance();
    }
}
