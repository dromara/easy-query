package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ValueObject;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.CompanyProxy;
import lombok.Data;

/**
 * create time 2023/11/3 08:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_company")
@Data
@EntityProxy
public class Company implements ProxyEntityAvailable<Company , CompanyProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @ValueObject
    private CompanyAddress address;
}
