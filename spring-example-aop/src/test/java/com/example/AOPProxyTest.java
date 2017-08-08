package com.example;

import com.example.aop.AOPProxyConfig;
import com.example.aop.User;
import com.example.cglib.English;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AOPProxyConfig.class})
public class AOPProxyTest {

    @Autowired
    User user;

    @Test
    public void testBefore() {
        user.sayHello("conan", "li");
    }

    @Test
    public void testAfter() {
        user.sayHello2("conan", "li");
    }

    @Test
    public void testAfterReturning() {
        user.sayHello3("conan", "li");
    }

    @Test
    public void testAfterThrowing() {
        user.sayHello4("conan", "li");
    }

    @Test
    public void testAround() {
        user.sayHello5("conan", "li");
    }
}
