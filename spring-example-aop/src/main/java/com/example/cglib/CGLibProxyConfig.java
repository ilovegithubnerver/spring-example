package com.example.cglib;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@Configurable
@Import({English.class, CGLibProxyAspect.class})
public class CGLibProxyConfig {
}
