package com.example.schedule.model;

public class ScheduleSaveParams {

    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String triggerType;
    private String triggerCron;
    private Long triggerInterval;
    private Integer triggerRepeat;
    private String isEnable;

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

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
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

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}
