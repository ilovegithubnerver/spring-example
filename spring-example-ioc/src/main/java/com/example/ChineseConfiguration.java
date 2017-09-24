package com.example;

import org.springframework.context.annotation.Bean;

/**
 * Created by liweitang on 2017/9/23.
 */
public class ChineseConfiguration {

    @Bean
    public Chinese chinese() {
        return new Chinese();
    }

}
