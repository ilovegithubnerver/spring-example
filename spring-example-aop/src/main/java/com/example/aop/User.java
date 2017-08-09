package com.example.aop;

public class User {

    public String sayHello(String lastName, String firstName) {
        if (lastName == null || firstName == null)
            throw new NullPointerException();
        String str = String.format("hello %s %s", lastName, firstName);
        System.out.println(str);
        return str;
    }

    public String hi() {
        String str = String.format("hi...");
        System.out.println(str);
        return str;
    }
}
