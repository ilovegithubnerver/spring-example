package com.example.config;

import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;
import brave.http.HttpTracing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

@Configuration
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
                .currentTraceContext(ThreadContextCurrentTraceContext.create())
                .reporter(reporter).build();
    }

    @Bean
    public HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

}
