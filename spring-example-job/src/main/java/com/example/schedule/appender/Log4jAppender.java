package com.example.schedule.appender;

import com.example.schedule.ScheduleAppender;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Enumeration;

public class Log4jAppender extends AppenderSkeleton {

    private boolean inited = false;
    private ScheduleAppender scheduleAppender;

    public static void regist(ScheduleAppender scheduleAppender) {
        new Log4jAppender(scheduleAppender);
    }

    private Log4jAppender(ScheduleAppender scheduleAppender) {
        ConsoleAppender consoleAppender = null;
        Enumeration<Appender> appenderEnumeration = Logger.getRootLogger().getAllAppenders();
        while (appenderEnumeration.hasMoreElements()) {
            Appender appender = appenderEnumeration.nextElement();
            if (appender instanceof ConsoleAppender) {
                consoleAppender = (ConsoleAppender) appender;
                break;
            }
        }

        if (consoleAppender == null || !(consoleAppender.getLayout() instanceof PatternLayout))
            return;

        setName("scheduleLog4jAppender");
        setThreshold(consoleAppender.getThreshold());
        setLayout(consoleAppender.getLayout());
        activateOptions();
        Logger.getRootLogger().addAppender(this);

        this.scheduleAppender = scheduleAppender;
        this.inited = true;
    }

    @Override
    protected void append(LoggingEvent event) {
        if (!inited)
            return;
        scheduleAppender.append(getLayout().format(event));
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    @Override
    public void close() {
        if (!inited)
            return;
        scheduleAppender.close();
    }
}
