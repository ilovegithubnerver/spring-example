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
public class SingleTransactionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Test
    public void transaction() {
        // 定义事务类型
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 如果当前没有事务，就新建一个事务，
        // 如果已经存在一个事务，就使用这个事务中
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 获取事务
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            doAction();// 执行一些数据库操作
            transactionManager.commit(transaction);// 提交事务，操作成功
        } catch (Exception e) {
            transactionManager.rollback(transaction);// 回滚事务，操作失败
            throw new RuntimeException(e);
        }
    }

    public void doAction() {
        jdbcTemplate.execute("insert into user(account,password) value('lily','123456')");
        fail();
    }

    private void fail() {
        throw new RuntimeException("系统异常");
    }
}
