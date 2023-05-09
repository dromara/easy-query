package com.easy.query.core.api.dynamic.order;

import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;

/**
 * @FileName: EasyOrderBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:16
 * @author xuejiaming
 */
public interface EasyOrderByBuilder {
    <TEntity> EasyOrderByPropertyBuilder<TEntity> mapTo(Class<TEntity> entityClass);

    /**
     * 添加字段属性排序
     * @exception EasyQueryOrderByInvalidOperationException 当添加的属性 {@param propertyName} 不存在本次查询被允许的排序字段里面时抛出
     * @param propertyName
     * @param asc
     * @return
     */
    EasyOrderByBuilder orderBy(String propertyName, boolean asc);
}
