package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8RoleMenuProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/4/19 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_role_menu")
@FieldNameConstants
public class M8RoleMenu implements ProxyEntityAvailable<M8RoleMenu , M8RoleMenuProxy> {
    @Column(primaryKey = true)
    private String id;
    private String role_id;
    private String menu_id;
}
