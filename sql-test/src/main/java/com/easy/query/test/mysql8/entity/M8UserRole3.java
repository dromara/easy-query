package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserRole2Proxy;
import com.easy.query.test.mysql8.entity.proxy.M8UserRole3Proxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/3/15 12:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_user_role3")
@EntityProxy
@FieldNameConstants
public class M8UserRole3 implements ProxyEntityAvailable<M8UserRole3, M8UserRole3Proxy> {
    @Column(primaryKey = true)
    private String userRoleId;
    private String userId;
    private String roleId;
}
