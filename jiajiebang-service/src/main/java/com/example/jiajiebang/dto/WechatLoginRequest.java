package com.example.jiajiebang.dto;

import lombok.Data;

/**
 * 微信登录请求DTO
 */
@Data
public class WechatLoginRequest {
    
    /**
     * 微信登录code
     */
    private String code;
    
    /**
     * 用户信息
     */
    private UserInfo userInfo;
    
    @Data
    public static class UserInfo {
        private String nickName;
        private String avatarUrl;
        private Integer gender;
    }
}
