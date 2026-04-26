package com.example.user.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserContextUtil {
    
    public static Long getCurrentUserId() {
        ServletRequestAttributes attributes = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            String userId = attributes.getRequest().getHeader("X-User-Id");
            if (userId != null) {
                return Long.parseLong(userId);
            }
        }
        return null;
    }
    
    public static String getCurrentUsername() {
        ServletRequestAttributes attributes = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            return attributes.getRequest().getHeader("X-Username");
        }
        return null;
    }
}
