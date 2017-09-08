package com.example.schedule;


import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleAppender {

    private boolean writeFile = false;
    private boolean writeBuffer = true;
    private ThreadLocal<FileWriter> fileWriterHolder = new ThreadLocal<>();
    private ThreadLocal<StringBuilder> stringBufferHolder = new ThreadLocal<>();
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    public void append(String line) {
        Schedule schedule = ScheduleHolder.get();
        if (schedule == null || schedule.getJobName() == null || schedule.getJarPath() == null)
            return;
        if (writeFile)
            writeFile(schedule, line);
        if (writeBuffer)
            writeBuffer(schedule, line);
    }

    public String getLog() {
        if (!writeBuffer || stringBufferHolder.get() == null)
            return "";
        return stringBufferHolder.get().toString();
    }

    public void close() {
        if (writeFile)
            closeFile();
        if (writeBuffer)
            closeBuffer();
    }

    protected void writeFile(Schedule schedule, String line) {
        try {
            FileWriter fileWriter = fileWriterHolder.get();
            if (fileWriter == null) {
                String fileName = String.format("%s-%s.log",
                        schedule.getJarPath().substring(0, schedule.getJarPath().lastIndexOf(".jar")),
                        df.format(new Date()));
                fileWriter = new FileWriter(fileName, true);
                fileWriterHolder.set(fileWriter);
            }
            fileWriter.write(line);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void closeFile() {
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

    protected void writeBuffer(Schedule schedule, String line) {
        StringBuilder stringBuffer = stringBufferHolder.get();
        if (stringBuffer == null) {
            stringBuffer = new StringBuilder();
            stringBufferHolder.set(stringBuffer);
        }
        stringBuffer.append(line);
    }

    protected void closeBuffer() {
        StringBuilder stringBuffer = stringBufferHolder.get();
        if (stringBuffer == null)
            return;
        stringBuffer.setLength(0);
        stringBufferHolder.set(stringBuffer);
    }
}
