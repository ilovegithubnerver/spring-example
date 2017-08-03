package com.example.cache;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.annotation.CacheDeleteTransactional;
import com.jarvis.cache.aop.aspectj.AspectjAopInterceptor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class CacheAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AspectjAopInterceptor aspectjAopInterceptor;

    @Around("execution(public * com.example.service.*Service.*(..)) && @annotation(cache)")
    public Object cache(ProceedingJoinPoint proceedingJoinPoint, Cache cache) throws Throwable {
        logger.debug("start do cache");
        return aspectjAopInterceptor.proceed(proceedingJoinPoint, cache);
    }

    @AfterReturning(value = "execution(public * com.example.service.*Service.*(..)) && @annotation(cacheDelete)", returning = "retVal")
    public void cacheDelete(JoinPoint aopProxyChain, CacheDelete cacheDelete, Object retVal) throws Throwable {
        logger.debug("start do cacheDelete");
        aspectjAopInterceptor.deleteCache(aopProxyChain, cacheDelete, retVal);
    }

    @Around("execution(public * com.example.service.*Service.*(..)) && @annotation(cacheDeleteTransactional)")
    public Object cacheDeleteTransactional(ProceedingJoinPoint proceedingJoinPoint, CacheDeleteTransactional cacheDeleteTransactional) throws Throwable {
        logger.debug("start do cacheDeleteTransactional");
        return aspectjAopInterceptor.deleteCacheTransactional(proceedingJoinPoint, cacheDeleteTransactional);
    }

}
