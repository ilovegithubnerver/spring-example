package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogTest2 {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger logger = LoggerFactory.getLogger(getClass());
                // System.setProperty("JOB_NAME", "job-aaa");
                MDC.put("JOB_NAME", "job-aaa");
                for (int i = 0; i < 3; i++) {
                    logger.info("job-aaa {}", i);
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MDC.remove("JOB_NAME");
            }
        }, "t-aaa").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger logger = LoggerFactory.getLogger(getClass());
                // System.setProperty("JOB_NAME", "job-bbb");
                MDC.put("JOB_NAME", "job-bbb");
                for (int i = 0; i < 3; i++) {
                    logger.info("job-bbb {}", i);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MDC.remove("JOB_NAME");
            }
        }, "t-bbb").start();

        if (Thread.activeCount() > 1)
            Thread.yield();
    }
}
