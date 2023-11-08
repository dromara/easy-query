package com.easy.query.test.entity.company;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ValueObject;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/11/7 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
@EntityProxy
public class ValueCompanyDTO {
    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业地址信息
     */
    @ValueObject
    private ValueCompanyAddressDTO address;
    /**
     * 企业营业执照信息
     */
    @ValueObject
    private ValueCompanyLicense license;
}
