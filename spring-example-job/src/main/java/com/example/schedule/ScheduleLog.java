package com.example.schedule;

import java.util.Date;

/**
 * Created by liweitang on 2017/9/8.
 */
public class ScheduleLog {

    private String jobName;// 任务名
    private String jobLog;// 运行日志
    private Date createDate;// 创建时间

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobLog() {
        return jobLog;
    }

    public void setJobLog(String jobLog) {
        this.jobLog = jobLog;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
