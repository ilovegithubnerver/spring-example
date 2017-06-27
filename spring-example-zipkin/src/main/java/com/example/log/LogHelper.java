package com.example.log;

import org.slf4j.MDC;

import java.lang.management.ManagementFactory;

public class LogHelper {

    public static void apply() {
        MDC.put("PID", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

    public static void clean() {
        MDC.remove("PID");
    }

}
