package com.example.schedule;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class ScheduleLog4jAppender extends AppenderSkeleton {

    private ThreadLocal<FileWriter> fileWriterHolder = new ThreadLocal<>();
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    public ScheduleLog4jAppender() {
        ConsoleAppender consoleAppender = null;
        Enumeration<Appender> appenderEnumeration = Logger.getRootLogger().getAllAppenders();
        while (appenderEnumeration.hasMoreElements()) {
            Appender appender = appenderEnumeration.nextElement();
            if (appender instanceof ConsoleAppender) {
                consoleAppender = (ConsoleAppender) appender;
                break;
            }
        }

        Priority priority = Level.INFO;
        String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} %5.5p [%15.15t] %40.40l : %m%n";
        if (consoleAppender != null) {
            priority = consoleAppender.getThreshold();
            if (consoleAppender.getLayout() instanceof PatternLayout) {
                pattern = ((PatternLayout) consoleAppender.getLayout()).getConversionPattern();
            }
        }

        setName("scheduleLog4jAppender");
        setThreshold(priority);
        setLayout(new PatternLayout(pattern));
        activateOptions();
        Logger.getRootLogger().addAppender(this);
    }

    @Override
    protected void append(LoggingEvent event) {
        Schedule schedule = ScheduleHolder.get();
        if (schedule == null || schedule.getJobName() == null || schedule.getJarPath() == null)
            return;
        try {
            FileWriter fileWriter = fileWriterHolder.get();
            if (fileWriter == null) {
                String fileName = String.format("%s-%s.log",
                        schedule.getJarPath().substring(0, schedule.getJarPath().lastIndexOf(".jar")),
                        df.format(new Date()));
                fileWriter = new FileWriter(fileName, true);
                fileWriterHolder.set(fileWriter);
            }
            fileWriter.write(layout.format(event));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    @Override
    public void close() {
        FileWriter fileWriter = fileWriterHolder.get();
        if (fileWriter == null)
            return;
        try {
            fileWriter.close();
            fileWriterHolder.set(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
