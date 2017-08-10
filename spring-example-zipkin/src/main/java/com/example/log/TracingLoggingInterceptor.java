package com.example.log;

import brave.Tracer;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import zipkin.Constants;
import zipkin.TraceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@Component
public class TracingLoggingInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private Tracer tracer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        brave.Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            String traceId = currentSpan.context().traceIdString();
            response.addHeader("Trace-Id", traceId);
            MDC.put("TRACE_ID", traceId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        brave.Span currentSpan = tracer.currentSpan();
        if (currentSpan != null && ex != null) {
            currentSpan.tag(TraceKeys.HTTP_STATUS_CODE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            currentSpan.tag(Constants.ERROR, ex.getMessage());
            currentSpan.tag("exception", getStackTrace(ex));
        }
        MDC.remove("TRACE_ID");
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
