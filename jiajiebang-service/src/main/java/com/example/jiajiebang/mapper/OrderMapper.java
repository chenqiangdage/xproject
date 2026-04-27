package com.example.jiajiebang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiajiebang.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
