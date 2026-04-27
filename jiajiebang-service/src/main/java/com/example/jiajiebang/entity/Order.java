package com.example.jiajiebang.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
@TableName("orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID（ORD+日期+序号）
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

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

    /**
     * 订单状态：pending-待接单，accepted-待服务，serving-服务中，completed-已完成，cancelled-已取消
     */
    private String status;

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
