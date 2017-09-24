package com.example;

import org.junit.Test;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class JavaBeanTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ChineseConfiguration.class);
        context.addApplicationListener(new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                System.out.println("refresh...");
            }
        });
        context.refresh();

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
        context2.setParent(context);
        context2.register(Canada.class);
        context2.refresh();

        System.out.println(context2.getBean(Chinese.class));
        System.out.println(context2.getBean(English.class));
        System.out.println(context2.getBean(Canada.class));
    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ChineseConfiguration.class);
        context.refresh();

        System.out.println(context.getBeanFactory().createBean(Canada.class));
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ChineseConfiguration.class);
        context.refresh();

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
        context2.setEnvironment(context.getEnvironment());
        context2.getBeanFactory().setParentBeanFactory(context.getBeanFactory());

        context2.register(Canada.class);
        context2.refresh();

        System.out.println(context2.getBean(Chinese.class));
        System.out.println(context2.getBean(English.class));
        System.out.println(context2.getBean(Canada.class));
    }

}
