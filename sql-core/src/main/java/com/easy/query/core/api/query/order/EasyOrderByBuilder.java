package com.easy.query.core.api.query.order;

/**
 * @FileName: EasyOrderBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:16
 * @Created by xuejiaming
 */
public interface EasyOrderByBuilder {
    <TEntity> EasyOrderByPropertyBuilder<TEntity> entity(Class<TEntity> entityClass);
    EasyOrderByBuilder orderBy(String propertyName,boolean asc);
}
