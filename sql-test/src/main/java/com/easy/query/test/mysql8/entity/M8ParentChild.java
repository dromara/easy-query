package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8ParentChildProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/5/7 15:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_parent_child")
@FieldNameConstants
public class M8ParentChild implements ProxyEntityAvailable<M8ParentChild , M8ParentChildProxy> {
    @Column(primaryKey = true)
    private String id;
    private String parentId;
    private String childId;
}
