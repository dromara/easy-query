package com.easy.query.test.entity.company;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.ValueObject;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.company.proxy.VCTable;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/11/7 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_company")
@Data
@ToString
@EntityProxy(value = "VCTable")
public class ValueCompany implements ProxyEntityAvailable<ValueCompany , VCTable> {
    @Column(primaryKey = true)
    private String id;
    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业地址信息
     */
    @ValueObject
    private ValueCompanyAddress address;
    /**
     * 企业营业执照信息
     */
    @ValueObject
    private ValueCompanyLicense license;
}
