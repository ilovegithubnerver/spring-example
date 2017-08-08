# Spring - AOP 切面

AOP面向切面编程，作为面向对象的一种补充，用于处理系统中分布于各个模块的横切关注点，比如事务管理、日志、缓存等。

AOP类型：

- 静态代理AspectJ，编译时生成代理类
- 动态代理Spring AOP，包括JDK动态代理、CGLib动态代理，运行时在内存生成代理类，不会修改字节码

#### AspectJ 静态代理

具有更好的性能，但是需要特定的编译器进行处理

#### JDK 动态代理

通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。

动态代理的核心是InvocationHandler接口和Proxy类。

#### CGLib 动态代理

如果目标类没有实现接口，那么Spring AOP会选择使用CGLib来动态代理目标类。

CGLib是通过继承的方式做的动态代理，如果某个类被标记为final，它是无法使用CGLib做动态代理的。

*PS：本文使用的是spring-4.3.7.RELEASE，aspectj-1.8.9*