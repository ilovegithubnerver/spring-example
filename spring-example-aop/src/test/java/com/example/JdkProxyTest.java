package com.example;

import com.example.jdk.Chinese;
import com.example.jdk.JdkProxyConfig;
import com.example.jdk.PersonAroundAdvice;
import com.example.jdk.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdkProxyConfig.class})
public class JdkProxyTest {

    @Autowired
    Person person;

    @Test
    public void test() {
        System.out.println(String.format("object: %s, class: %s", person, person.getClass()));
        person.sayHello("conanli");
    }

    @Test
    public void testMyProxy() {
        Chinese target = new Chinese();
        Class<?> targetClazz = target.getClass();
        InvocationHandler handler = new PersonAroundAdvice(target);
        Person person = (Person) Proxy.newProxyInstance(targetClazz.getClassLoader(), targetClazz.getInterfaces(), handler);
        person.sayHello("conanli");
    }
}
