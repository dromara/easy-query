package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8RoleProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/3/15 12:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_role")
@EntityProxy
@FieldNameConstants
public class M8Role implements ProxyEntityAvailable<M8Role, M8RoleProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.ManyToMany, selfProperty = {M8Role.Fields.id}, selfMappingProperty = {M8RoleMenu.Fields.roleId}, mappingClass = M8RoleMenu.class, targetProperty = {M8Menu.Fields.id}, targetMappingProperty = {M8RoleMenu.Fields.menuId})
    private List<M8Menu> menus;
}
