package com.example.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyJob implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String x = jobDataMap.getString("x");
        String y = jobDataMap.getString("y");
        logger.info("Job={}, x={}, y={}", jobDetail.getKey().getName(), x, y);
        sayHello();
    }

    public void sayHello() {
        System.out.println("Hello World !");
    }
}
