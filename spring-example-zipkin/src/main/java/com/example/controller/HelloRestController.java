package com.example.controller;

import brave.Tracer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloRestController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Tracer tracer;

    @RequestMapping("/hello")
    public String hello() {
        logger.info("Hello, I am Jack!");
        // tracer.addTag("hello", "jack");
        String res = restTemplate.getForObject("http://localhost:8080/hello2", String.class);
        return "Hello, I am Jack! " + res;
    }

    @RequestMapping("/hello2")
    public String hello2() {
        logger.info("Hello, I am Lucy!");
        // tracer.addTag("hello2", "lucy");
        String res = restTemplate.getForObject("http://localhost:8080/hello3", String.class);
        return "Hello, I am Lucy! " + res;
    }

    @RequestMapping("/hello3")
    public String hello3() {
        logger.info("Hello, I am Fuck!");
        // tracer.addTag("hello3", "fuck");
        // Span span = tracer.createSpan("hello4");
        // tracer.addTag("hello4", "none");
        // tracer.close(span);
        return "Hello, I am Fuck!";
    }

}
