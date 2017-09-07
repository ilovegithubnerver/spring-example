package com.example;

import com.example.schedule.Schedule;
import com.example.schedule.ScheduleAppender;
import com.example.schedule.ScheduleHolder;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleAppenderTest {

    public static void main(String[] args) {
        ScheduleAppender appender = new ScheduleAppender();
        appender.setName("scheduleAppender");
        appender.setThreshold(Level.DEBUG);
        appender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS} %5.5p [%15.15t] %40.40l : %m%n"));
        appender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(appender);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Schedule schedule = new Schedule();
                schedule.setJobName("aaa");
                schedule.setJobGroup("test");
                schedule.setJarPath("d:/conanli-repository/spring-example/spring-example-job/src/main/resources/job-example-aaa-0.0.1.jar");
                ScheduleHolder.set(schedule);

                Logger logger = LoggerFactory.getLogger(getClass());
                for (int i = 0; i < 3; i++) {
                    logger.info("job-aaa {}", i);
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ScheduleHolder.set(null);
            }
        }, "t-aaa").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Schedule schedule = new Schedule();
                schedule.setJobName("bbb");
                schedule.setJobGroup("test");
                schedule.setJarPath("d:/conanli-repository/spring-example/spring-example-job/src/main/resources/job-example-bbb-0.0.1.jar");
                ScheduleHolder.set(schedule);

                Logger logger = LoggerFactory.getLogger(getClass());
                for (int i = 0; i < 3; i++) {
                    logger.info("job-bbb {}", i);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ScheduleHolder.set(null);
            }
        }, "t-bbb").start();

        if (Thread.activeCount() > 1)
            Thread.yield();
    }
}
