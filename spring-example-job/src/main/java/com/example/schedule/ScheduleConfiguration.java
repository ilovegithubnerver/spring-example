package com.example.schedule;

import com.example.quartz.QuartzManager;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by liweitang on 2017/9/4.
 */
public class ScheduleConfiguration {

    @Bean
    public ScheduleStore jobDefinitionStore(DSLContext dsl) {
        return new ScheduleStore(dsl);
    }

    @Bean
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore jobDefinitionStore) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, jobDefinitionStore);
        scheduleManager.init();
        return scheduleManager;
    }
}
