package com.example.config;

import brave.Tracer;
import brave.Tracing;
import brave.context.log4j12.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.TracingHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

@Configuration
@Import({TracingClientHttpRequestInterceptor.class, TracingHandlerInterceptor.class})
public class ZipkinConfig {

    @Bean
    public Sender sender() {
        return OkHttpSender.create("http://localhost:9411/api/v1/spans");
    }

    @Bean
    public Reporter<Span> reporter(Sender sender) {
        return AsyncReporter.builder(sender).build();
    }

    @Bean
    public Tracing tracing(Reporter<Span> reporter) {
        return Tracing.newBuilder()
                .localServiceName("spring-example-zipkin")
                .currentTraceContext(MDCCurrentTraceContext.create())
                .reporter(reporter).build();
    }

    @Bean
    public HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    @Bean
    public Tracer tracer(Tracing tracing) {
        return tracing.tracer();
    }

}
