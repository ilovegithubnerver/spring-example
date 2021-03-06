package com.example.quartz;

import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class QuartzConfiguration {

    @Autowired(required = false)
    JobListener[] jobListeners;

    @Bean
    public QuartzJobFactory jobFactory(AutowireCapableBeanFactory beanFactory) {
        return new QuartzJobFactory(beanFactory);
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(QuartzJobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        if (jobListeners != null && jobListeners.length > 0)
            schedulerFactoryBean.setGlobalJobListeners(jobListeners);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactory) {
        return schedulerFactory.getScheduler();
    }

    @Bean
    public QuartzManager quartzManager(Scheduler scheduler) {
        return new QuartzManager(scheduler);
    }

}
