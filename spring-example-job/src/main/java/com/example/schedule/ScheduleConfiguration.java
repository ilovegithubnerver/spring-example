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
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore jobDefinitionStore) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, jobDefinitionStore);
        scheduleManager.init();
        return scheduleManager;
    }
}
