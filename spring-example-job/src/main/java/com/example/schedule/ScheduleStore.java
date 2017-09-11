package com.example.schedule;

import com.example.jooq_generated.tables.Job;
import com.example.jooq_generated.tables.JobHistory;
import com.example.jooq_generated.tables.JobParam;
import com.example.jooq_generated.tables.records.JobHistoryRecord;
import com.example.jooq_generated.tables.records.JobParamRecord;
import com.example.jooq_generated.tables.records.JobRecord;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleStore {

    private DSLContext dsl;

    public ScheduleStore(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void save(String jobName, String jobGroup, String jobClassName, String triggerType, Long triggerInterval, Integer triggerRepeat, String triggerCron, String isEnable) {
        Job JOB = Job.JOB;

        dsl.insertInto(JOB)
                .columns(JOB.JOB_NAME, JOB.JOB_GROUP, JOB.JOB_CLASS_NAME, JOB.TRIGGER_TYPE, JOB.TRIGGER_INTERVAL, JOB.TRIGGER_REPEAT, JOB.TRIGGER_CRON, JOB.IS_ENABLE)
                .values(jobName, jobGroup, jobClassName, triggerType, triggerInterval != null ? triggerInterval.intValue() : 0, triggerRepeat, triggerCron, isEnable)
                .onDuplicateKeyUpdate()
                .set(JOB.JOB_GROUP, jobGroup)
                .set(JOB.JOB_CLASS_NAME, jobClassName)
                .set(JOB.TRIGGER_TYPE, triggerType)
                .set(JOB.TRIGGER_INTERVAL, triggerInterval != null ? triggerInterval.intValue() : 0)
                .set(JOB.TRIGGER_REPEAT, triggerRepeat)
                .set(JOB.TRIGGER_CRON, triggerCron)
                .set(JOB.IS_ENABLE, isEnable)
                .execute();
    }

    public void delete(String jobName) {
        Job JOB = Job.JOB;
        JobParam JOB_PARAM = JobParam.JOB_PARAM;
        JobHistory JOB_HISTORY = JobHistory.JOB_HISTORY;

        dsl.deleteFrom(JOB)
                .where(JOB.JOB_NAME.eq(jobName))
                .execute();
        dsl.deleteFrom(JOB_PARAM)
                .where(JOB_PARAM.JOB_NAME.eq(jobName));
        dsl.deleteFrom(JOB_HISTORY)
                .where(JOB_HISTORY.JOB_NAME.eq(jobName))
                .execute();
    }

    public List<Schedule> list() {
        Job JOB = Job.JOB;
        JobParam JOB_PARAM = JobParam.JOB_PARAM;

        List<JobRecord> jobs = dsl.select()
                .from(JOB)
                .fetchInto(JOB);
        List<JobParamRecord> jobParams = dsl.select()
                .from(JOB_PARAM)
                .fetchInto(JOB_PARAM);

        return jobs.stream()
                .map(job -> {
                    Schedule schedule = new Schedule();
                    schedule.setJobName(job.getJobName());
                    schedule.setJobGroup(job.getJobGroup());
                    schedule.setJobClassName(job.getJobClassName());
                    schedule.setJobParams(jobParams.stream()
                            .filter(jobParam -> jobParam.getJobName().equals(job.getJobName()))
                            .map(p -> {
                                Schedule.Param scheduleParam = new Schedule.Param();
                                scheduleParam.setJobName(p.getJobName());
                                scheduleParam.setParamKey(p.getParamKey());
                                scheduleParam.setParamValue(p.getParamValue());
                                scheduleParam.setIsEnable(p.getIsEnable());
                                return scheduleParam;
                            })
                            .collect(Collectors.toList()));
                    schedule.setTriggerType(job.getTriggerType());
                    schedule.setTriggerCron(job.getTriggerCron());
                    schedule.setTriggerInterval(job.getTriggerInterval() != null ? job.getTriggerInterval().longValue() : 0);
                    schedule.setTriggerRepeat(job.getTriggerRepeat());
                    schedule.setJarPath(job.getJarPath());
                    schedule.setIsEnable(job.getIsEnable());
                    return schedule;
                }).collect(Collectors.toList());
    }

    public List<String> listName() {
        Job JOB = Job.JOB;

        return dsl.select(JOB.JOB_NAME)
                .from(JOB)
                .fetch(JOB.JOB_NAME);
    }

    public Schedule get(String jobName) {
        Job JOB = Job.JOB;
        JobParam JOB_PARAM = JobParam.JOB_PARAM;

        JobRecord job = dsl.select()
                .from(JOB)
                .where(JOB.JOB_NAME.eq(jobName))
                .fetchOneInto(JOB);
        List<JobParamRecord> jobParams = dsl.select()
                .from(JOB_PARAM)
                .where(JOB_PARAM.JOB_NAME.eq(jobName))
                .fetchInto(JOB_PARAM);

        Schedule schedule = new Schedule();
        schedule.setJobName(job.getJobName());
        schedule.setJobGroup(job.getJobGroup());
        schedule.setJobClassName(job.getJobClassName());
        schedule.setJobParams(jobParams.stream()
                .filter(jobParam -> jobParam.getJobName().equals(job.getJobName()))
                .map(p -> {
                    Schedule.Param scheduleParam = new Schedule.Param();
                    scheduleParam.setJobName(p.getJobName());
                    scheduleParam.setParamKey(p.getParamKey());
                    scheduleParam.setParamValue(p.getParamValue());
                    scheduleParam.setIsEnable(p.getIsEnable());
                    return scheduleParam;
                })
                .collect(Collectors.toList()));
        schedule.setTriggerType(job.getTriggerType());
        schedule.setTriggerCron(job.getTriggerCron());
        schedule.setTriggerInterval(job.getTriggerInterval() != null ? job.getTriggerInterval().longValue() : 0);
        schedule.setTriggerRepeat(job.getTriggerRepeat());
        schedule.setJarPath(job.getJarPath());
        schedule.setIsEnable(job.getIsEnable());
        return schedule;
    }

    public void saveLog(String jobName, String jobLog) {
        JobHistory JOB_HISTORY = JobHistory.JOB_HISTORY;

        dsl.insertInto(JOB_HISTORY)
                .columns(JOB_HISTORY.JOB_NAME, JOB_HISTORY.JOB_LOG)
                .values(jobName, jobLog)
                .execute();
    }

    public List<ScheduleLog> listLog(String jobName) {
        JobHistory JOB_HISTORY = JobHistory.JOB_HISTORY;

        List<JobHistoryRecord> jobHistories = new ArrayList<>();

        if (jobName != null && jobName.trim().length() > 0) {
            jobHistories = dsl.select(JOB_HISTORY.JOB_NAME, JOB_HISTORY.JOB_LOG, JOB_HISTORY.CREATE_DATE)
                    .from(JOB_HISTORY)
                    .where(JOB_HISTORY.JOB_NAME.eq(jobName))
                    .orderBy(JOB_HISTORY.CREATE_DATE.desc())
                    .limit(10)
                    .fetchInto(JOB_HISTORY);
        } else {
            jobHistories = dsl.select(JOB_HISTORY.JOB_NAME, JOB_HISTORY.JOB_LOG, JOB_HISTORY.CREATE_DATE)
                    .from(JOB_HISTORY)
                    .orderBy(JOB_HISTORY.CREATE_DATE.desc())
                    .limit(10)
                    .fetchInto(JOB_HISTORY);
        }

        return jobHistories.stream().map(jobHistory -> {
            ScheduleLog scheduleLog = new ScheduleLog();
            scheduleLog.setJobName(jobHistory.getJobName());
            scheduleLog.setJobLog(jobHistory.getJobLog());
            scheduleLog.setCreateDate(jobHistory.getCreateDate());
            return scheduleLog;
        }).collect(Collectors.toList());
    }
}
