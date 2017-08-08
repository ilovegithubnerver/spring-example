package com.example.config;

import brave.Tracer;
import brave.Tracing;
import brave.context.log4j12.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import brave.spring.webmvc.TracingHandlerInterceptor;
import com.example.rabbit.RabbitSender;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import zipkin.Constants;
import zipkin.Span;
import zipkin.TraceKeys;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@Configuration
@Import({TracingClientHttpRequestInterceptor.class, TracingHandlerInterceptor.class})
public class ZipkinConfig {

    @Bean
    public Sender httpSender() {
        return OkHttpSender.create("http://localhost:9411/api/v1/spans");
    }

    // @Bean
    public Sender rabbitSender() {
        String url = "127.0.0.1";
        ConnectionFactory connectionFactory = new CachingConnectionFactory(url);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("sleuth");
        return new RabbitSender(rabbitTemplate);
    }

    @Bean
    public Reporter<Span> reporter(@Qualifier("httpSender") Sender sender) {
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

    @ControllerAdvice
    public static class TraceContextAdvice implements ResponseBodyAdvice<Object> {
        @Autowired
        Tracer tracer;

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            brave.Span currentSpan = tracer.currentSpan();
            if (currentSpan != null && currentSpan.context().parentId() == null) {
                String traceId = currentSpan.context().traceIdString();
                response.getHeaders().add("Trace-Id", traceId);
            }
            return body;
        }

        @Order(0)
        @ExceptionHandler
        public void exceptionHandler(Exception e, HttpServletResponse response) throws Exception {
            brave.Span currentSpan = tracer.currentSpan();
            if (currentSpan != null) {
                currentSpan.tag(TraceKeys.HTTP_STATUS_CODE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                currentSpan.tag(Constants.ERROR, e.getMessage());
                currentSpan.tag("exception", getStackTrace(e));

                if (currentSpan.context().parentId() == null) {
                    String traceId = currentSpan.context().traceIdString();
                    response.addHeader("Trace-Id", traceId);
                }
            }
            throw e;
        }

        private String getStackTrace(Throwable t) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            t.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        }
    }

}
