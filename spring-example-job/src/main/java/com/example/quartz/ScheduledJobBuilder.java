package com.example.quartz;

import com.example.job.MyJob;
import org.springframework.context.ApplicationContext;

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

}
