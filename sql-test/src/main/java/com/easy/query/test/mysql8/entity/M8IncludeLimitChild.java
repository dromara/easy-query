package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8IncludeLimitChildProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2026/8/4
 * include limit 场景专用实体
 */
@Data
@EntityProxy
@Table("m8_include_limit_child")
@FieldNameConstants
public class M8IncludeLimitChild implements ProxyEntityAvailable<M8IncludeLimitChild, M8IncludeLimitChildProxy> {
    @Column(primaryKey = true)
    private String id;

    private String parentId;

    private String name;

    private Integer order;

    @Column(autoSelect = false)
    private Boolean deleted;
}
