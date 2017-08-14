package com.example;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class XmlBeanTest {

    @Test
    public void test() {
        // 根据Xml配置文件创建Resource资源对象，该对象中包含了BeanDefinition的信息
        ClassPathResource resource = new ClassPathResource("spring.xml");
        // 创建DefaultListableBeanFactory
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        // 创建XmlBeanDefinitionReader读取器，用于载入BeanDefinition。之所以需要BeanFactory作为参数，是因为会将读取的信息回调配置给factory
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        // XmlBeanDefinitionReader执行载入BeanDefinition的方法，最后会完成Bean的载入和注册。完成后Bean就成功的放置到IOC容器当中，以后我们就可以从中取得Bean来使用
        reader.loadBeanDefinitions(resource);

        Chinese chinese = factory.getBean(Chinese.class);
        chinese.sayHello("conanli");
    }

    @Test
    public void testXmlBean() {
        // 根据Xml配置文件创建Resource资源对象，该对象中包含了BeanDefinition的信息
        ClassPathResource resource = new ClassPathResource("spring.xml");
        XmlBeanFactory factory = new XmlBeanFactory(resource);

        Chinese chinese = factory.getBean(Chinese.class);
        chinese.sayHello("conanli");
    }

    @Test
    public void testApplicationContext() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("spring.xml");
        Chinese chinese = factory.getBean(Chinese.class);
        chinese.sayHello("conanli");
    }

}
