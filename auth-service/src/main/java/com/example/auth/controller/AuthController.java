package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.common.result.Result;
import com.example.common.util.JwtUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final StringRedisTemplate redisTemplate;

    public AuthController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        // 模拟用户验证（实际应该查询数据库）
        if ("admin".equals(request.getUsername()) && "123456".equals(request.getPassword())) {
            Long userId = 1L; // 模拟用户ID
            String token = JwtUtils.generateToken(userId, request.getUsername());
            
            // 将token存储到Redis，设置过期时间（24小时）
            String tokenKey = "token:" + token;
            redisTemplate.opsForValue().set(tokenKey, userId.toString(), 24, TimeUnit.HOURS);
            
            LoginResponse response = new LoginResponse(token, request.getUsername());
            return Result.success(response);
        }
        return Result.error(401, "Invalid username or password");
    }
    
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String token = authorization;
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        // 将token加入黑名单
        String blacklistKey = "token:blacklist:" + token;
        redisTemplate.opsForValue().set(blacklistKey, "1", 24, TimeUnit.HOURS);
        
        // 删除token缓存
        String tokenKey = "token:" + token;
        redisTemplate.delete(tokenKey);
        
        return Result.success(null);
    }
}
