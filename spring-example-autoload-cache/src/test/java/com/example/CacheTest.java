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
    public void testCache() {
        User user = userService.getUserById(1L);
        user = userService.getUserById(1L);
    }

    @Test
    public void testDeleteCache() {
        userService.deleteUserById(1L);
    }

    @Test
    public void testDeleteCache2() {
        User user = new User(1L, "conanli", "123456");
        userService.addUser(user);
    }

}
