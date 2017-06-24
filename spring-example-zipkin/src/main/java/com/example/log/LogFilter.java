package com.example.log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LogHelper.apply();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LogHelper.cleanup();
    }

}
