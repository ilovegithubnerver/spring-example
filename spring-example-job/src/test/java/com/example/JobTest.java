package com.example;

import com.example.quartz.JobDefinition;
import com.example.quartz.QuartzConfiguration;
import com.example.quartz.QuartzManager;
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
public class JobTest {

    @Autowired
    private QuartzManager quartzManager;

    @Test
    public void test() throws Exception {
        Class<? extends Job> aaa = getJobClass("job-example-aaa-0.0.1.jar", "com.example.MyJob");
        Class<? extends Job> bbb = getJobClass("job-example-bbb-0.0.1.jar", "com.example.MyJob");

        JobDefinition aaaJobDefinition = new JobDefinition("aaa", "test", aaa, 1000L, 0);
        JobDefinition bbbJobDefinition = new JobDefinition("bbb", "test", bbb, 1000L, 0);

        addJob(aaaJobDefinition);
        addJob(bbbJobDefinition);

        while (Thread.activeCount() > 0)
            Thread.yield();
    }

    private void addJob(JobDefinition jobDefinition) {
        if (JobDefinition.TRIGGER_SIMPLE.equals(jobDefinition.getTriggerType()))
            quartzManager.addJob(jobDefinition.getJobName(), jobDefinition.getJobGroup(), jobDefinition.getJobClass(), jobDefinition.getJobData(), jobDefinition.getTriggerInterval(), jobDefinition.getTriggerRepeat());
        else if (JobDefinition.TRIGGER_CRON.equals(jobDefinition.getTriggerType()))
            quartzManager.addJob(jobDefinition.getJobName(), jobDefinition.getJobGroup(), jobDefinition.getJobClass(), jobDefinition.getJobData(), jobDefinition.getTriggerCron());
    }

    private Class<? extends Job> getJobClass(String jarName, String className) throws Exception {
        URL jar = getJar(jarName);
        URLClassLoader loader = new URLClassLoader(new URL[]{jar}, getClass().getClassLoader(), null);
        Class<? extends Job> jobClass = (Class<? extends Job>) loader.loadClass(className);
        return jobClass;
    }

    private URL getJar(String jarName) {
        URL url = getClass().getClassLoader().getResource(jarName);
        if (url != null)
            return url;
        return null;
    }
}
