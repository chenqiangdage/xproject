package com.example.order.controller;

import com.example.common.result.Result;
import com.example.order.feign.UserFeignClient;
import com.example.order.model.Order;
import com.example.order.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        Result<User> userResult = userFeignClient.getUserById(1L);
        User user = userResult.getData();
        
        Order order = new Order();
        order.setId(id);
        order.setOrderNo("ORDER_" + id);
        order.setUser(user);
        order.setAmount(99.99);
        
        return Result.success(order);
    }
}
