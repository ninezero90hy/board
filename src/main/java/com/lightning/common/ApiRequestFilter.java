package com.lightning.common;

import com.lightning.constants.CommonApiConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
@Component
@WebFilter(CommonApiConstant.PREFIX + "/*")
public class ApiRequestFilter implements Filter {

    private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_FORMAT = "[{}] {}: {}";

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String method = httpServletRequest.getMethod();

        printStartTime(httpServletRequest, method);

        try {
            chain.doFilter(request, response);
        } finally {
            printEndTime(httpServletRequest, method);
        }
    }

    private void printStartTime(final HttpServletRequest httpServletRequest,
                                final String method) {
        log.info(LOG_FORMAT, method, httpServletRequest.getRequestURI(), LOG_DATE_FORMAT.format(System.currentTimeMillis()));
    }

    private void printEndTime(final HttpServletRequest httpServletRequest,
                              final String method) {
        log.info(LOG_FORMAT, method, httpServletRequest.getRequestURI(), LOG_DATE_FORMAT.format(System.currentTimeMillis()));
    }
}