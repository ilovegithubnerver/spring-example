package com.example.schedule;

import com.example.quartz.QuartzManager;
import com.example.schedule.appender.Log4jAppender;
import com.example.schedule.appender.LogbackAppender;
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
        Log4jAppender.regist(scheduleAppender);
        LogbackAppender.regist(scheduleAppender);
        return scheduleAppender;
    }

    @Bean
    public ScheduleListener scheduleListener(ScheduleAppender scheduleAppender, ScheduleStore scheduleStore) {
        ScheduleListener scheduleListener = new ScheduleListener();
        scheduleListener.setScheduleAppender(scheduleAppender);
        scheduleListener.setScheduleStore(scheduleStore);
        return scheduleListener;
    }

    @Bean
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore, ScheduleLoader scheduleLoader) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, scheduleStore, scheduleLoader);
        scheduleManager.init();
        return scheduleManager;
    }
}
