package com.easy.query.core.expression.parser.core.available;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/5/1 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableAvailable extends IndexAvailable{
    Class<?> getEntityClass();
    EntityMetadata getEntityMetadata();
    boolean hasSchema();
    String getSchema();
    String getTableName();
    String getAlias();
    String getColumnName(String propertyName);
}
