package com.example.service;

import com.example.model.Permission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {

    public List<Permission> listByRoleId(Long roleId) {
        List<Permission> permissions = new ArrayList<>();

        permissions.add(new Permission(1L, "add"));
        permissions.add(new Permission(2L, "update"));
        permissions.add(new Permission(3L, "delete"));

        return permissions;
    }
}
