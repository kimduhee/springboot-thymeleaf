package com.framework.app.common.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //로그 추적을 위한 traceId 설정(logback 설정과 연계)
        MDC.put("traceId", UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.clear();
    }
}
