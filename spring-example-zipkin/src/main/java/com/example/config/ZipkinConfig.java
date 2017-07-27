package com.example.config;

import brave.Tracer;
import brave.Tracing;
import brave.context.log4j12.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.TracingHandlerInterceptor;
import com.example.zipkin.RabbitSender;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

@Configuration
@Import({TracingClientHttpRequestInterceptor.class, TracingHandlerInterceptor.class})
public class ZipkinConfig {

    @Bean
    public Sender httpSender() {
        return OkHttpSender.create("http://localhost:9411/api/v1/spans");
    }

    @Bean
    public Sender rabbitSender() {
        String url = "127.0.0.1";
        ConnectionFactory connectionFactory = new CachingConnectionFactory(url);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("sleuth");
        return new RabbitSender(rabbitTemplate);
    }

    @Bean
    public Reporter<Span> reporter(@Qualifier("httpSender") Sender rabbitSender) {
        return AsyncReporter.builder(rabbitSender).build();
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

    @ControllerAdvice
    public static class TraceContextAdvice implements ResponseBodyAdvice<Object> {
        @Autowired
        private Tracer tracer;

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            if (tracer.currentSpan().context().parentId() == null) {
                String traceId = "";
                if (tracer.currentSpan() != null) {
                    traceId = tracer.currentSpan().context().traceIdString();
                }
                response.getHeaders().add("Trace-Id", traceId);
            }
            return body;
        }
    }

}
