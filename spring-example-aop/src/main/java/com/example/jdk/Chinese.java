package com.example.jdk;

public class Chinese implements Person {

    @Override
    public String sayHello(String name) {
        String str = String.format("Hello %s, I am AOP", name);
        System.out.println(str);
        return str;
    }
}
