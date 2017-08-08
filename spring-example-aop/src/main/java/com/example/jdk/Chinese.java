package com.example.jdk;

public class Chinese implements Person {

    @Override
    public String sayHello(String name) {
        return String.format("Hello %s, I am AOP", name);
    }
}
