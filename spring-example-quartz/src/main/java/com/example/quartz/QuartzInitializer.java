package com.example.quartz;

import java.util.List;

public class QuartzInitializer {

    private QuartzManager quartzManager;

    public QuartzInitializer(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    public void init(List<ScheduledJob> scheduledJobs) {
        for (ScheduledJob scheduledJob : scheduledJobs) {
            if (ScheduledJob.TRIGGER_SIMPLE.equals(scheduledJob.getTriggerType()))
                quartzManager.addJob(scheduledJob.getJobName(), scheduledJob.getJobGroup(), scheduledJob.getJobClass(), scheduledJob.getJobData(), scheduledJob.getTriggerInterval(), scheduledJob.getTriggerRepeat());
            else if (ScheduledJob.TRIGGER_CRON.equals(scheduledJob.getTriggerType()))
                quartzManager.addJob(scheduledJob.getJobName(), scheduledJob.getJobGroup(), scheduledJob.getJobClass(), scheduledJob.getJobData(), scheduledJob.getTriggerCron());
        }
        quartzManager.startScheduler();
    }

}
