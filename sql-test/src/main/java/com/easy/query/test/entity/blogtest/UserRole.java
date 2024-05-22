package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.blogtest.proxy.UserRoleProxy;
import lombok.Data;

/**
 * create time 2024/4/29 22:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user_role")
@Data
@EntityProxy
public class UserRole implements ProxyEntityAvailable<UserRole , UserRoleProxy> {
    @Column(primaryKey = true)
    private String id;
    private String userId;
    private String roleId;
}
