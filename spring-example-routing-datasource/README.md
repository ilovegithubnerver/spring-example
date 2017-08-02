# Spring - Routing DataSource 动态数据源

通过Spring AOP在DAO层调用前定义切面，利用Spring的AbstractRoutingDataSource解决多数据源的问题，实现动态选择数据源。

#### RoutingDataSourceType

定义读READ、写WRITE两种类型的数据源。

```java
public class RoutingDataSourceType {
    public static final String WRITE = "WRITE";
    public static final String READ = "READ";
}
```

#### RoutingDataSourceHolder

用线程变量保存当前正在操作的数据源类型。

```java
public class RoutingDataSourceHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();
    
    public static void set(String key) {
        holder.set(key);
    }

    public static String get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
```

#### RoutingDataSourceAspect

Dao切面，在执行前先相应的数据源保存到线程变量中，执行结束后再清空。

```java
@Aspect
public class RoutingDataSourceAspect {
    private static String regex = "^(get|list|count|exist).*";

    @Around("execution(* com.example.mapper.*Mapper.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        String methodName = point.getSignature().getName();
        if (Pattern.matches(regex, methodName)) {
            RoutingDataSourceHolder.set(RoutingDataSourceType.READ);
        } else {
            RoutingDataSourceHolder.set(RoutingDataSourceType.WRITE);
        }

        Object res = point.proceed();

        RoutingDataSourceHolder.clear();

        return res;
    }
}
```

#### RoutingDataSourceTransactionManager

处理存在事务的Dao操作，开始事务前保存相应的数据源到线程变量中，完成时再清空。

```java
public class RoutingDataSourceTransactionManager extends DataSourceTransactionManager {
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        boolean isReadOnly = definition.isReadOnly();
        if (isReadOnly) {
            RoutingDataSourceHolder.set(RoutingDataSourceType.READ);
        } else {
            RoutingDataSourceHolder.set(RoutingDataSourceType.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        RoutingDataSourceHolder.clear();
    }
}
```

#### RoutingDataSource

从线程变量中获取相应的数据源类型。

```java
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if (RoutingDataSourceType.READ.equals(RoutingDataSourceHolder.get()))
            return RoutingDataSourceType.READ;
        return RoutingDataSourceType.WRITE;
    }
}
```

#### RoutingDataSourceTest

分别测试读库，写库，事务

```java
public class RoutingDataSourceTest {
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
    ...
}
```

*注意：存在事务的Dao是在获取事务时获取DataSource，因此，同一个事务中的所有Dao，共用同一个DataSource，并不会单独获取*

*PS：本文使用的是spring-4.3.7.RELEASE、mybatis-3.4.4*