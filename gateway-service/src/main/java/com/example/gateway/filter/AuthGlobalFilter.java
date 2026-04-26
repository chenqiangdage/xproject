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

import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> WHITE_LIST = List.of("/api/auth/login");
    
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
            String blacklistKey = "token:blacklist:" + token;
            return redisTemplate.hasKey(blacklistKey)
                .flatMap(isBlacklisted -> {
                    if (Boolean.TRUE.equals(isBlacklisted)) {
                        return unauthorized(exchange.getResponse());
                    }
                    
                    // Token有效，将用户信息传递给下游服务
                    Long userId = JwtUtils.getUserIdFromToken(token);
                    String username = JwtUtils.getUsernameFromToken(token);
                    
                    ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", String.valueOf(userId))
                        .header("X-Username", username)
                        .build();
                    
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                });
        }

        return chain.filter(exchange);
    }
    
    private Mono<Void> unauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
