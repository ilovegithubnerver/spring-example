package com.example;

import com.example.config.JdbcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcConfig.class})
public class MultiTransactionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Test
    public void transaction() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
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
        jdbcTemplate.execute("insert into user(account,password) value('lily','123456')");
        doAction2();
    }

    public void doAction2() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            jdbcTemplate.execute("insert into user(account,password) value('lucy','123456')");
            fail();
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            System.out.println(e.getMessage());
        }
    }

    private void fail() {
        throw new RuntimeException("系统异常");
    }

}
