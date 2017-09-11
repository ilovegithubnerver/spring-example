package com.example.schedule;

import com.example.schedule.model.ScheduleQueryParams;
import com.example.schedule.model.ScheduleSaveParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleApi {

    @Autowired
    ScheduleManager scheduleManager;

    @PostMapping("/start")
    public Boolean start(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return false;
        }
        scheduleManager.start(params.getJobName());
        return true;
    }

    @PostMapping("/stop")
    public Boolean stop(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return false;
        }
        scheduleManager.stop(params.getJobName());
        return true;
    }

    @PostMapping("/restart")
    public Boolean restart(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return false;
        }
        scheduleManager.restart(params.getJobName());
        return true;
    }

    @PostMapping("/run")
    public Boolean run(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return false;
        }
        scheduleManager.run(params.getJobName());
        return true;
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody ScheduleSaveParams params) {
        if (params.getJobName() == null) {
            return false;
        }
        scheduleManager.save(params.getJobName(), params.getJobGroup(), params.getJobClassName(), params.getTriggerType(), params.getTriggerInterval(), params.getTriggerRepeat(), params.getTriggerCron(), params.getIsEnable());
        return true;
    }

    @PostMapping("/delete")
    public Boolean delete(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return false;
        }
        scheduleManager.delete(params.getJobName());
        return true;
    }

    @PostMapping("/list")
    public List<Schedule> list() {
        List<Schedule> schedules = scheduleManager.list();
        return schedules;
    }

    @PostMapping("/listName")
    public List<String> listName() {
        List<String> jobNames = scheduleManager.listName();
        return jobNames;
    }

    @PostMapping("/listLog")
    public List<ScheduleLog> listLog(@RequestBody ScheduleQueryParams params) {
        List<ScheduleLog> scheduleLogs = scheduleManager.listLog(params.getJobName());
        return scheduleLogs;
    }
}
