package com.example;

import com.example.job.MyJob;
import com.example.jooq.JooqConfiguration;
import com.example.quartz.QuartzConfiguration;
import com.example.quartz.QuartzManager;
import com.example.schedule.ScheduleConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JooqConfiguration.class, QuartzConfiguration.class, ScheduleConfiguration.class})
public class ScheduleTest {

    @Autowired
    private QuartzManager quartzManager;

    @Test
    public void test() {
        quartzManager.addJob("myjob", "test", MyJob.class, null, 1000L, 0);

        while (Thread.activeCount() > 0)
            Thread.yield();
    }
}
