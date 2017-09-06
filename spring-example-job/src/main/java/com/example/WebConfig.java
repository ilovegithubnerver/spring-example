package com.example;

import com.example.jooq.JooqConfiguration;
import com.example.quartz.QuartzConfiguration;
import com.example.schedule.ScheduleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@Import({JooqConfiguration.class, QuartzConfiguration.class, ScheduleConfiguration.class})
@ComponentScan(basePackages = "com.example")
public class WebConfig {
}
