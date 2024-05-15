package com.easy.query.test.dto.autotest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dto.autotest.proxy.RoleProxy;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2024/5/15 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Accessors(chain = true)
@EntityProxy
@Table("sys_role")
@EasyAlias("tRole")
@FieldNameConstants
public class Role implements ProxyEntityAvailable<Role, RoleProxy> {

    @Column(primaryKey = true)
    private Long id;

    /** 角色名称 */
    @Column("name")
    private String name;

    /** 权限值 */
    @Column("permission")
    private String permission;

    @Column("deleted")
    @LogicDelete(strategy = LogicDeleteStrategyEnum.DELETE_LONG_TIMESTAMP)
    private Long deleted;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = RoleJoin.class
            , selfMappingProperty = "roleId"
            , targetMappingProperty = "bizId"
            , extraFilter = RoleJoin.RoleJoinType.class)
    private List<Route> routes;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = RoleJoin.class
            , selfMappingProperty = "roleId"
            , targetMappingProperty = "bizId"
            , extraFilter = RoleJoin.RoleJoinType.class)
    private List<Resource> resources;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = RoleJoin.class
            , selfMappingProperty = "roleId"
            , targetMappingProperty = "bizId"
            , extraFilter = RoleJoin.RoleJoinType.class)
    private List<User> users;

    @Override
    public Class<RoleProxy> proxyTableClass() {
        return RoleProxy.class;
    }
}
