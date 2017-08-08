package com.example.cglib;

public class English {

    public String sayHello(String name) {
        String str = String.format("Hello %s, I am AOP", name);
        System.out.println(str);
        return str;
    }
}
