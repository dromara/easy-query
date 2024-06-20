package com.easy.query.core.basic.extension.generated;

import com.easy.query.core.metadata.ColumnMetadata;

import java.io.Serializable;

/**
 * create time 2024/6/20 22:23
 * 逐渐生成器在拦截器调用前生效
 *
 * @author xuejiaming
 */
public interface PrimaryKeyGenerator {
    /**
     * 返回一个主键
     *
     * @return
     */
    Serializable getPrimaryKey();

    /**
     * 如何设置主键,默认无脑设置
     * @param entity
     * @param columnMetadata
     */
    default void setPrimaryKey(Object entity, ColumnMetadata columnMetadata) {
        Serializable primaryKey = getPrimaryKey();
        columnMetadata.getSetterCaller().call(entity, primaryKey);
    }

}
