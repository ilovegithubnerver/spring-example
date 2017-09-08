package com.example.schedule;

import com.example.quartz.QuartzManager;
import org.quartz.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleManager {

    private QuartzManager quartzManager;
    private ScheduleStore scheduleStore;
    private ScheduleLoader scheduleLoader;

    public ScheduleManager(QuartzManager quartzManager, ScheduleStore scheduleStore, ScheduleLoader scheduleLoader) {
        this.quartzManager = quartzManager;
        this.scheduleStore = scheduleStore;
        this.scheduleLoader = scheduleLoader;
    }

    public void init() {
        List<Schedule> schedules = scheduleStore.list();
        for (Schedule schedule : schedules) {
            start(schedule);
        }
        quartzManager.startScheduler();
    }

    public void start(String jobName) {
        Schedule schedule = scheduleStore.get(jobName);
        start(schedule);
    }

    public void stop(String jobName) {
        Schedule schedule = scheduleStore.get(jobName);
        stop(schedule);
    }

    public void restart(String jobName) {
        Schedule schedule = scheduleStore.get(jobName);
        restart(schedule);
    }

    public void run(String jobName) {
        Schedule schedule = scheduleStore.get(jobName);
        run(schedule);
    }

    public void delete(String jobName) {
        stop(jobName);
        scheduleStore.delete(jobName);
    }

    public List<Schedule> list() {
        List<Schedule> schedules = scheduleStore.list();

        for (Schedule schedule : schedules) {
            List<? extends Trigger> triggers = quartzManager.listTrigger(JobKey.jobKey(schedule.getJobName(), schedule.getJobGroup()));
            for (Trigger trigger : triggers) {
                if (trigger.getNextFireTime() == null)
                    continue;
                schedule.setIsRunning("1");
                if (schedule.getNextFireTime() == null || schedule.getNextFireTime().compareTo(trigger.getNextFireTime()) == 1) {
                    schedule.setNextFireTime(trigger.getNextFireTime());
                }
            }
        }

        return schedules;
    }

    public List<ScheduleLog> listLog(String jobName) {
        return scheduleStore.listLog(jobName);
    }

    public void start(Schedule schedule) {
        if (!"1".equals(schedule.getIsEnable()))
            return;

        Class<? extends Job> jobClass = scheduleLoader.loadClass(schedule.getJobName(), schedule.getJobClassName(), schedule.getJarPath());
        if (jobClass == null)
            return;
        Map<String, Object> jobData = getJobData(schedule.getJobParams());
        jobData.put("jarPath", schedule.getJarPath());

        if (Schedule.TRIGGER_SIMPLE.equals(schedule.getTriggerType())) {
            quartzManager.addJob(schedule.getJobName(), schedule.getJobGroup(), jobClass, jobData, schedule.getTriggerInterval(), schedule.getTriggerRepeat());
        } else if (Schedule.TRIGGER_CRON.equals(schedule.getTriggerType())) {
            quartzManager.addJob(schedule.getJobName(), schedule.getJobGroup(), jobClass, jobData, schedule.getTriggerCron());
        }
    }

    public void stop(Schedule schedule) {
        quartzManager.removeJob(schedule.getJobName(), schedule.getJobGroup());
    }

    public void restart(Schedule schedule) {
        stop(schedule);
        start(schedule);
    }

    public void run(Schedule schedule) {
        String jobGroup = "ONCE";
        Class<? extends Job> jobClass = scheduleLoader.loadClass(schedule.getJobName(), schedule.getJobClassName(), schedule.getJarPath());
        if (jobClass == null)
            return;
        Map<String, Object> jobData = getJobData(schedule.getJobParams());
        jobData.put("jarPath", schedule.getJarPath());

        if (quartzManager.hasJob(schedule.getJobName(), jobGroup)) {
            quartzManager.removeJob(schedule.getJobName(), jobGroup);
        }
        quartzManager.addJob(schedule.getJobName(), jobGroup, jobClass, jobData);
        quartzManager.triggerJob(schedule.getJobName(), jobGroup);
    }

    private Map<String, Object> getJobData(List<Schedule.Param> jobParams) {
        if (jobParams == null || jobParams.size() == 0)
            return new HashMap<>();
        return jobParams.stream()
                .filter((p -> "1".equals(p.getIsEnable())))
                .collect(Collectors.toMap(p -> p.getParamKey(), p -> p.getParamValue()));
    }
}
