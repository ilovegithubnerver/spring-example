package com.example.schedule;

import com.example.quartz.QuartzManager;
import org.quartz.Job;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ZhiDian on 2017/9/5.
 */
public class ScheduleManager {

    private QuartzManager quartzManager;
    private ScheduleStore jobDefinitionStore;

    public ScheduleManager(QuartzManager quartzManager, ScheduleStore jobDefinitionStore) {
        this.quartzManager = quartzManager;
        this.jobDefinitionStore = jobDefinitionStore;
    }

    public void init() {
        List<Schedule> schedules = jobDefinitionStore.listSchedule();
        for (Schedule schedule : schedules) {
            addJob(schedule);
        }
        quartzManager.startScheduler();
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
