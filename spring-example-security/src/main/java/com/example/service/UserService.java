package com.example.service;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getByUsername(String username) {
        User user = new User();
        user.setUsername("conanli");
        user.setPassword(passwordEncoder.encode("123456"));
        return user;
    }

}
