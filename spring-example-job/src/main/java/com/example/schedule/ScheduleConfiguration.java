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
    public ScheduleListener scheduleListener() {
        return new ScheduleListener();
    }

    @Bean
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore, ScheduleLoader scheduleLoader) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, scheduleStore, scheduleLoader);
        scheduleManager.init();
        return scheduleManager;
    }
}
