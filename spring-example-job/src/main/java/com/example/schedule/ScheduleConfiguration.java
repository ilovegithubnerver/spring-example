package com.example.schedule;

import com.example.quartz.QuartzManager;
import org.apache.log4j.*;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;

import java.util.Enumeration;

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
        ConsoleAppender consoleAppender = null;

        Enumeration<Appender> appenderEnumeration = Logger.getRootLogger().getAllAppenders();
        while (appenderEnumeration.hasMoreElements()) {
            Appender appender = appenderEnumeration.nextElement();
            if (appender instanceof ConsoleAppender) {
                consoleAppender = (ConsoleAppender) appender;
                break;
            }
        }

        ScheduleLog4jAppender scheduleLog4jAppender = new ScheduleLog4jAppender();
        scheduleLog4jAppender.setName("scheduleLog4jAppender");
        scheduleLog4jAppender.setThreshold(consoleAppender != null ? consoleAppender.getThreshold() : Level.INFO);
        scheduleLog4jAppender.setLayout(consoleAppender != null ? consoleAppender.getLayout() : new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS} %5.5p [%15.15t] %40.40l : %m%n"));
        scheduleLog4jAppender.activateOptions();
        Logger.getRootLogger().addAppender(scheduleLog4jAppender);
        return scheduleLog4jAppender;
    }

    @Bean
    public ScheduleListener scheduleListener(ScheduleLog4jAppender scheduleLog4jAppender) {
        ScheduleListener scheduleListener = new ScheduleListener();
        scheduleListener.setScheduleLog4jAppender(scheduleLog4jAppender);
        return scheduleListener;
    }

    @Bean
    public ScheduleManager scheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore, ScheduleLoader scheduleLoader) {
        ScheduleManager scheduleManager = new ScheduleManager(quartzManager, scheduleStore, scheduleLoader);
        scheduleManager.init();
        return scheduleManager;
    }
}
