package com.example.jiajiebang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiajiebang.entity.Address;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地址Mapper接口
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {
}
