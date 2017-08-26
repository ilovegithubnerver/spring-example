package com.example.quartz;

import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

public class QuartzJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Class<? extends Job> jobClass = bundle.getJobDetail().getJobClass();
        return jobClass.isAnnotationPresent(Component.class) ? capableBeanFactory.createBean(jobClass) : jobClass.newInstance();
    }
}
