package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.blogtest.proxy.SysRoleProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2024/4/29 22:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_role")
@Data
@EntityProxy
public class SysRole implements ProxyEntityAvailable<SysRole, SysRoleProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            mappingClass = UserRole.class,
            selfMappingProperty = "roleId",
            targetMappingProperty = "userId")
    private List<SysUser> users;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            mappingClass = RoleMenu.class,
            selfMappingProperty = "roleId",
            targetMappingProperty = "menuId")
    private List<SysMenu> menus;

    @Override
    public Class<SysRoleProxy> proxyTableClass() {
        return SysRoleProxy.class;
    }
}
