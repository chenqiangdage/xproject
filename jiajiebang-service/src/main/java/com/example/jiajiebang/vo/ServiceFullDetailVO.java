package com.example.jiajiebang.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务完整详情VO（包含提供者和评价）
 */
@Data
public class ServiceFullDetailVO {
    
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
    
    /**
     * 服务提供者列表
     */
    private List<ProviderVO> providers;
    
    /**
     * 评价列表
     */
    private List<ReviewVO> reviews;
    
    @Data
    public static class ProviderVO {
        private Long id;
        private String name;
        private String avatar;
        private Integer experience;
        private BigDecimal rating;
        private Integer orderCount;
        private String certificates;
        private String specialties;
        private String introduction;
    }
    
    @Data
    public static class ReviewVO {
        private Long id;
        private Long userId;
        private String userName;
        private String userAvatar;
        private Integer rating;
        private String content;
        private String images;
        private LocalDateTime createTime;
        private String serviceName;
    }
}
