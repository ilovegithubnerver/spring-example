package com.example;

import com.example.dao.Schema;
import com.example.dao.tables.User;
import com.example.jooq.JooqConfiguration;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JooqConfiguration.class})
public class JooqTest {

    @Autowired
    DSLContext dsl;

    @Test
    public void testInsert() {
        User USER = Schema.SCHEMA.USER;
        dsl.insertInto(USER)
                .columns(USER.ID, USER.ACCOUNT, USER.PASSWORD)
                .values(1L, "conanli", "123456")
                .execute();
    }
}
