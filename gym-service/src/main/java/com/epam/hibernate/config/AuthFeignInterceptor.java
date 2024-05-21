package com.epam.hibernate.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            template.header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        }
    }
}
