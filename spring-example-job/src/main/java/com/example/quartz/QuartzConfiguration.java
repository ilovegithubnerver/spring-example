package com.example.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

@ComponentScan(basePackages = "com.example.job")
public class QuartzConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ScheduledJobBuilder.setApplicationContext(applicationContext);
    }

    @Bean
    public QuartzJobFactory jobFactory() {
        return new QuartzJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(QuartzJobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
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

    @Bean
    public QuartzInitializer quartzInitializer(QuartzManager quartzManager) {
        QuartzInitializer quartzInitializer = new QuartzInitializer(quartzManager);

        List<ScheduledJob> scheduledJobs = ScheduledJobBuilder.buildAll();
        quartzInitializer.init(scheduledJobs);
        return quartzInitializer;
    }

}
