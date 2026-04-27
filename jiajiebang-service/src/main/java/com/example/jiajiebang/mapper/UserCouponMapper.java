package com.example.jiajiebang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiajiebang.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户优惠券关联Mapper接口
 */
@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {
}
