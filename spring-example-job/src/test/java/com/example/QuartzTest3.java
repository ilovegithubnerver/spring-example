package com.example;

import com.example.job.MyJob;
import com.example.quartz.QuartzConfiguration;
import com.example.quartz.QuartzManager;
import com.example.schedule.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {QuartzConfiguration.class})
public class QuartzTest3 {

    @Autowired
    private QuartzManager quartzManager;

    @Test
    public void test() {
        Schedule schedule = new Schedule();
        schedule.setJobName("aaa");
        schedule.setJobGroup("test");
        schedule.setJobClassName("com.zhidian.cloud.analyze.job.Main");
        schedule.setTriggerType(Schedule.TRIGGER_SIMPLE);
        schedule.setTriggerInterval(1000L);
        schedule.setTriggerRepeat(0);
        schedule.setJarPath("file:///D:/zhidian-repository/zhidian-cloud-analyze/analyze-job-jar/analyze-job-order/target/analyze-job-order-0.0.1-jar-with-dependencies.jar");

        addJob(schedule);

        while (Thread.activeCount() > 0)
            Thread.yield();
    }

    private void addJob(Schedule schedule) {
        Class<? extends Job> jobClass = getJobClass(schedule.getJobClassName(), schedule.getJarPath());

        if (Schedule.TRIGGER_SIMPLE.equals(schedule.getTriggerType())) {
            quartzManager.addJob(schedule.getJobName(), schedule.getJobGroup(), jobClass, null, schedule.getTriggerInterval(), schedule.getTriggerRepeat());
        } else if (Schedule.TRIGGER_CRON.equals(schedule.getTriggerType())) {
            quartzManager.addJob(schedule.getJobName(), schedule.getJobGroup(), jobClass, null, schedule.getTriggerCron());
        }
    }

    private Class<? extends Job> getJobClass(String jobClassName, String jarPath) {
        try {
            URL jar = new URL(jarPath);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jar}, getClass().getClassLoader(), null);
            return (Class<? extends Job>) classLoader.loadClass(jobClassName);
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
