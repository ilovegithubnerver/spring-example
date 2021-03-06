package com.example.config;

import com.example.cache.CacheAspect;
import com.jarvis.cache.CacheHandler;
import com.jarvis.cache.ICacheManager;
import com.jarvis.cache.aop.aspectj.AspectjAopInterceptor;
import com.jarvis.cache.redis.ShardedJedisCacheManager;
import com.jarvis.cache.script.SpringELParser;
import com.jarvis.cache.serializer.HessianSerializer;
import com.jarvis.cache.to.AutoLoadConfig;
import org.springframework.context.annotation.*;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Collections;

@ComponentScan(basePackages = {"com.example.service"})
@EnableAspectJAutoProxy
@Configuration
@Import({CacheAspect.class})
public class CacheConfig {

    @Bean
    public ICacheManager cacheManager(ShardedJedisPool shardedJedisPool) {
        AutoLoadConfig config = new AutoLoadConfig();
        config.setThreadCnt(10);
        config.setMaxElement(20000);
        config.setPrintSlowLog(true);
        config.setSlowLoadTime(500);
        config.setSortType(1);
        config.setCheckFromCacheBeforeLoad(true);
        config.setAutoLoadPeriod(50);
        config.setFunctions(Collections.singletonMap("isEmpty", "com.jarvis.cache.CacheUtil"));
        HessianSerializer serializer = new HessianSerializer();

        ShardedJedisCacheManager cacheManager = new ShardedJedisCacheManager(config, serializer);
        cacheManager.setShardedJedisPool(shardedJedisPool);
        return cacheManager;
    }

    @Bean(destroyMethod="destroy")
    public CacheHandler cacheHandler(ICacheManager cacheManager) {
        SpringELParser scriptParser = new SpringELParser();
        CacheHandler cacheHandler = new CacheHandler(cacheManager, scriptParser);
        return cacheHandler;
    }

    @Bean
    public AspectjAopInterceptor cacheInterceptor(CacheHandler cacheHandler) {
        AspectjAopInterceptor cacheInterceptor = new AspectjAopInterceptor(cacheHandler);
        return cacheInterceptor;
    }

}
