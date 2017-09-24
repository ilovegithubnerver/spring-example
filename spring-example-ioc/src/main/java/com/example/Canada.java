package com.example;

import org.springframework.context.annotation.Import;

/**
 * Created by Letorn on 2017/9/24.
 */
@Import(EnglishConfiguration.class)
public class Canada {

    public String sayHello(String name) {
        String str = String.format("Hello %s!", name);
        System.out.println(str);
        return str;
    }
}
