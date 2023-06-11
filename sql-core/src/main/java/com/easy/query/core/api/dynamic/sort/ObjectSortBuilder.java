package com.easy.query.core.api.dynamic.sort;

import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;

import java.util.Map;

/**
 * @FileName: EasyOrderBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:16
 * @author xuejiaming
 */
public interface ObjectSortBuilder {

    /**
     * 添加字段属性排序
     * @exception EasyQueryOrderByInvalidOperationException 当添加的属性 {@param propertyName} 不存在本次查询被允许的排序字段里面时抛出
     * @param propertyName
     * @param asc
     * @return
     */
    ObjectSortBuilder orderBy(String propertyName, boolean asc);
    ObjectSortBuilder allowed(String propertyName);
    ObjectSortBuilder notAllowed(String propertyName);
    Map<String, Boolean> build();
}
