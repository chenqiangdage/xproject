package com.example.order.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            // 从当前请求中获取Authorization header
            String authorization = attributes.getRequest().getHeader("Authorization");
            if (authorization != null && !authorization.isEmpty()) {
                template.header("Authorization", authorization);
            }
            
            // 传递用户信息header
            String userId = attributes.getRequest().getHeader("X-User-Id");
            String username = attributes.getRequest().getHeader("X-Username");
            
            if (userId != null) {
                template.header("X-User-Id", userId);
            }
            if (username != null) {
                template.header("X-Username", username);
            }
        }
    }
}
