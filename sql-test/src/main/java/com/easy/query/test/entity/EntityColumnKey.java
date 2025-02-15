package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.EntityColumnKeyProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2025/2/15 15:43
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("test_table")
@Data
@EntityProxy
@ToString
public class EntityColumnKey implements ProxyEntityAvailable<EntityColumnKey , EntityColumnKeyProxy> {
    @Column(primaryKey = true)
    private String id;
    private String key;
}
