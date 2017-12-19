package com.example.hello;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by liweitang on 2017/12/19.
 */
@EnableJms
public class HelloListener {

    @JmsListener(destination = "hello", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println(tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
