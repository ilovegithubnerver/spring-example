package com.example;

public class English {

    public String sayHello(String name) {
        String str = String.format("Hello %s!", name);
        System.out.println(str);
        return str;
    }

}
