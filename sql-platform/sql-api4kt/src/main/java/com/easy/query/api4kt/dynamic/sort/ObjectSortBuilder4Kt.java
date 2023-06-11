package com.easy.query.api4kt.dynamic.sort;

import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/11 11:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ObjectSortBuilder4Kt<TEntity> {
    /**
     * 添加字段属性排序
     *
     * @param propertyName
     * @param asc
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当添加的属性 {@param propertyName} 不存在本次查询被允许的排序字段里面时抛出
     */
    ObjectSortBuilder4Kt<TEntity> orderBy(String propertyName, boolean asc);

    ObjectSortBuilder4Kt<TEntity> allowed(KProperty1<TEntity, ?> property);

    ObjectSortBuilder4Kt<TEntity> notAllowed(KProperty1<TEntity, ?> property);

}
