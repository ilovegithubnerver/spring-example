package com.example.schedule;

public class ScheduleHolder {

    private static ThreadLocal<String> jobNameHolder = new ThreadLocal();

    public static void setJobName(String jobName) {
        jobNameHolder.set(jobName);
    }

    public static String getJobName() {
        return jobNameHolder.get();
    }
}
