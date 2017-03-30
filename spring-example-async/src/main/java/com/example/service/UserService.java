package com.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@Service
public class UserService {

    private static Logger logger = Logger.getGlobal();

    @Async
    public void sayHello() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        logger.info("hello");
    }

    @Async
    public void sayHello2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        logger.info("hello2");
    }

    @Async
    public Future<String> getUser() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return new AsyncResult<>("Conan");
    }

    @Async
    public Future<List<String>> listUser() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return new AsyncResult<>(Arrays.asList("Conan", "Lucy", "Lily"));
    }

}
