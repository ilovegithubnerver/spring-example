package com.example.log;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;

public class LogApplicationListener implements GenericApplicationListener {
    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
