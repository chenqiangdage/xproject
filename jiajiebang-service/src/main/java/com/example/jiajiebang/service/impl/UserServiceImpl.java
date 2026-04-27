package com.example.jiajiebang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.JwtUtils;
import com.example.jiajiebang.dto.UpdateUserRequest;
import com.example.jiajiebang.dto.WechatLoginRequest;
import com.example.jiajiebang.entity.User;
import com.example.jiajiebang.mapper.UserMapper;
import com.example.jiajiebang.service.UserService;
import com.example.jiajiebang.vo.LoginResponse;
import com.example.jiajiebang.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public LoginResponse wechatLogin(WechatLoginRequest request) {
        // TODO: 实际项目中需要调用微信API验证code并获取openid
        // 这里模拟一个openid
        String openid = "mock_openid_" + UUID.randomUUID().toString().substring(0, 8);
        
        // 查询用户是否存在
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenid, openid));
        
        // 如果用户不存在，创建新用户
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            if (request.getUserInfo() != null) {
                user.setNickName(request.getUserInfo().getNickName());
                user.setAvatarUrl(request.getUserInfo().getAvatarUrl());
                user.setGender(request.getUserInfo().getGender());
            }
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            save(user);
            log.info("创建新用户: {}", user.getId());
        }
        
        // 生成JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getNickName() != null ? user.getNickName() : "用户" + user.getId());
        String token = JwtUtils.generateToken(user.getId(), claims.get("username").toString());
        
        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(convertToVO(user));
        
        return response;
    }

    @Override
    public UserVO getUserProfile(Long userId) {
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    public UserVO updateUserProfile(Long userId, UpdateUserRequest request) {
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (request.getNickName() != null) {
            user.setNickName(request.getNickName());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        user.setUpdateTime(LocalDateTime.now());
        
        updateById(user);
        return convertToVO(user);
    }

    /**
     * 转换为VO
     */
    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}
