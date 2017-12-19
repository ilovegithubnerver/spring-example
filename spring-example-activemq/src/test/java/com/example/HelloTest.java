package com.example;

import com.example.hello.HelloConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HelloConfiguration.class})
public class HelloTest {

    @Qualifier("queueJmsTemplate")
    @Autowired
    JmsTemplate queueJmsTemplate;

    @Test
    public void test() throws Exception{
        queueJmsTemplate.convertAndSend("test", "HelloWorld!!!");
        Thread.sleep(1000 * 60);
    }
}
