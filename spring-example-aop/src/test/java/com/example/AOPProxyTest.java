package com.example;

import com.example.aop.AOPProxyConfig;
import com.example.aop.FilteredAdvisor;
import com.example.aop.User;
import com.example.aop.UserAroundAdvice;
import com.example.cglib.English;
import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
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

    @Test
    public void testMyProxy() {
        Advice aroundAdvice = new UserAroundAdvice();// 补充的内容

        User user = new User();// 目标

        ProxyFactoryBean proxyFactory = new ProxyFactoryBean();// 工厂类，用于生成代理类
        // proxyFactory.setInterfaces();// 目标类的接口
        proxyFactory.setTarget(user);// 目标对象
        proxyFactory.setProxyTargetClass(true);// true CGLib false JDK
        proxyFactory.addAdvice(aroundAdvice);// 设置补充的内容

        User proxy = (User) proxyFactory.getObject();// 生成代理类

        proxy.sayHello("conan", "li");// 执行
    }

    @Test
    public void testMyProxy2() {
        Advice aroundAdvice = new UserAroundAdvice();// 补充的内容

        User user = new User();// 目标

        ProxyFactoryBean proxyFactory = new ProxyFactoryBean();// 工厂类，用于生成代理类
        // proxyFactory.setInterfaces();// 目标类的接口
        proxyFactory.setTarget(user);// 目标对象
        proxyFactory.setProxyTargetClass(true);// true CGLib false JDK

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();// 切面，过滤方法
        pointcut.setExpression("execution(public * com.example.aop.User.sayHello(..))");// 只过滤sayHello
        FilteredAdvisor sayHelloAroundAdvior = new FilteredAdvisor(pointcut, aroundAdvice);// 设置补充的内容
        proxyFactory.addAdvisor(sayHelloAroundAdvior);// 关联切面

        User proxy = (User) proxyFactory.getObject();// 生成代理类
        proxy.sayHello("conan", "li");// 有输出补充的内容
        proxy.sayHello2("conan", "li");// 不输出补充的内容
    }
}
