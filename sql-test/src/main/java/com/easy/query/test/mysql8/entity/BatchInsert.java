package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.BatchInsertProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/8/25 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_batch")
@FieldNameConstants
public class BatchInsert implements ProxyEntityAvailable<BatchInsert , BatchInsertProxy> {
    @Column(primaryKey = true,generatedKey = true)
    private Long id;
    private String name;
}
