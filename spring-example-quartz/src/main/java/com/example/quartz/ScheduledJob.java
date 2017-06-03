package com.example.quartz;

import org.quartz.Job;

import java.util.Map;

public class ScheduledJob {

    public static final String TRIGGER_SIMPLE = "SIMPLE";
    public static final String TRIGGER_CRON = "CRON";

    private String jobName;// 任务名
    private String jobGroup;// 任务组
    private Class<? extends Job> jobClass;// 任务实现类
    private Map<String, Object> jobData;// 任务数据
    private String triggerType;// 触发器类型
    private String triggerCron;// 触发器定时表示式
    private Long triggerInterval;// 触发器间隔（毫秒）
    private Integer triggerRepeat;// 触发器重复执行次数

    public ScheduledJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Long triggerInterval, Integer triggerRepeat) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClass = jobClass;
        this.triggerType = TRIGGER_SIMPLE;
        this.triggerInterval = triggerInterval;
        this.triggerRepeat = triggerRepeat;
    }

    public ScheduledJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData, Long triggerInterval, Integer triggerRepeat) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClass = jobClass;
        this.jobData = jobData;
        this.triggerType = TRIGGER_SIMPLE;
        this.triggerInterval = triggerInterval;
        this.triggerRepeat = triggerRepeat;
    }

    public ScheduledJob(String jobName, String jobGroup, Class<? extends Job> jobClass, String triggerCron) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClass = jobClass;
        this.triggerType = TRIGGER_CRON;
        this.triggerCron = triggerCron;
    }

    public ScheduledJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData, String triggerCron) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobClass = jobClass;
        this.jobData = jobData;
        this.triggerType = TRIGGER_CRON;
        this.triggerCron = triggerCron;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }

    public Map<String, Object> getJobData() {
        return jobData;
    }

    public void setJobData(Map<String, Object> jobData) {
        this.jobData = jobData;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerCron() {
        return triggerCron;
    }

    public void setTriggerCron(String triggerCron) {
        this.triggerCron = triggerCron;
    }

    public Long getTriggerInterval() {
        return triggerInterval;
    }

    public void setTriggerInterval(Long triggerInterval) {
        this.triggerInterval = triggerInterval;
    }

    public Integer getTriggerRepeat() {
        return triggerRepeat;
    }

    public void setTriggerRepeat(Integer triggerRepeat) {
        this.triggerRepeat = triggerRepeat;
    }
}
