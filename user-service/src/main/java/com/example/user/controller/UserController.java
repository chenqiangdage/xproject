package com.example.user.controller;

import com.example.common.result.Result;
import com.example.user.model.User;
import com.example.user.util.UserContextUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        // 获取当前登录用户信息（从网关传递的header中）
        Long currentUserId = UserContextUtil.getCurrentUserId();
        String currentUsername = UserContextUtil.getCurrentUsername();
        
        System.out.println("Current user: " + currentUsername + " (ID: " + currentUserId + ")");
        
        User user = new User(id, "User_" + id, 20 + id.intValue());
        return Result.success(user);
    }
}
