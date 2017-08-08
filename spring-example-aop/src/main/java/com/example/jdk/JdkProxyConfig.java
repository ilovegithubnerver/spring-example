package com.example.jdk;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@Configurable
@Import({Chinese.class, JdkProxyAspect.class})
public class JdkProxyConfig {
}
