package com.example.jiajiebang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.jiajiebang.entity.User;
import com.example.jiajiebang.dto.WechatLoginRequest;
import com.example.jiajiebang.dto.UpdateUserRequest;
import com.example.jiajiebang.vo.UserVO;
import com.example.jiajiebang.vo.LoginResponse;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 微信登录
     */
    LoginResponse wechatLogin(WechatLoginRequest request);
    
    /**
     * 获取用户信息
     */
    UserVO getUserProfile(Long userId);
    
    /**
     * 更新用户信息
     */
    UserVO updateUserProfile(Long userId, UpdateUserRequest request);
}
