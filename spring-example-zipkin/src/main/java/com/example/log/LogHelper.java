package com.example.log;

import brave.Tracing;
import brave.propagation.TraceContext;
import com.example.util.ApplicationContextHelper;
import org.slf4j.MDC;

import java.lang.management.ManagementFactory;

public class LogHelper {

    private static boolean inited = false;
    private static Tracing tracing;

    public static void apply() {
        if (!inited) {
            init();
        }
        MDC.put("PID", ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

        TraceContext traceContext = tracing.currentTraceContext().get();
        if (traceContext != null) {
            MDC.put("TRACE_ID", String.valueOf(traceContext.traceId()));
        }
    }

    public static void clean() {
        MDC.remove("PID");
        MDC.remove("TRACE_ID");
    }

    public static void init() {
        tracing = ApplicationContextHelper.getBean(Tracing.class);
        // inited = true;
    }

}
