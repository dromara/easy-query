package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserRoleProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/3/15 12:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_user_role")
@EntityProxy
@FieldNameConstants
public class M8UserRole implements ProxyEntityAvailable<M8UserRole , M8UserRoleProxy> {
    @Column(primaryKey = true)
    private String id;
    @Column(oldName = "uid")
    private String userId;
    @Column(oldName = "rid")
    private String roleId;
}
