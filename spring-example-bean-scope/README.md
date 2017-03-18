# Spring-BeanScope（Bean作用域）

#### *singleton*

全局只有一个实例

#### *prototype*

每次调用产生一个新的实例

#### *request（Web）*

每次请求产生一个bean

#### *session（Web）*

每个用户session可以产生一个新的bean，不同用户之间的bean互相不影响

#### *globalSession（Web）*

用和session类似，只是使用portlet的时候使用

## 测试

```java
@Component
@Scope("singleton")
public class SingletonObj {}

@Component
@Scope("prototype")
public class PrototypeObj {}

@Component
@Scope("request")
public class RequestObj {}

@Component
@Scope("session")
public class SessionObj {}
```

```java
@RestController
@Scope("prototype")
public class IndexController {

    @Autowired
    private SingletonObj singletonObj;
    @Autowired
    private SingletonObj singletonObj2;
    @Autowired
    private PrototypeObj prototypeObj;
    @Autowired
    private PrototypeObj prototypeObj2;
    @Autowired
    private RequestObj requestObj;
    @Autowired
    private RequestObj requestObj2;
    @Autowired
    private SessionObj sessionObj;
    @Autowired
    private SessionObj sessionObj2;

    @RequestMapping("/")
    public List<String> index() {
        List<String> list = new ArrayList<>();
        list.add("first  time singleton is :" + singletonObj);
        list.add("second time singleton is :" + singletonObj2);

        list.add("first  time prototype is :" + prototypeObj);
        list.add("second time prototype is :" + prototypeObj2);

        list.add("first  time request is :" + requestObj);
        list.add("second time request is :" + requestObj2);

        list.add("first  time session is :" + sessionObj);
        list.add("second time session is :" + sessionObj2);

        for (String str : list)
            System.out.println(str);
        return list;
    }

}
```

```text
// 使用Chrome第一次访问
first  time singleton is :com.example.model.SingletonObj@59d6be72
second time singleton is :com.example.model.SingletonObj@59d6be72
first  time prototype is :com.example.model.PrototypeObj@72c2c7fa
second time prototype is :com.example.model.PrototypeObj@535b221b
first  time request is :com.example.model.RequestObj@1421de53
second time request is :com.example.model.RequestObj@1421de53
first  time session is :com.example.model.SessionObj@7868c78b
second time session is :com.example.model.SessionObj@7868c78b

// 使用Chrome第二次访问
first  time singleton is :com.example.model.SingletonObj@59d6be72
second time singleton is :com.example.model.SingletonObj@59d6be72
first  time prototype is :com.example.model.PrototypeObj@293b44b1
second time prototype is :com.example.model.PrototypeObj@27fcafe6
first  time request is :com.example.model.RequestObj@23c32fc1
second time request is :com.example.model.RequestObj@23c32fc1
first  time session is :com.example.model.SessionObj@7868c78b
second time session is :com.example.model.SessionObj@7868c78b

// 使用IE第三次访问
first  time singleton is :com.example.model.SingletonObj@59d6be72
second time singleton is :com.example.model.SingletonObj@59d6be72
first  time prototype is :com.example.model.PrototypeObj@3144d501
second time prototype is :com.example.model.PrototypeObj@471afe2f
first  time request is :com.example.model.RequestObj@5332078a
second time request is :com.example.model.RequestObj@5332078a
first  time session is :com.example.model.SessionObj@2f1de87b
second time session is :com.example.model.SessionObj@2f1de87b
```