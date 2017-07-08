package com.example.log;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
public class LogApplicationListener implements GenericApplicationListener {

    private static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 20;
    private static Class<?>[] EVENT_TYPES = {ContextStartedEvent.class, ContextStoppedEvent.class, ContextClosedEvent.class};
    private static Class<?>[] SOURCE_TYPES = {ApplicationContext.class};

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return isAssignableFrom(resolvableType.getRawClass(), EVENT_TYPES);
    }

    @Override
    public boolean supportsSourceType(Class<?> type) {
        return isAssignableFrom(type, SOURCE_TYPES);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextStartedEvent) {
            LogHelper.apply();
        } else if (applicationEvent instanceof ContextStoppedEvent || applicationEvent instanceof ContextClosedEvent) {
            LogHelper.clean();
        }

    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    private boolean isAssignableFrom(Class<?> type, Class<?>... supportedTypes) {
        if (type != null) {
            for (Class<?> supportedType : supportedTypes) {
                if (supportedType.isAssignableFrom(type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
