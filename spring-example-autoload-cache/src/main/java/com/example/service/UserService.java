package com.example.service;

import com.example.model.User;
import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.annotation.CacheDeleteKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Cache(expire = 1800, key = "'USER_ID_'+#args[0]", autoload = true, requestTimeout = 1800L)
    public User getUserById(Long userId) {
        logger.info("select * from user where id={}", userId);
        User user = new User(userId, "conanli", "123456");
        return user;
    }

    @CacheDelete({
            @CacheDeleteKey("'USER_ID_'+#args[0].id")
    })
    public Boolean addUser(User user) {
        logger.info("insert into user(id, account, password)", user.getId(), user.getAccount(), user.getPassword());
        return true;
    }

    @CacheDelete({
            @CacheDeleteKey("'USER_ID_'+#args[0]")
    })
    public Boolean deleteUserById(Long userId) {
        logger.info("delete from user where id={}", userId);
        return true;
    }

}
