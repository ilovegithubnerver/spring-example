# Spring - Web

#### *WebApplicationInitializer 接口*

```java
public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);
        ctx.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }

}
```

```java
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.example")
public class WebConfig {
}
```

#### *RestController*

```java
@RestController
public class HelloRestController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!!!";
    }

}
```

## Apache Tomcat Maven Plugin

官网：http://tomcat.apache.org/maven-plugin.html

#### 本地运行

在`pom.xml`中添加如下配置，使用`mvn clean tomcat7:run`启动即可。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>2.2</version>
            <configuration>
                <path>/</path>
                <port>8080</port>
                <uriEncoding>UTF-8</uriEncoding>
                <server>tomcat7</server>
            </configuration>
        </plugin>
    </plugins>
</build>
```

#### 远程部署

第一步：修改远程Tomcat，在`tomcat/conf/tomcat-users.xml`下配置用户权限

```xml
<role rolename="tomcat"/>
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="admin" roles="tomcat,manager-gui,manager-script"/>
```

第二步：修改本地Maven，在`maven/conf/settings.xml`下配置服务

```xml
<server>
    <id>tomcat7</id>
    <username>admin</username>
    <password>admin</password>
</server>
```

第三步：修改项目`pom.xml`配置，使用`mvn clean tomcat7:deploy`即可把项目部署到远程Tomcat中。

还有重新部署`mvn clean tomcat7:redeploy`、清除部署`mvn clean tomcat7:undeploy`

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.tomcat.maven</groupId>
            <artifactId>tomcat7-maven-plugin</artifactId>
            <version>2.2</version>
            <configuration>
                <url>http://localhost:8080/manager/text</url>
                <server>tomcat7</server>
            </configuration>
        </plugin>
    </plugins>
</build>
```

*PS：本文使用的是spring-4.3.7.RELEASE*