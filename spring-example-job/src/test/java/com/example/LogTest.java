package com.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test() {
        System.setProperty("JOB_NAME", "myjob");
        logger.info("hello world, myjob");
        System.clearProperty("JOB_NAME");
    }
}
