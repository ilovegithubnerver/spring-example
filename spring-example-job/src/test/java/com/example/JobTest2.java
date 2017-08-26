package com.example;

import com.example.quartz.QuartzManager;
import org.junit.Test;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;

public class JobTest2 {

    @Test
    public void test() throws Exception {
        ClassLoader parentLoader = this.getClass().getClassLoader();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        QuartzManager quartzManager = new QuartzManager(scheduler);

        Class<? extends Job> aaa = getJobClass("job-example-aaa-0.0.1.jar", "com.example.MyJob", parentLoader);
        Class<? extends Job> bbb = getJobClass("job-example-bbb-0.0.1.jar", "com.example.MyJob", parentLoader);

        // 每1000毫秒执行一次，重复执行3次，共执行4次
        quartzManager.addJob("aaa", "test", aaa, Collections.singletonMap("x", "1"), 1000L, 3);
        quartzManager.addJob("bbb", "test", bbb, Collections.singletonMap("x", "1"), 1000L, 3);
        quartzManager.startScheduler();

        while (Thread.activeCount() > 0)
            Thread.yield();
    }

    private Class<? extends Job> getJobClass(String jarName, String className, ClassLoader parent) throws Exception {
        URL jar = getJar(jarName);
        URLClassLoader loader = new URLClassLoader(new URL[]{jar}, parent, null);
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
