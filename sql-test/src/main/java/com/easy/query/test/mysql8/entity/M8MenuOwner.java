package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8MenuOwnerProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/7/22 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_menu")
@FieldNameConstants
public class M8MenuOwner implements ProxyEntityAvailable<M8MenuOwner , M8MenuOwnerProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
}
