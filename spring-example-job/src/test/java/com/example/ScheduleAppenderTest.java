package com.example;

import com.example.schedule.Schedule;
import com.example.schedule.ScheduleAppender;
import com.example.schedule.ScheduleHolder;
import com.example.schedule.ScheduleLog4jAppenderRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleAppenderTest {

    public static void main(String[] args) {
        ScheduleAppender scheduleAppender = new ScheduleAppender();
        ScheduleLog4jAppenderRegister.regist(scheduleAppender);

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
