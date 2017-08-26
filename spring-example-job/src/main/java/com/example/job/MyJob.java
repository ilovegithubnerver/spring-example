package com.example.job;

import com.example.quartz.LoggedJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyJob extends LoggedJob {

    @Override
    public void executeJob(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String x = jobDataMap.getString("x");
        String y = jobDataMap.getString("y");
        logger.info("Job={}, x={}, y={}", jobDetail.getKey().getName(), x, y);
    }
}
