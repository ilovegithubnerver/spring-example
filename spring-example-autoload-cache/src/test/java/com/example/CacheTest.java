package com.example;

import com.example.cache.CacheAspect;
import com.example.config.CacheConfig;
import com.example.config.RedisConfig;
import com.example.model.User;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class, CacheConfig.class})
public class CacheTest {

    @Autowired
    UserService userService;
    @Autowired
    CacheAspect cacheAspect;

    @Test
    public void test() {
        User user = userService.getUserById(1L);
        user = userService.getUserById(1L);
        System.out.println(user);
    }

}
