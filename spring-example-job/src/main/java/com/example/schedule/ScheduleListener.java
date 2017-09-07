package com.example.schedule;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleListener implements JobListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ScheduleLog4jAppender scheduleLog4jAppender;
    private ScheduleLogbackAppender scheduleLogbackAppender;

    @Override
    public String getName() {
        return "scheduleListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        Schedule schedule = new Schedule();
        schedule.setJobName(jobDetail.getKey().getName());
        schedule.setJobGroup(jobDetail.getKey().getGroup());
        schedule.setJarPath(jobDetail.getJobDataMap().getString("jarPath"));
        ScheduleHolder.set(schedule);
        logger.info("Schedule 开始: name={}, data={}", jobDetail.getKey().getName(), jobDetail.getJobDataMap().getWrappedMap());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobDetail jobDetail = context.getJobDetail();
        logger.info("Schedule 结束: name={}, data={}", jobDetail.getKey().getName(), jobDetail.getJobDataMap().getWrappedMap());
        if (scheduleLog4jAppender != null)
            scheduleLog4jAppender.close();
        if (scheduleLogbackAppender != null)
            scheduleLogbackAppender.close();
        ScheduleHolder.set(null);
    }

    public void setScheduleLog4jAppender(ScheduleLog4jAppender scheduleLog4jAppender) {
        this.scheduleLog4jAppender = scheduleLog4jAppender;
    }

    public void setScheduleLogbackAppender(ScheduleLogbackAppender scheduleLogbackAppender) {
        this.scheduleLogbackAppender = scheduleLogbackAppender;
    }
}
