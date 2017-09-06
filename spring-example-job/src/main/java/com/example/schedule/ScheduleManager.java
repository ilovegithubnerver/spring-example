package com.example.schedule;

import com.example.quartz.QuartzManager;
import org.quartz.Job;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleManager {

    private QuartzManager quartzManager;
    private ScheduleStore scheduleStore;

    public ScheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore) {
        this.quartzManager = quartzManager;
        this.scheduleStore = scheduleStore;
    }

    public void init() {
        List<Schedule> schedules = scheduleStore.listSchedule();
        for (Schedule schedule : schedules) {
            addJob(schedule);
        }
        quartzManager.startScheduler();
    }

    public void start(String jobName) {
        Schedule schedule = scheduleStore.getSchedule(jobName);
        addJob(schedule);
    }

    public void stop(String jobName) {
        Schedule schedule = scheduleStore.getSchedule(jobName);
        removeJob(schedule);
    }

    public void restart(String jobName) {
        stop(jobName);
        start(jobName);
    }

    public void run(String jobName) {
        Schedule schedule = scheduleStore.getSchedule(jobName);
        runJob(schedule);
    }

    private void addJob(Schedule schedule) {
        if (!"1".equals(schedule.getIsEnable()))
            return;

        Class<? extends Job> jobClass = getJobClass(schedule.getJobClassName(), schedule.getJarPath());
        if (jobClass == null)
            return;
        Map<String, Object> jobData = getJobData(schedule.getJobParams());

        if (Schedule.TRIGGER_SIMPLE.equals(schedule.getTriggerType())) {
            quartzManager.addJob(schedule.getJobName(), schedule.getJobGroup(), jobClass, jobData, schedule.getTriggerInterval(), schedule.getTriggerRepeat());
        } else if (Schedule.TRIGGER_CRON.equals(schedule.getTriggerType())) {
            quartzManager.addJob(schedule.getJobName(), schedule.getJobGroup(), jobClass, jobData, schedule.getTriggerCron());
        }
    }

    private void removeJob(Schedule schedule) {
        quartzManager.removeJob(schedule.getJobName(), schedule.getJobGroup());
    }

    private void runJob(Schedule schedule) {
        String jobGroup = "ONCE";
        Class<? extends Job> jobClass = getJobClass(schedule.getJobClassName(), schedule.getJarPath());
        if (jobClass == null)
            return;
        Map<String, Object> jobData = getJobData(schedule.getJobParams());

        if (quartzManager.hasJob(schedule.getJobName(), jobGroup)) {
            quartzManager.removeJob(schedule.getJobName(), jobGroup);
        }
        quartzManager.addJob(schedule.getJobName(), jobGroup, jobClass, jobData);
        quartzManager.triggerJob(schedule.getJobName(), schedule.getJobGroup());
    }

    private Class<? extends Job> getJobClass(String jobClassName, String jarPath) {
        try {
            URL jar = new URL(jarPath);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jar}, getClass().getClassLoader(), null);
            return (Class<? extends Job>) classLoader.loadClass(jobClassName);
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, Object> getJobData(List<Schedule.Param> jobParams) {
        return jobParams.stream()
                .filter((p -> "1".equals(p.getIsEnable())))
                .collect(Collectors.toMap(p -> p.getParamKey(), p -> p.getParamValue()));
    }
}
