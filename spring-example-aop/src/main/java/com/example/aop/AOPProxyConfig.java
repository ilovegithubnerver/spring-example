package com.example.aop;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@Configurable
@Import({User.class, AOPProxyAspect.class})
public class AOPProxyConfig {
}
