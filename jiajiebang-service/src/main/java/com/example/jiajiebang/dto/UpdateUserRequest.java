package com.example.jiajiebang.dto;

import lombok.Data;

/**
 * 更新用户信息请求DTO
 */
@Data
public class UpdateUserRequest {
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
    
    /**
     * 手机号
     */
    private String phone;
}
