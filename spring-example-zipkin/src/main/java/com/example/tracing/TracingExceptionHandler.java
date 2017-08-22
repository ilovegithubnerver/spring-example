package com.example.tracing;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import zipkin.Constants;
import zipkin.TraceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

@ControllerAdvice
public class TracingExceptionHandler {

    @Autowired
    private Tracer tracer;

    @ExceptionHandler
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        brave.Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            currentSpan.tag(TraceKeys.HTTP_STATUS_CODE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            currentSpan.tag(Constants.ERROR, e.getMessage());
            currentSpan.tag("exception", getStackTrace(e));

            String requestBody = getRequestBody(request);
            if (requestBody != null && requestBody.trim().length() > 0)
                currentSpan.tag("http.request_body", requestBody);

            String queryParams = request.getQueryString();
            if (queryParams != null && queryParams.trim().length() > 0)
                currentSpan.tag("http.query_params", queryParams);
        }
        throw e;
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            byte[] body = ((ContentCachingRequestWrapper) request).getContentAsByteArray();
            if (body.length > 0)
                return new String(body, Charset.defaultCharset());
        }
        return null;
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
