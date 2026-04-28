package com.example.gateway.filter;

import com.example.common.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> WHITE_LIST = List.of("/api/auth/login");
    
    // 网关签名密钥（生产环境应该从配置中心获取）
    private static final String GATEWAY_SECRET_KEY = "gateway-secret-key-2026";
    
    private final ReactiveStringRedisTemplate redisTemplate;

    public AuthGlobalFilter(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 白名单直接放行
        if (WHITE_LIST.contains(path)) {
            return chain.filter(exchange);
        }

        // 只对 /api/ 开头的接口进行鉴权
        if (path.startsWith("/api/")) {
            List<String> authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
            
            if (authHeader == null || authHeader.isEmpty()) {
                return unauthorized(exchange.getResponse());
            }

            String token = authHeader.get(0);
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 验证Token格式和签名
            if (!JwtUtils.isTokenValid(token)) {
                return unauthorized(exchange.getResponse());
            }
            
            // 检查Token是否在黑名单中（已登出）
            final String finalToken = token;
            String blacklistKey = "token:blacklist:" + finalToken;
            final ServerHttpRequest finalRequest = request;
            final ServerWebExchange finalExchange = exchange;
            return redisTemplate.hasKey(blacklistKey)
                .flatMap(isBlacklisted -> {
                    if (Boolean.TRUE.equals(isBlacklisted)) {
                        return unauthorized(finalExchange.getResponse());
                    }
                    
                    // Token有效，将用户信息传递给下游服务
                    Long userId = JwtUtils.getUserIdFromToken(finalToken);
                    String username = JwtUtils.getUsernameFromToken(finalToken);
                    
                    // 生成网关签名（防止请求被伪造）
                    String signature = generateSignature(finalRequest, userId, username);
                    
                    // 构建新的请求，保留原始Authorization header并添加网关签名
                    ServerHttpRequest.Builder requestBuilder = finalRequest.mutate()
                        .header("X-User-Id", String.valueOf(userId))
                        .header("X-Username", username)
                        .header("X-Gateway-Signature", signature)
                        .header("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()));
                    
                    // 保留原始的Authorization header，让下游服务也能验证
                    if (!finalRequest.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).isEmpty()) {
                        requestBuilder.header(HttpHeaders.AUTHORIZATION, 
                            finalRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
                    }
                    
                    ServerHttpRequest modifiedRequest = requestBuilder.build();
                    
                    return chain.filter(finalExchange.mutate().request(modifiedRequest).build());
                });
        }

        return chain.filter(exchange);
    }
    
    private Mono<Void> unauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
    
    /**
     * 生成网关签名（HMAC-SHA256）
     * 签名内容：userId + username + timestamp + path
     */
    private String generateSignature(ServerHttpRequest request, Long userId, String username) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String path = request.getURI().getPath();
            
            // 签名字符串
            String dataToSign = userId + ":" + username + ":" + timestamp + ":" + path;
            
            // HMAC-SHA256签名
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                GATEWAY_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"
            );
            mac.init(secretKey);
            byte[] hash = mac.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
            
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
