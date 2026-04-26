package com.example.user.controller;

import com.example.common.result.Result;
import com.example.user.model.User;
import com.example.user.util.UserContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户服务", description = "用户信息查询相关接口")
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    public Result<User> getUserById(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long id) {
        // 获取当前登录用户信息（从网关传递的header中）
        Long currentUserId = UserContextUtil.getCurrentUserId();
        String currentUsername = UserContextUtil.getCurrentUsername();
        
        System.out.println("Current user: " + currentUsername + " (ID: " + currentUserId + ")");
        
        User user = new User(id, "User_" + id, 20 + id.intValue());
        return Result.success(user);
    }
}
