package com.example.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggedJob implements Job {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        logger.info("开始执行Job: name={}, group={}, data={}", jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), jobDetail.getJobDataMap().getWrappedMap());
        executeJob(context);
        logger.info("Job执行结束: name={}, group={}, data={}", jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), jobDetail.getJobDataMap().getWrappedMap());
    }

    public abstract void executeJob(JobExecutionContext context) throws JobExecutionException;

}
