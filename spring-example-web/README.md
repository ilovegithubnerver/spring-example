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

*PS：本文使用的是spring-4.3.7.RELEASE*