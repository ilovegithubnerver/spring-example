package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        if (applicationContext == null || beanName == null)
            return null;
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanType) {
        if (applicationContext == null || beanType == null)
            return null;
        return applicationContext.getBean(beanType);
    }

}
