package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        // 生成多机连接信息列表
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("127.0.0.1", 6379));

        // 生成连接池配置信息
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);// 对象最大空闲时间
        config.setMaxTotal(30);// 最大活动的对象个数
        config.setMaxWaitMillis(3 * 1000);// 获取对象时最大等待时间
        config.setTestOnBorrow(true);

        // 在应用初始化的时候生成连接池
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shards);
        return shardedJedisPool;
    }

}
