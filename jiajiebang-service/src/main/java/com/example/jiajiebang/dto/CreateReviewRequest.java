package com.example.jiajiebang.dto;

import lombok.Data;
import java.util.List;

/**
 * 提交评价请求DTO
 */
@Data
public class CreateReviewRequest {
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 服务ID
     */
    private Long serviceId;
    
    /**
     * 提供者ID
     */
    private Long providerId;
    
    /**
     * 评分（1-5星）
     */
    private Integer rating;
    
    /**
     * 评价内容
     */
    private String content;
    
    /**
     * 评价图片列表
     */
    private List<String> images;
}
