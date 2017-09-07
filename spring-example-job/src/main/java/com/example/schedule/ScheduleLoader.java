package com.example.schedule;

import org.quartz.Job;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ScheduleLoader {

    private ConcurrentHashMap<String, LinkedBlockingQueue<URLClassLoader>> classLoaders = new ConcurrentHashMap<>();

    public Class<? extends Job> loadClass(String jobName, String jobClassName, String jarPath) {
        try {
            URL jar = new URL("file://" + (jarPath.startsWith("/") ? jarPath : "/" + jarPath));
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jar}, getClass().getClassLoader(), null);
            LinkedBlockingQueue<URLClassLoader> classLoaderQueue = classLoaders.get(jobName);
            if (classLoaderQueue == null) {
                classLoaderQueue = new LinkedBlockingQueue<>(3);
                classLoaders.put(jobName, classLoaderQueue);
            }
            while (!classLoaderQueue.offer(classLoader)) {
                classLoaderQueue.poll().close();
            }
            return (Class<? extends Job>) classLoader.loadClass(jobClassName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
