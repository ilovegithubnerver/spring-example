package com.example.aop;

public class User {

    public String sayHello(String lastName, String firstName) {
        String str = String.format("hello %s %s", lastName, firstName);
        System.out.println(str);
        return str;
    }

    public String sayHello2(String lastName, String firstName) {
        String str = String.format("hello %s %s", lastName, firstName);
        System.out.println(str);
        return str;
    }

    public String sayHello3(String lastName, String firstName) {
        String str = String.format("hello %s %s", lastName, firstName);
        System.out.println(str);
        return str;
    }

    public String sayHello4(String lastName, String firstName) {
        String str = null;
        System.out.println(str.length());
        return str;
    }

    public String sayHello5(String lastName, String firstName) {
        String str = String.format("hello %s %s", lastName, firstName);
        System.out.println(str);
        return str;
    }
}
