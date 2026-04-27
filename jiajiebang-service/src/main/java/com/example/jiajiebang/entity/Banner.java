package com.example.jiajiebang.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 轮播图实体类
 */
@Data
@TableName("banners")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 轮播图ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 排序号（数字越小越靠前）
     */
    private Integer sort;

    /**
     * 状态：0-停用，1-启用
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
