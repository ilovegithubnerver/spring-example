package com.example;

import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration("src/main/resources")
public class AsyncTest {

    private static Logger logger = Logger.getGlobal();

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        long start = System.currentTimeMillis();

        userService.sayHello();
        userService.sayHello2();

        long stop = System.currentTimeMillis();
        logger.info(String.format("time: %d", stop - start));
        Thread.sleep(5000);
    }

    @Test
    public void test2() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> future1 = userService.getUser();
        Future<List<String>> future2 = userService.listUser();

        String user = future1.get();
        List<String> users = future2.get();

        logger.info(String.format("user: %s", user));
        logger.info(String.format("users: %s", Arrays.toString(users.toArray())));


        long stop = System.currentTimeMillis();
        logger.info(String.format("time: %d", stop - start));
        Thread.sleep(3000);
    }

}
