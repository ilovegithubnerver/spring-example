package com.example.schedule;

import com.example.schedule.model.ScheduleQueryParams;
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
    public String start(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return "任务名不能为空";
        }
        scheduleManager.start(params.getJobName());
        return "OK";
    }

    @PostMapping("/stop")
    public String stop(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return "任务名不能为空";
        }
        scheduleManager.stop(params.getJobName());
        return "OK";
    }

    @PostMapping("/restart")
    public String restart(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return "任务名不能为空";
        }
        scheduleManager.restart(params.getJobName());
        return "OK";
    }

    @PostMapping("/run")
    public String run(@RequestBody ScheduleQueryParams params) {
        if (params.getJobName() == null) {
            return "任务名不能为空";
        }
        scheduleManager.run(params.getJobName());
        return "OK";
    }

    @PostMapping("/list")
    public List<Schedule> list() {
        List<Schedule> schedules = scheduleManager.list();
        return schedules;
    }

    @PostMapping("/listLog")
    public List<ScheduleLog> listLog(@RequestBody ScheduleQueryParams params) {
        List<ScheduleLog> scheduleLogs = scheduleManager.listLog(params.getJobName());
        return scheduleLogs;
    }
}
