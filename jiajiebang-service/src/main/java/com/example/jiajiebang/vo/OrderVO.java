package com.example.jiajiebang.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单VO
 */
@Data
public class OrderVO {
    
    /**
     * 订单ID
     */
    private String id;
    
    /**
     * 服务名称
     */
    private String serviceName;
    
    /**
     * 服务图片URL
     */
    private String serviceImage;
    
    /**
     * 提供者姓名
     */
    private String providerName;
    
    /**
     * 提供者头像URL
     */
    private String providerAvatar;
    
    /**
     * 订单状态
     */
    private String status;
    
    /**
     * 订单状态文本
     */
    private String statusText;
    
    /**
     * 预约服务时间
     */
    private LocalDateTime serviceTime;
    
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
    
    /**
     * 是否可以评价
     */
    private Boolean canEvaluate;
    
    /**
     * 取消原因
     */
    private String cancelReason;
    
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
