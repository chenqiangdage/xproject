package com.example.jiajiebang.vo;

import lombok.Data;

/**
 * 地址VO
 */
@Data
public class AddressVO {
    
    /**
     * 地址ID
     */
    private Long id;
    
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
