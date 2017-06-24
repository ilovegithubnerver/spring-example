package com.example.log;

import org.slf4j.MDC;

import java.lang.management.ManagementFactory;

public class LogHelper {

    public static void apply() {
        MDC.put("PID", ManagementFactory.getRuntimeMXBean().getName());
    }

    public static void cleanup() {
        MDC.remove("PID");
    }

}
