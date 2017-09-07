package com.example.schedule;

import com.example.quartz.QuartzManager;
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
        ScheduleLog4jAppenderRegister.regist(scheduleAppender);
        ScheduleLogbackAppenderRegister.regist(scheduleAppender);
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
