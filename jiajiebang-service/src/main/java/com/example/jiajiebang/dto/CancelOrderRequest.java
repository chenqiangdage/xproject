package com.example.jiajiebang.dto;

import lombok.Data;

/**
 * 取消订单请求DTO
 */
@Data
public class CancelOrderRequest {
    
    /**
     * 取消原因
     */
    private String reason;
}
