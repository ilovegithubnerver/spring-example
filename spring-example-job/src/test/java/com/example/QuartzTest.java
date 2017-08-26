package com.example;

import com.example.job.MyJob;
import com.example.quartz.JobDefinition;
import com.example.quartz.QuartzConfiguration;
import com.example.quartz.QuartzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {QuartzConfiguration.class})
public class QuartzTest {

    @Autowired
    private QuartzManager quartzManager;

    @Test
    public void test() {
        JobDefinition jobDefinition = new JobDefinition("myJob", "test", MyJob.class, 1000L, 0);

        addJob(jobDefinition);

        while (Thread.activeCount() > 0)
            Thread.yield();
    }

    public void addJob(JobDefinition jobDefinition) {
        if (JobDefinition.TRIGGER_SIMPLE.equals(jobDefinition.getTriggerType()))
            quartzManager.addJob(jobDefinition.getJobName(), jobDefinition.getJobGroup(), jobDefinition.getJobClass(), jobDefinition.getJobData(), jobDefinition.getTriggerInterval(), jobDefinition.getTriggerRepeat());
        else if (JobDefinition.TRIGGER_CRON.equals(jobDefinition.getTriggerType()))
            quartzManager.addJob(jobDefinition.getJobName(), jobDefinition.getJobGroup(), jobDefinition.getJobClass(), jobDefinition.getJobData(), jobDefinition.getTriggerCron());
    }

}
