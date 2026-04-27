package com.example.jiajiebang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiajiebang.entity.Review;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评价Mapper接口
 */
@Mapper
public interface ReviewMapper extends BaseMapper<Review> {
}
