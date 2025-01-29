package com.easy.query.test.dto;

import com.easy.query.core.annotation.ValueObject;
import lombok.Data;

/**
 * create time 2024/2/9 10:43
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class ValueCompanyDTO {
    private String id;
    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业地址信息
     */
    @ValueObject
    private ValueCompanyAddressDTO address;

}
