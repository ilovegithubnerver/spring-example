package com.example.tracing;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import zipkin.Constants;
import zipkin.TraceKeys;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class TracingExceptionHandler {

    @Autowired
    private Tracer tracer;

    @ExceptionHandler
    public void exceptionHandler(Exception e, HttpServletResponse response) throws Exception {
        brave.Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            currentSpan.tag(TraceKeys.HTTP_STATUS_CODE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            currentSpan.tag(Constants.ERROR, e.getMessage());
            currentSpan.tag("exception", getStackTrace(e));
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
