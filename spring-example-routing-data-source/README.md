# Spring - Routing DataSource 动态数据源

通过Spring AOP在DAO层调用前定义切面，利用Spring的AbstractRoutingDataSource解决多数据源的问题，实现动态选择数据源。

#### RoutingDataSourceType

定义读READ、写WRITE两种类型的数据源

```java
public class RoutingDataSourceType {
    public static final String WRITE = "WRITE";
    public static final String READ = "READ";
}
```

#### RoutingDataSourceHolder

用线程变量保存当前正在操作的数据源类型

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

*PS：本文使用的是spring-4.3.7.RELEASE、mybatis-3.4.4*