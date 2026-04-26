package com.example.user.filter;

import com.example.common.util.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.io.IOException;

/**
 * JWT认证过滤器 - 保护内部服务不被未授权访问
 */
@Component
@Order(1)
public class JwtAuthFilter implements Filter {

    // 网关签名密钥（必须与gateway-service保持一致）
    private static final String GATEWAY_SECRET_KEY = "gateway-secret-key-2026";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String path = request.getRequestURI();
        
        // 跳过健康检查等公开接口
        if (path.startsWith("/actuator/") || path.equals("/health")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        
        // 从请求头获取Token
        String authorization = request.getHeader("Authorization");
        
        // 优先验证Authorization header中的Token
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            if (JwtUtils.isTokenValid(token)) {
                // Token有效，放行
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        
        // 如果没有Authorization header，检查是否是来自网关的内部调用
        // 网关会添加 X-User-Id、X-Gateway-Signature 和 X-Gateway-Timestamp header
        String userId = request.getHeader("X-User-Id");
        String username = request.getHeader("X-Username");
        String gatewaySignature = request.getHeader("X-Gateway-Signature");
        String timestamp = request.getHeader("X-Gateway-Timestamp");
        
        if (userId != null && username != null && gatewaySignature != null && timestamp != null) {
            // 验证网关签名
            if (verifyGatewaySignature(request, userId, username, timestamp, gatewaySignature)) {
                // 签名验证通过，放行
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                // 签名验证失败
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"Invalid gateway signature\"}");
                return;
            }
        }
        
        // 既没有有效Token，也没有有效的网关签名，拒绝访问
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"Unauthorized: Valid token or gateway signature required\"}");
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
                return false; // 请求超时
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
            
            // 比较签名
            return expectedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
