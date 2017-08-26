package com.example;

import com.example.quartz.QuartzConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {QuartzConfiguration.class})
public class QuartzTest {

    @Test
    public void test() {
        while (Thread.activeCount() > 0)
            Thread.yield();
    }

}
