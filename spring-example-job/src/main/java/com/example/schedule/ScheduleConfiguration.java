package com.example.schedule;

import com.example.quartz.QuartzManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;

public class ScheduleConfiguration {

    @Bean
    public ScheduleStore scheduleStore(DSLContext dsl) {
        return new ScheduleStore(dsl);
    }

    @Bean
    public ScheduleLoader scheduleLoader() {
        return new ScheduleLoader();
    }

    @Bean
    public ScheduleAppender scheduleAppender() {
        ScheduleAppender scheduleAppender = new ScheduleAppender();
        scheduleAppender.setName("scheduleAppender");
        scheduleAppender.setPath("d:");
        scheduleAppender.setThreshold(Level.DEBUG);
        scheduleAppender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS} %5.5p [%15.15t] %40.40l : %m%n"));
        scheduleAppender.activateOptions();
        Logger.getRootLogger().addAppender(scheduleAppender);
        return scheduleAppender;
    }

    @Bean
    public ScheduleListener scheduleListener(ScheduleAppender scheduleAppender) {
        ScheduleListener scheduleListener = new ScheduleListener();
        scheduleListener.setScheduleAppender(scheduleAppender);
        return scheduleListener;
    }

    @Bean
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore, ScheduleLoader scheduleLoader) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, scheduleStore, scheduleLoader);
        scheduleManager.init();
        return scheduleManager;
    }
}
