# Spring - Multi Transaction 复合事务

### 事务隔离级别

类型                       | 说明
-------------------------- | -----------
ISOLATION_DEFAULT          | 使用数据库默认的隔离级别。
ISOLATION_READ_UNCOMMITTED | 隔离级别最低，并发性能最高。允许读取未提交的数据。可能导致脏读、幻读、不可重复读。
ISOLATION_READ_COMMITTED   | 只能读取已提交的读取。可防止脏读，但仍可能导致幻读、不可重复读。
ISOLATION_REPEATABLE_READ  | 可防止脏读，不可重复读，不能避免幻读。
ISOLATION_SERIALIZABLE     | 隔离级别最高，消耗资源最低，代价最高，可防止脏读，幻读，不可重复读。

### 事务传播行为

类型                      | 说明
------------------------- | -----------
PROPAGATION_REQUIRED      | 如果当前没有事务，就新建一个事务，如果当前存在事务，就使用这个事务。
PROPAGATION_SUPPORTS      | 使用当前事务，如果当前没有事务，就以非事务方式执行。
PROPAGATION_MANDATORY     | 使用当前事务，如果当前没有事务，就抛出异常。
PROPAGATION_REQUIRES_NEW  | 新建一个事务，如果当前存在事务，就把当前事务挂起。
PROPAGATION_NOT_SUPPORTED | 以非事务方式执行，如果当前存在事务，就把当前事务挂起。
PROPAGATION_NEVER         | 以非事务方式执行，如果当前存在事务，则抛出异常。
PROPAGATION_NESTED        | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。

### 单一事务

```java
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
```

`transaction()`实现的事务与`@Transactional`或者AOP形式的类似，其中`doAction()`为真正的业务代码。`doAction()`抛出异常，事务回滚。

### 复合事务

```java
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
```

为实现部分提交，`doAction()`内部又开启了新的事务，为保证不影响原事务，传播行为设置成`PROPAGATION_REQUIRED`。即使`doAction2()`发生异常进行回滚，也不影响`doAction()`的提交。

*PS：本文使用的是spring-4.3.7.RELEASE*