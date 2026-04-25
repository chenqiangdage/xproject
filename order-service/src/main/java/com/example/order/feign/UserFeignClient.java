package com.example.order.feign;

import com.example.common.result.Result;
import com.example.order.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/api/user/{id}")
    Result<User> getUserById(@PathVariable("id") Long id);
}
