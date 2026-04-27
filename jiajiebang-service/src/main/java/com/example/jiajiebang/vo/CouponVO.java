package com.example.jiajiebang.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券VO
 */
@Data
public class CouponVO {
    
    /**
     * 优惠券ID
     */
    private Long id;
    
    /**
     * 优惠券标题
     */
    private String title;
    
    /**
     * 优惠金额（元）
     */
    private BigDecimal discount;
    
    /**
     * 最低使用金额（元）
     */
    private BigDecimal minAmount;
    
    /**
     * 有效期结束时间
     */
    private LocalDateTime expireTime;
    
    /**
     * 状态：unused-未使用，used-已使用，expired-已过期
     */
    private String status;
}
