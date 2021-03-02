package com.anupama.sinha;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        prepareMDC(request, handler);
        logger.info("Incoming request");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long requestEndTime = System.currentTimeMillis();
        long execTime = requestEndTime - Long.parseLong(MDC.get("startTime"));
        logger.info("Request Completed; execTime={}ms", execTime);
        MDC.clear();
    }

    private void prepareMDC(HttpServletRequest request, Object handler) {
        long requestStartTime = System.currentTimeMillis();
        MDC.put("startTime", String.valueOf(requestStartTime));
        if(handler instanceof HandlerMethod) {
            String method = ((HandlerMethod) handler).getMethod().getName();
            MDC.put("ENDPOINT",method);
        }
        MDC.put("QUERYSTRING",request.getQueryString());
        MDC.put("URI",request.getRequestURI());
    }
}