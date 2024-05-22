package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.blogtest.proxy.RoleMenuProxy;
import lombok.Data;

/**
 * create time 2024/4/29 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_role_menu")
@Data
@EntityProxy
public class RoleMenu implements ProxyEntityAvailable<RoleMenu , RoleMenuProxy> {
    @Column(primaryKey = true)
    private String id;
    private String roleId;
    private String menuId;

}
