package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8RoleProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/3/15 12:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_role")
@EntityProxy
@FieldNameConstants
public class M8Role implements ProxyEntityAvailable<M8Role , M8RoleProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;

}
