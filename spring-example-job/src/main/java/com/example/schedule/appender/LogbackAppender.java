package com.example.schedule.appender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.example.schedule.ScheduleAppender;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class LogbackAppender extends AppenderBase<ILoggingEvent> {

    private boolean inited = false;
    private ScheduleAppender scheduleAppender;
    private Layout layout;

    public static void regist(ScheduleAppender scheduleAppender) {
        new LogbackAppender(scheduleAppender);
    }

    private LogbackAppender(ScheduleAppender scheduleAppender) {
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

        if (consoleAppender == null)
            return;

        if (consoleAppender.getEncoder() instanceof LayoutWrappingEncoder) {
            TTLLLayout ttllLayout = new TTLLLayout();
            ttllLayout.setContext(loggerContext);
            ttllLayout.start();
            layout = ttllLayout;
        }

        if (consoleAppender.getEncoder() instanceof PatternLayoutEncoder) {
            PatternLayout patternLayout = new PatternLayout();
            patternLayout.setContext(loggerContext);
            patternLayout.setPattern(((PatternLayoutEncoder) consoleAppender.getEncoder()).getPattern());
            patternLayout.start();
            layout = patternLayout;
        }

        if (layout == null)
            return;

        setName("scheduleLogbackAppender");
        setContext(loggerContext);
        start();
        rootLogger.addAppender(this);

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
