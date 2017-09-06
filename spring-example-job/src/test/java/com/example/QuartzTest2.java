package com.example;

import com.example.quartz.QuartzConfiguration;
import com.example.quartz.QuartzManager;
import com.example.schedule.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.net.URLClassLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {QuartzConfiguration.class})
public class QuartzTest2 {

    @Autowired
    private QuartzManager quartzManager;

    @Test
    public void test() throws Exception {
        Schedule scheduleAAA = new Schedule();
        scheduleAAA.setJobName("aaa");
        scheduleAAA.setJobGroup("test");
        scheduleAAA.setJobClassName("com.example.MyJob");
        scheduleAAA.setTriggerInterval(1000L);
        scheduleAAA.setTriggerRepeat(0);
        scheduleAAA.setJarPath("job-example-aaa-0.0.1.jar");

        Schedule scheduleBBB = new Schedule();
        scheduleBBB.setJobName("bbb");
        scheduleBBB.setJobGroup("test");
        scheduleBBB.setJobClassName("com.example.MyJob");
        scheduleBBB.setTriggerInterval(1000L);
        scheduleBBB.setTriggerRepeat(0);
        scheduleBBB.setJarPath("job-example-bbb-0.0.1.jar");

        addJob(scheduleAAA);
        addJob(scheduleBBB);

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
            URL jar = getJar(jarPath);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jar}, getClass().getClassLoader(), null);
            return (Class<? extends Job>) classLoader.loadClass(jobClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private URL getJar(String jarName) {
        URL url = getClass().getClassLoader().getResource(jarName);
        if (url != null)
            return url;
        return null;
    }
}
