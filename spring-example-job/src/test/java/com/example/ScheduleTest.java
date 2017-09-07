package com.example;

import com.example.job.MyJob;
import com.example.jooq.JooqConfiguration;
import com.example.quartz.QuartzConfiguration;
import com.example.quartz.QuartzManager;
import com.example.schedule.Schedule;
import com.example.schedule.ScheduleConfiguration;
import com.example.schedule.ScheduleManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JooqConfiguration.class, QuartzConfiguration.class, ScheduleConfiguration.class})
public class ScheduleTest {

    @Autowired
    private ScheduleManager scheduleManager;

    @Test
    public void test() {
        Schedule schedule = new Schedule();
        schedule.setJobName("aaa");
        schedule.setJobGroup("test");
        schedule.setJobClassName("com.example.MyJob");
        schedule.setTriggerType(Schedule.TRIGGER_SIMPLE);
        schedule.setTriggerInterval(1000L);
        schedule.setTriggerRepeat(0);
        schedule.setJarPath("d:/conanli-repository/spring-example/spring-example-job/src/main/resources/job-example-aaa-0.0.1.jar");
        schedule.setIsEnable("1");

        scheduleManager.start(schedule);

        while (Thread.activeCount() > 0)
            Thread.yield();
    }
}
