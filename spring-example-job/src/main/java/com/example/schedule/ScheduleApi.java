package com.example.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleApi {

    @Autowired
    ScheduleManager scheduleManager;

    @PostMapping("/start")
    public String start(String jobName) {
        scheduleManager.start(jobName);
        return "OK";
    }

    @PostMapping("/stop")
    public String stop(String jobName) {
        scheduleManager.stop(jobName);
        return "OK";
    }

    @PostMapping("/restart")
    public String restart(String jobName) {
        scheduleManager.restart(jobName);
        return "OK";
    }

    @PostMapping("/run")
    public String run(String jobName) {
        scheduleManager.run(jobName);
        return "OK";
    }
}
