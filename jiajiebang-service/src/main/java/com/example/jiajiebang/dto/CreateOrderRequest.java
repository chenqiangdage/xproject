package com.example.jiajiebang.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 创建订单请求DTO
 */
@Data
public class CreateOrderRequest {
    
    /**
     * 服务ID
     */
    private Long serviceId;
    
    /**
     * 服务名称
     */
    private String serviceName;
    
    /**
     * 服务图片URL
     */
    private String serviceImage;
    
    /**
     * 提供者ID
     */
    private Long providerId;
    
    /**
     * 提供者姓名
     */
    private String providerName;
    
    /**
     * 提供者头像URL
     */
    private String providerAvatar;
    
    /**
     * 预约服务时间
     */
    private LocalDateTime serviceTime;
    
    /**
     * 地址ID
     */
    private Long addressId;
    
    /**
     * 服务地址
     */
    private String address;
    
    /**
     * 订单金额（元）
     */
    private BigDecimal price;
    
    /**
     * 备注信息
     */
    private String remark;
}
