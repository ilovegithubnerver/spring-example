package com.example.schedule;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class ScheduleLogbackAppenderRegister extends AppenderBase<ILoggingEvent> {

    private boolean inited = false;
    private ScheduleAppender scheduleAppender;
    private PatternLayout layout;

    public static void regist(ScheduleAppender scheduleAppender) {
        new ScheduleLogbackAppenderRegister(scheduleAppender);
    }

    private ScheduleLogbackAppenderRegister(ScheduleAppender scheduleAppender) {
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory == null || !(loggerFactory instanceof LoggerContext))
            return;

        LoggerContext loggerContext = (LoggerContext) loggerFactory;
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        if (rootLogger == null)
            return;

        ConsoleAppender<ILoggingEvent> consoleAppender = null;
        Iterator<Appender<ILoggingEvent>> appenderIterator = rootLogger.iteratorForAppenders();
        while (appenderIterator.hasNext()) {
            Appender appender = appenderIterator.next();
            if (appender instanceof ConsoleAppender) {
                consoleAppender = (ConsoleAppender) appender;
                break;
            }
        }

        if (consoleAppender == null || !(consoleAppender.getEncoder() instanceof PatternLayoutEncoder))
            return;

        PatternLayout layout = new PatternLayout();
        layout.setContext(loggerContext);
        layout.setPattern(((PatternLayoutEncoder) consoleAppender.getEncoder()).getPattern());
        layout.start();

        setName("scheduleLogbackAppender");
        setContext(loggerContext);
        start();
        rootLogger.addAppender(this);

        this.layout = layout;
        this.scheduleAppender = scheduleAppender;
        this.inited = true;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!inited)
            return;
        scheduleAppender.append(layout.doLayout(event));
    }
}
