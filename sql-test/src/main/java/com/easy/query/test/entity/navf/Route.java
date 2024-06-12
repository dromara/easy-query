package com.easy.query.test.entity.navf;


import com.easy.query.core.annotation.*;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.navf.proxy.RouteProxy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * 后端接口路由表
 */
@Getter
@Setter
@Accessors(chain = true)
@EntityProxy
@Table("sys_route")
@EasyAlias("tRoute")
@FieldNameConstants
public class Route implements ProxyEntityAvailable<Route , RouteProxy> {


    @Column(primaryKey = true)
    private Long id;

    /** 资源名称 */
    @Column("name")
    private String name;

    /** 资源类型{0：目录，1：url，2：按钮} */
    @Column("type")
    private Integer type;

    /** 浏览器path */
    @Column("path")
    private String path;

    /** 组件路径 */
    @Column("component")
    private String component;

    /** 描述 */
    @Column("describe")
    private String describe;

    /** 排序 */
    @Column("sort")
    private Integer sort;

    /** 父节点 */
    @Column("parent_id")
    private Long parentId;

    /** 是否显示{0:显示,1:隐藏} */
    @Column("hidden")
    private Boolean hidden;

    /** 触发父级地址 */
    @Column("active_menu")
    private String activeMenu;

    /** 重定向地址 */
    @Column("redirect")
    private String redirect;

    @Column("deleted")
    @LogicDelete(strategy = LogicDeleteStrategyEnum.DELETE_LONG_TIMESTAMP)
    private Long deleted;

    /** 图标 */
    @Column("icon")
    private String icon;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = RoleJoin.class
            , selfMappingProperty = "bizId"
            , targetMappingProperty = "roleId"
            , extraFilter = RoleJoin.RoleJoinType.class)
    private List<Role> roles;

}
