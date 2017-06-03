package com.example.quartz;

import com.example.job.MyJob;
import org.quartz.Job;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScheduledJobBuilder {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ScheduledJobBuilder.applicationContext = applicationContext;
    }

    public static List<ScheduledJob> buildAll() {
        List<ScheduledJob> scheduledJobs = new ArrayList<>();

        scheduledJobs.add(new ScheduledJob("myJob", "test", MyJob.class, 3000L, 3));

        return scheduledJobs;
    }

    public static Class<? extends Job> getJobClass(String jobClassImpl) {
        Class<?> clazz = null;

        if (jobClassImpl.startsWith("com.example.")) {
            try {
                clazz = Class.forName(jobClassImpl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Object job = applicationContext.getBean(jobClassImpl);
                if (job != null)
                    clazz = job.getClass();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        List<Class<?>> clses = listAllSuper(clazz);
        for (Class<?> cls : clses) {
            if (Job.class == cls)
                return (Class<? extends Job>) clazz;
        }

        return null;
    }

    private static List<Class<?>> listAllSuper(Class<?> clazz) {
        Type t = clazz.getGenericSuperclass();
        Type[] ts = clazz.getGenericInterfaces();
        if (t == null && ts.length == 0)
            return new ArrayList<>(0);

        List<Class<?>> types = new ArrayList<>();

        if (t != null) {
            Class<?> cls = (Class<?>) t;
            types.add(cls);
            types.addAll(listAllSuper(cls));
        }

        for (Type type : ts) {
            Class<?> cls = (Class<?>) type;
            types.add(cls);
            types.addAll(listAllSuper(cls));
        }
        return types;
    }

}
