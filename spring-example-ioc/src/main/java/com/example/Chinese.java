package com.example;

public class Chinese {

    public String sayHello(String name) {
        String str = String.format("Hello %s!", name);
        System.out.println(str);
        return str;
    }

}
