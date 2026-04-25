package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.common.result.Result;
import com.example.common.util.JwtUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "123456".equals(request.getPassword())) {
            String token = JwtUtils.generateToken(request.getUsername());
            LoginResponse response = new LoginResponse(token, request.getUsername());
            return Result.success(response);
        }
        return Result.error(401, "Invalid username or password");
    }
}
