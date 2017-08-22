package com.example;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);
        ctx.setServletContext(servletContext);

        FilterRegistration.Dynamic filter = servletContext.addFilter("myFilter", MyFilter.class);
        filter.addMappingForUrlPatterns(null, false, "/*");

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        /**
         * 设置Multipart具体细节（必须）
         * @params location 指定文件存放的临时路径
         * @params maxFileSize 上传文件最大容量
         * @params maxRequestSize 整个请求的最大容量
         * @params fileSizeThreshold 0 表示将所有上传的文件写入到磁盘中
         */
        servlet.setMultipartConfig(new MultipartConfigElement("d:/temp", 20971520, 41943040, 0));
    }

}
