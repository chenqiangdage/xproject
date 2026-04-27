package com.example.jiajiebang.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户信息VO
 */
@Data
public class UserVO {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 微信OpenID
     */
    private String openid;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
    
    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
