package com.example.jiajiebang.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务实体类
 */
@Data
@TableName("services")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 服务特色（JSON数组）
     */
    private String features;

    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}
