package com.example.schedule;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleAppender extends AppenderSkeleton {

    private ThreadLocal<FileWriter> fileWriterHolder = new ThreadLocal<>();
    private String path;
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    protected void append(LoggingEvent event) {
        if (ScheduleHolder.getJobName() == null)
            return;
        FileWriter fileWriter = fileWriterHolder.get();
        if (fileWriter == null) {
            String fileName = String.format("%s-%s.log", ScheduleHolder.getJobName(), df.format(new Date()));
            try {
                fileWriter = new FileWriter(path + File.separator + fileName, true);
                fileWriterHolder.set(fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileWriter.write(layout.format(event));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateOptions() {
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
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

    public void setPath(String path) {
        this.path = path;
    }
}
