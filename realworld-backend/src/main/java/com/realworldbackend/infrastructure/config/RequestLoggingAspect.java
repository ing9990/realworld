package com.realworldbackend.infrastructure.config;

import com.google.common.base.Joiner;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class RequestLoggingAspect {

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    @Pointcut("within(*..*Api)")
    public void apiRequest() {
    }

    @Around(value = "com.realworldbackend.infrastructure.config.RequestLoggingAspect.apiRequest()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String[]> paramMap = request.getParameterMap();

        String params = "";

        if (!paramMap.isEmpty()) {
            params = " [" + paramMapToString(paramMap) + "]";
        }

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();

        log.info("Request: {} {} {} < ({}ms)", request.getMethod(), request.getRequestURI(), params, end - start);

        return result;
    }
}