package com.example.jiajiebang.vo;

import lombok.Data;

/**
 * 轮播图VO
 */
@Data
public class BannerVO {
    
    /**
     * 轮播图ID
     */
    private Long id;
    
    /**
     * 轮播图URL
     */
    private String image;
    
    /**
     * 跳转链接
     */
    private String link;
    
    /**
     * 排序号
     */
    private Integer sort;
}
