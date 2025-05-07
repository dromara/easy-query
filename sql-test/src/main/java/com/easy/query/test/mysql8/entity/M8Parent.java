package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8ParentProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2025/5/7 14:58
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_parent")
@FieldNameConstants
public class M8Parent implements ProxyEntityAvailable<M8Parent , M8ParentProxy> {
    @Column(primaryKey = true)
    private String id;

    private String name;

    private Integer order;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8Parent.Fields.id}, targetProperty = {M8Child.Fields.parentId},
    orderByProps = {
            @OrderByProperty(property = "order")
    })
    private List<M8Child> children;


    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {M8Parent.Fields.id},
            selfMappingProperty = {M8ParentChild.Fields.parentId},
            mappingClass = M8ParentChild.class,
            targetProperty = {M8Child.Fields.id},
            targetMappingProperty = {M8ParentChild.Fields.childId},
            orderByProps = {
                    @OrderByProperty(property = "order")
            })
    private List<M8Child> children2;
}
