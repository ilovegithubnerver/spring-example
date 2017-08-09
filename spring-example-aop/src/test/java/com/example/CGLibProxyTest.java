package com.example;

import com.example.cglib.CGLibProxyConfig;
import com.example.cglib.English;
import com.example.cglib.EnglishAroundAdvice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CGLibProxyConfig.class})
public class CGLibProxyTest {

    @Autowired
    English person;

    @Test
    public void test() {
        System.out.println(String.format("object: %s, class: %s", person, person.getClass()));
        person.sayHello("conanli");
    }

    @Test
    public void testMyProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(English.class);
        enhancer.setCallback(new EnglishAroundAdvice());
        English person = (English) enhancer.create();
        person.sayHello("conanli");
    }
}
