package com.easy.query.test.dto.autotest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dto.autotest.proxy.ResourceProxy;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2024/5/15 16:59
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Accessors(chain = true)
@EntityProxy
@Table("sys_resource")
@EasyAlias("tRes")
@FieldNameConstants
public class Resource implements ProxyEntityAvailable<Resource, ResourceProxy> {
    /** ID */
    @Column(primaryKey = true)
    private Long id;

    /** 接口名称 */
    @Column("name")
    private String name;

    /** 接口的url */
    @Column("path")
    private String path;

    /** 请求方式 */
    @Column("method")
    private String method;

    /** 权限标识,主要给前端识别，例子res:a:u:listPage */
    @Column("permission_mark")
    private String permissionMark;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = RoleJoin.class
            , selfMappingProperty = "bizId"
            , targetMappingProperty = "roleId"
            , extraFilter = RoleJoin.RoleJoinType.class)
    private List<Role> roles;

    @Override
    public Class<ResourceProxy> proxyTableClass() {
        return ResourceProxy.class;
    }
}
