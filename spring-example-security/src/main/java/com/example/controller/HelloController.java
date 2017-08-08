package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!!!";
    }

    @RequestMapping("/user")
    public String user() {
        return "Any User Information";
    }

    @RequestMapping("/role")
    public String role() {
        return "Any Role Information";
    }

}
