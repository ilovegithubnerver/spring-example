package com.example.schedule;

import java.util.Date;
import java.util.List;

public class Schedule {

    public static final String TRIGGER_SIMPLE = "SIMPLE";
    public static final String TRIGGER_CRON = "CRON";

    private String jobName;// 任务名
    private String jobGroup;// 任务组
    private String jobClassName;// 实现类名
    private List<Param> jobParams;// 任务数据
    private String triggerType;// 触发器类型
    private String triggerCron;// 触发器定时表示式
    private Long triggerInterval;// 触发器间隔（毫秒）
    private Integer triggerRepeat;// 触发器重复执行次数
    private String jarPath;// JAR包文件名，包括目录
    private String isEnable;// 0 禁用 1 启动
    private String isRunning = "0";// 是否正在运行 0 不是 1 是
    private Date NextFireTime;// 下次运行时间

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

    public List<Param> getJobParams() {
        return jobParams;
    }

    public void setJobParams(List<Param> jobParams) {
        this.jobParams = jobParams;
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

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(String isRunning) {
        this.isRunning = isRunning;
    }

    public Date getNextFireTime() {
        return NextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        NextFireTime = nextFireTime;
    }

    public static class Param {
        private String jobName;// 任务名
        private String paramKey;// 参数名
        private String paramValue;// 参数值
        private String isEnable;// 0 禁用 1 启动

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getParamKey() {
            return paramKey;
        }

        public void setParamKey(String paramKey) {
            this.paramKey = paramKey;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }

        public String getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(String isEnable) {
            this.isEnable = isEnable;
        }
    }
}
