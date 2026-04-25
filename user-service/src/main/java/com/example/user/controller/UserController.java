package com.example.user.controller;

import com.example.common.result.Result;
import com.example.user.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = new User(id, "User_" + id, 20 + id.intValue());
        return Result.success(user);
    }
}
