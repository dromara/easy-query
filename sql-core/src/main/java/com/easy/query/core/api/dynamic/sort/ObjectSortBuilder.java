package com.easy.query.core.api.dynamic.sort;

import com.easy.query.core.api.dynamic.sort.internal.ObjectSortEntry;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.func.def.enums.OrderByModeEnum;

import java.util.Map;

/**
 * @author xuejiaming
 * @FileName: EasyOrderBuilder.java
 * @Description: 文件说明
 * create time 2023/3/23 21:16
 */
public interface ObjectSortBuilder {

    /**
     * 添加字段属性排序
     *
     * @param propertyName
     * @param asc
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当添加的属性 {@param propertyName} 不存在本次查询被允许的排序字段里面时抛出
     */
    default ObjectSortBuilder orderBy(String propertyName, boolean asc) {
        return orderBy(propertyName, asc, 0);
    }

    /**
     * 添加字段属性排序
     *
     * @param propertyName
     * @param asc
     * @param tableIndex   第几个表,默认0表就是主表
     * @return
     */
    ObjectSortBuilder orderBy(String propertyName, boolean asc, int tableIndex);

    default ObjectSortBuilder orderBy(String propertyName, boolean asc, OrderByModeEnum orderByMode) {
        return orderBy(propertyName, asc, orderByMode, 0);
    }

    ObjectSortBuilder orderBy(String propertyName, boolean asc, OrderByModeEnum orderByMode, int tableIndex);

    /**
     * 如果调用过一次 allowed 那么所有的属性都必须在allowed里面才会生效
     *
     * @param propertyName
     * @return
     */
    ObjectSortBuilder allowed(String propertyName);

    ObjectSortBuilder notAllowed(String propertyName);

    Map<String, ObjectSortEntry> build();
}
