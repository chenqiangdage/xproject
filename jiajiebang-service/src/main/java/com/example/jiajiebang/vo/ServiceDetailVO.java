package com.example.jiajiebang.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 服务详情VO
 */
@Data
public class ServiceDetailVO {
    
    /**
     * 服务ID
     */
    private Long id;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 服务名称
     */
    private String name;
    
    /**
     * 价格（元）
     */
    private BigDecimal price;
    
    /**
     * 单位
     */
    private String unit;
    
    /**
     * 服务时长
     */
    private String duration;
    
    /**
     * 服务图片URL
     */
    private String image;
    
    /**
     * 评分（0-5分）
     */
    private BigDecimal rating;
    
    /**
     * 订单数量
     */
    private Integer orderCount;
    
    /**
     * 服务描述
     */
    private String description;
    
    /**
     * 服务特色（JSON数组字符串）
     */
    private String features;
}
