package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8MenuProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/4/19 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_menu")
@FieldNameConstants
public class M8Menu implements ProxyEntityAvailable<M8Menu, M8MenuProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String path;
    private String ownerId;

    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = {M8Menu.Fields.ownerId}, targetProperty = {M8MenuOwner.Fields.id})
    private M8MenuOwner menuOwner;

}
