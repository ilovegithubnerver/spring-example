package com.example;

import com.example.dao.Schema;
import com.example.dao.tables.Example;
import com.example.jooq.JooqConfiguration;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JooqConfiguration.class})
public class JooqTest2 {

    @Autowired
    DSLContext dsl;
    @Autowired
    DataSourceTransactionManager transactionManager;

    @Test
    public void transaction() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            doAction();
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new RuntimeException(e);
        }
    }

    public void doAction() {
        Example EXAMPLE = Schema.SCHEMA.EXAMPLE;
        dsl.insertInto(EXAMPLE)
                .columns(EXAMPLE.ID, EXAMPLE.NAME, EXAMPLE.CODE)
                .values(1L, "a", "aaa")
                .execute();
        fail();
    }

    private void fail() {
        throw new RuntimeException("系统异常");
    }
}
