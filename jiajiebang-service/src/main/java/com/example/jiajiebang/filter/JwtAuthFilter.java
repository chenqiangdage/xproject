package com.example.jiajiebang.filter;

import com.example.common.util.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * JWT认证过滤器
 */
@Slf4j
@Component
@Order(1)
public class JwtAuthFilter implements Filter {

    // 网关签名密钥（与gateway-service保持一致）
    private static final String GATEWAY_SECRET_KEY = "gateway-secret-key-2026";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String path = request.getRequestURI();
        
        // 跳过Swagger等公开接口
        if (path.startsWith("/actuator/") || 
            path.equals("/health") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
        // 获取请求头中的Token
        String authorization = request.getHeader("Authorization");
        
        // 验证JWT Token
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            try {
                if (JwtUtils.isTokenValid(token)) {
                    log.debug("JWT Token验证通过: {}", path);
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            } catch (Exception e) {
                log.warn("JWT Token验证失败: {}", e.getMessage());
            }
        }
        
        // 验证网关签名（内部服务调用）
        String userId = request.getHeader("X-User-Id");
        String username = request.getHeader("X-Username");
        String gatewaySignature = request.getHeader("X-Gateway-Signature");
        String timestamp = request.getHeader("X-Gateway-Timestamp");
        
        if (userId != null && username != null && gatewaySignature != null && timestamp != null) {
            if (verifyGatewaySignature(request, userId, username, timestamp, gatewaySignature)) {
                log.debug("网关签名验证通过: {}", path);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                log.warn("网关签名验证失败");
            }
        }
        
        // 未通过认证，返回401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"未授权访问\",\"data\":null}");
    }

    /**
     * 验证网关签名
     */
    private boolean verifyGatewaySignature(HttpServletRequest request, String userId, String username, 
                                           String timestamp, String signature) {
        try {
            // 检查时间戳是否过期（5分钟）
            long requestTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis();
            if (currentTime - requestTime > 5 * 60 * 1000) {
                log.warn("请求时间戳已过期");
                return false;
            }
            
            // 重新计算签名
            String path = request.getRequestURI();
            String dataToSign = userId + ":" + username + ":" + timestamp + ":" + path;
            
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                GATEWAY_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"
            );
            mac.init(secretKey);
            byte[] hash = mac.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
            String expectedSignature = Base64.getEncoder().encodeToString(hash);
            
            return expectedSignature.equals(signature);
        } catch (Exception e) {
            log.error("验证网关签名失败", e);
            return false;
        }
    }
}
