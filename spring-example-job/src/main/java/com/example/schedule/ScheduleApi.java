package com.example.schedule;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liweitang on 2017/9/5.
 */
@RestController
@RequestMapping("/apis/schedule")
public class ScheduleApi {

    @PostMapping("/say")
    public String say() {
        return "hello world";
    }
}
