package com.easy.query.core.api.dynamic.condition;

/**
 * @FileName: EasyDynamicWhereBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/24 07:54
 * @author xuejiaming
 */
public interface ObjectQueryBuilder<TObject> {
    <TEntity> ObjectQueryPropertyBuilder<TEntity,TObject> mapTo(Class<TEntity> entityClass);
}
