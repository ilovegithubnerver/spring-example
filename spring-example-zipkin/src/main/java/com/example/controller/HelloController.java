package com.example.controller;

import brave.Span;
import brave.Tracer;
import com.example.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Tracer tracer;

    @RequestMapping("/hello")
    public String hello() {
        logger.info("Hello, I am Jack!");
        tracer.currentSpan().tag("hello", "jack");
        String res = restTemplate.getForObject("http://localhost:8080/hello2", String.class);
        return "Hello, I am Jack! " + res;
    }

    @RequestMapping("/hello2")
    public String hello2() {
        logger.info("Hello, I am Lucy!");
        tracer.currentSpan().tag("hello2", "lucy");
        String res = restTemplate.getForObject("http://localhost:8080/hello3", String.class);
        return "Hello, I am Lucy! " + res;
    }

    @RequestMapping("/hello3")
    public String hello3() {
        logger.info("Hello, I am Fuck!");
        tracer.currentSpan().tag("hello3", "fuck");
        Span span = tracer.nextSpan().name("hello4").start();
        span.tag("hello4", "none");
        span.finish();
        return "Hello, I am Fuck!";
    }

    @RequestMapping("/hello5")
    public String hello5() {
        logger.info("Hello, I am ...!");
        return "Hello, I am ...! ";
    }

    @RequestMapping("/hello6")
    public String hello6(String name) {
        logger.info("some error " + name);
        throw new NullPointerException("Hell...");
    }

    @RequestMapping("/hello7")
    public String hello7(@RequestBody User params) {
        logger.info("some error " + params.getName());
        throw new NullPointerException("Hell...");
    }

}
