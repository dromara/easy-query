package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8IncludeLimitTopicProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2026/8/4
 * include limit 场景专用实体,包含 autoSelect=false 列用于复现 include+limit 场景的 Unknown column 缺陷
 */
@Data
@EntityProxy
@Table("m8_include_limit_topic")
@FieldNameConstants
public class M8IncludeLimitTopic implements ProxyEntityAvailable<M8IncludeLimitTopic, M8IncludeLimitTopicProxy> {
    @Column(primaryKey = true)
    private String id;

    private String name;

    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {M8IncludeLimitTopic.Fields.id},
            targetProperty = {M8IncludeLimitChild.Fields.parentId},
            orderByProps = {
                    @OrderByProperty(property = "order")
            })
    private List<M8IncludeLimitChild> children;
}
