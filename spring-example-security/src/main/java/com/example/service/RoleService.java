package com.example.service;

import com.example.model.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    public List<Role> listByUserId(Long userId) {
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(1L, "ADMIN"));
        roles.add(new Role(2L, "ROLE"));
        roles.add(new Role(3L, "USER"));

        return roles;
    }
}
