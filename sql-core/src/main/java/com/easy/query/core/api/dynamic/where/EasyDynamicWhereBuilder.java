package com.easy.query.core.api.dynamic.where;

/**
 * @FileName: EasyDynamicWhereBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/24 07:54
 * @Created by xuejiaming
 */
public interface EasyDynamicWhereBuilder<TObject> {
    <TEntity> EasyDynamicWherePropertyBuilder<TEntity,TObject> entity(Class<TEntity> entityClass);
}
