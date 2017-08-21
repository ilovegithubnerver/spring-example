package com.example;

import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof RereadableHttpServletRequestWrapper))
            request = new RereadableHttpServletRequestWrapper(request);
        filterChain.doFilter(request, response);
    }

    public static class RereadableHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private byte[] body = new byte[0];
        private ByteArrayInputStream byteArrayInputStream = null;
        private ServletInputStream servletInputStream = null;
        private BufferedReader bufferedReader = null;

        public RereadableHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            try {
                if (request.getInputStream() != null)
                    body = StreamUtils.copyToByteArray(request.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayInputStream = new ByteArrayInputStream(body);
            servletInputStream = new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
            bufferedReader = new BufferedReader(new InputStreamReader(servletInputStream));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            byteArrayInputStream.reset();
            return servletInputStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            byteArrayInputStream.reset();
            return bufferedReader;
        }
    }
}
