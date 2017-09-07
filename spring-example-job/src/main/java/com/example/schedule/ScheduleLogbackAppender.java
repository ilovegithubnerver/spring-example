package com.example.schedule;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ScheduleLogbackAppender extends AppenderBase<ILoggingEvent> {

    private ThreadLocal<PatternLayoutEncoder> encoderHolder = new ThreadLocal<>();
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    private String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %5.5p [%15.15t] %40.40l : %m%n";
    private LoggerContext loggerContext;

    public ScheduleLogbackAppender() {
        ConsoleAppender<ILoggingEvent> consoleAppender = null;
        Logger rootLogger = null;

        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory != null && loggerFactory instanceof LoggerContext) {
            loggerContext = (LoggerContext) loggerFactory;
            rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
            Iterator<Appender<ILoggingEvent>> appenderIterator = rootLogger.iteratorForAppenders();
            if (appenderIterator.hasNext()) {
                Appender appender = appenderIterator.next();
                if (appender instanceof ConsoleAppender) {
                    consoleAppender = (ConsoleAppender) appender;
                }
            }
        }

        if (consoleAppender != null && consoleAppender.getEncoder() instanceof PatternLayoutEncoder) {
            pattern = ((PatternLayoutEncoder) consoleAppender.getEncoder()).getPattern();
        }

        if (rootLogger != null) {
            setName("scheduleLogbackAppender");
            setContext(loggerContext);
            start();
            rootLogger.addAppender(this);
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        Schedule schedule = ScheduleHolder.get();
        if (schedule == null || schedule.getJobName() == null || schedule.getJarPath() == null)
            return;
        try {
            PatternLayoutEncoder encoder = encoderHolder.get();
            if (encoder == null) {
                String fileName = String.format("%s-%s.log",
                        schedule.getJarPath().substring(0, schedule.getJarPath().lastIndexOf(".jar")),
                        df.format(new Date()));
                encoder = new PatternLayoutEncoder();
                encoder.setCharset(Charset.forName("UTF-8"));
                encoder.setPattern(pattern);
                encoder.setContext(loggerContext);
                encoder.init(new FileOutputStream(fileName, true));
                encoder.start();
                encoderHolder.set(encoder);
            }
            encoder.doEncode(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        PatternLayoutEncoder encoder = encoderHolder.get();
        if (encoder == null)
            return;
        try {
            encoder.close();
            encoderHolder.set(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
