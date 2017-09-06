package com.example.schedule;

import com.example.jooq_generated.tables.Job;
import com.example.jooq_generated.tables.JobParam;
import com.example.jooq_generated.tables.records.JobParamRecord;
import com.example.jooq_generated.tables.records.JobRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liweitang on 2017/9/4.
 */
public class ScheduleStore {

    private DSLContext dsl;

    public ScheduleStore(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Schedule> listSchedule() {
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
                    schedule.setTriggerInterval(job.getTriggerInterval() != null ? job.getTriggerInterval().longValue() : 0);
                    schedule.setTriggerRepeat(job.getTriggerRepeat());
                    schedule.setJarPath(job.getJarPath());
                    schedule.setIsEnable(job.getIsEnable());
                    return schedule;
                }).collect(Collectors.toList());
    }

    public Schedule getSchedule(String jobName) {
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
        schedule.setTriggerInterval(job.getTriggerInterval() != null ? job.getTriggerInterval().longValue() : 0);
        schedule.setTriggerRepeat(job.getTriggerRepeat());
        schedule.setJarPath(job.getJarPath());
        schedule.setIsEnable(job.getIsEnable());
        return schedule;
    }
}
