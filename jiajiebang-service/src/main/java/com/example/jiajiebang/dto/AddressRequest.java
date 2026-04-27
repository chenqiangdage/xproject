package com.example.jiajiebang.dto;

import lombok.Data;

/**
 * 添加/更新地址请求DTO
 */
@Data
public class AddressRequest {
    
    /**
     * 联系人姓名
     */
    private String name;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 详细地址
     */
    private String address;
    
    /**
     * 是否默认地址：0-否，1-是
     */
    private Integer isDefault;
}
