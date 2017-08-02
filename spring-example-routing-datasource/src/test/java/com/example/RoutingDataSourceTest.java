package com.example;

import com.example.config.JdbcConfig;
import com.example.config.MybatisConfig;
import com.example.mapper.UserMapper;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcConfig.class, MybatisConfig.class})
public class RoutingDataSourceTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Test
    public void test() {
        User user = null;

        // 读库 切面 ^(get|list|count|exist).*
        System.out.println("--------- 读库 ---------");
        System.out.println("user: " + getUser(1L));

        // 写库 切面 ^(get|list|count|exist).*
        System.out.println("--------- 写库 ---------");
        System.out.println("insert: " + insertUser(111L, "conanli", "123456"));

        // 写库 事务 isReadOnly
        System.out.println("--------- 写库 ---------");
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            System.out.println("user: " + getUser(2L));
            System.out.println("insert: " + insertUser(222L, "conanli", "123456"));

            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new RuntimeException(e);
        }
    }

    private User getUser(Long userId) {
        return userMapper.getById(userId);
    }

    private Long insertUser(Long userId, String account, String password) {
        User user = new User();
        user.setId(userId);
        user.setAccount(account);
        user.setPassword(password);
        return userMapper.insert(user);
    }

}
