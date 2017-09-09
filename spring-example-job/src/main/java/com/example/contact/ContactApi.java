package com.example.contact;

import com.example.contact.model.ContactQueryParams;
import com.example.contact.model.ContactSaveParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liweitang on 2017/9/9.
 */
@RestController
@RequestMapping("/contact")
public class ContactApi {

    @Autowired
    ContactStore contactStore;

    @PostMapping("/save")
    public Boolean save(@RequestBody ContactSaveParams params) {
        contactStore.save(params.getName(), params.getPhone(), params.getEmail(), params.getGrouping(), params.getIsEnable());
        return true;
    }

    @PostMapping("/delete")
    public Boolean delete(@RequestBody ContactQueryParams params) {
        contactStore.delete(params.getName());
        return true;
    }

    @PostMapping("/list")
    public List<Contact> list() {
        List<Contact> contacts = contactStore.list();
        return contacts;
    }
}
