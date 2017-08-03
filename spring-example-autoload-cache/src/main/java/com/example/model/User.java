package com.example.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String account;
    private String password;

    public User() {
    }

    public User(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", account='" + account + '\'' + ", password='" + password + '\'' + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
