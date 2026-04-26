package com.example.order.controller;

import com.example.common.result.Result;
import com.example.order.feign.UserFeignClient;
import com.example.order.model.Order;
import com.example.order.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Tag(name = "订单服务", description = "订单查询、创建等订单管理相关接口")
public class OrderController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/{id}")
    @Operation(summary = "获取订单信息", description = "根据订单ID获取订单详细信息，包含用户信息")
    public Result<Order> getOrderById(
            @Parameter(description = "订单ID", required = true, example = "1")
            @PathVariable Long id) {
        // 通过Feign调用user-service获取用户信息
        Result<User> userResult = userFeignClient.getUserById(1L);
        User user = userResult.getData();
        
        Order order = new Order();
        order.setId(id);
        order.setOrderNo("ORDER_" + id);
        order.setUser(user);
        order.setAmount(99.99);
        
        return Result.success(order);
    }
    
    @PostMapping
    @Operation(summary = "创建订单", description = "创建新订单，会自动关联用户信息")
    public Result<Order> createOrder(
            @Parameter(description = "创建订单请求，包含用户ID和金额", required = true)
            @RequestBody CreateOrderRequest request) {
        // 通过Feign调用user-service获取用户信息（会自动传递Token）
        Result<User> userResult = userFeignClient.getUserById(request.getUserId());
        
        if (userResult.getCode() != 200 || userResult.getData() == null) {
            return Result.error(404, "User not found");
        }
        
        User user = userResult.getData();
        
        // 创建订单，包含用户信息
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setOrderNo("ORDER_" + System.currentTimeMillis());
        order.setUser(user);
        order.setAmount(request.getAmount());
        
        System.out.println("订单创建成功: " + order.getOrderNo() + ", 用户: " + user.getName());
        
        return Result.success(order);
    }
    
    // 请求DTO
    public static class CreateOrderRequest {
        private Long userId;
        private Double amount;
        
        public Long getUserId() {
            return userId;
        }
        
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        
        public Double getAmount() {
            return amount;
        }
        
        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }
}
