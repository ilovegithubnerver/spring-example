package com.example.service;

import com.example.model.User;
import com.jarvis.cache.annotation.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Cache(expire = 1800, key = "'USER_ID'+#args[0]", autoload = true, requestTimeout = 1800L)
    public User getUserById(Long userId) {
        logger.info("connect database to get user");
        User user = new User(userId, "conanli", "123456");
        return user;
    }

}
