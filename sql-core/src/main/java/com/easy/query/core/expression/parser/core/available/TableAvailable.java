package com.easy.query.core.expression.parser.core.available;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/5/1 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableAvailable{
    Class<?> getEntityClass();
    EntityMetadata getEntityMetadata();
    boolean hasSchema();
    String getSchema();
    String getTableName();
    String getColumnName(String propertyName);
    boolean isAnonymous();
    void asAlias(String alias);
    String getAlias();
    default boolean hasAlias(){
        return getAlias()!=null;
    }
}
