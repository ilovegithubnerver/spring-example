package com.example.contact.model;

import javax.validation.constraints.NotNull;

/**
 * Created by liweitang on 2017/9/9.
 */
public class ContactSaveParams {

    @NotNull(message = "名称不能为空")
    private String name;
    private String phone;
    @NotNull(message = "邮箱不能为空")
    private String email;
    private String grouping;
    private String isEnable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}
