package com.example.jiajiebang.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务提供者实体类（保洁师）
 */
@Data
@TableName("service_providers")
public class ServiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 提供者ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 从业年限
     */
    private Integer experience;

    /**
     * 评分（0-5分）
     */
    private BigDecimal rating;

    /**
     * 服务订单数
     */
    private Integer orderCount;

    /**
     * 资质证书（JSON数组）
     */
    private String certificates;

    /**
     * 擅长领域（JSON数组）
     */
    private String specialties;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 状态：0-休息，1-工作中
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
