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
    public ScheduleLog4jAppender scheduleLog4jAppender() {
        return new ScheduleLog4jAppender();
    }

    @Bean
    public ScheduleLogbackAppender scheduleLogbackAppender() {
        return new ScheduleLogbackAppender();
    }

    @Bean
    public ScheduleListener scheduleListener(ScheduleLog4jAppender scheduleLog4jAppender, ScheduleLogbackAppender scheduleLogbackAppender) {
        ScheduleListener scheduleListener = new ScheduleListener();
        scheduleListener.setScheduleLog4jAppender(scheduleLog4jAppender);
        scheduleListener.setScheduleLogbackAppender(scheduleLogbackAppender);
        return scheduleListener;
    }

    @Bean
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore, ScheduleLoader scheduleLoader) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, scheduleStore, scheduleLoader);
        scheduleManager.init();
        return scheduleManager;
    }
}
