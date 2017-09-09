package com.example.contact;

import com.example.jooq_generated.tables.Contact;
import com.example.jooq_generated.tables.records.ContactRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liweitang on 2017/9/9.
 */
public class ContactStore {

    private DSLContext dsl;

    public ContactStore(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void save(String name, String phone, String email, String grouping, String isEnable) {
        Contact CONTACT = Contact.CONTACT;

        dsl.insertInto(CONTACT)
                .columns(CONTACT.NAME, CONTACT.PHONE, CONTACT.EMAIL, CONTACT.GROUPING, CONTACT.IS_ENABLE)
                .values(name, phone, email, grouping, isEnable)
                .onDuplicateKeyUpdate()
                .set(CONTACT.PHONE, phone)
                .set(CONTACT.EMAIL, email)
                .set(CONTACT.GROUPING, grouping)
                .set(CONTACT.IS_ENABLE, isEnable)
                .execute();
    }

    public void delete(String name) {
        Contact CONTACT = Contact.CONTACT;

        dsl.deleteFrom(CONTACT)
                .where(CONTACT.NAME.eq(name))
                .execute();
    }

    public List<com.example.contact.Contact> list() {
        Contact CONTACT = Contact.CONTACT;

        List<ContactRecord> contacts = dsl.select()
                .from(CONTACT)
                .fetchInto(CONTACT);

        return contacts.stream().map(contact -> {
            com.example.contact.Contact contact2 = new com.example.contact.Contact();
            contact2.setName(contact.getName());
            contact2.setPhone(contact.getPhone());
            contact2.setEmail(contact.getEmail());
            contact2.setGrouping(contact.getGrouping());
            contact2.setIsEnable(contact.getIsEnable());
            return contact2;
        }).collect(Collectors.toList());
    }
}
