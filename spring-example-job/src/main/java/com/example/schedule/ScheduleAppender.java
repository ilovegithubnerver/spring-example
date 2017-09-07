package com.example.schedule;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleAppender extends AppenderSkeleton {

    private ThreadLocal<FileWriter> fileWriterHolder = new ThreadLocal<>();
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

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
