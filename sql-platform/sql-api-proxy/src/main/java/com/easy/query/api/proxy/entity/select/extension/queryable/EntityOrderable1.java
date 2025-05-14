package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    default EntityQueryable<T1Proxy, T1> orderBy(SQLActionExpression1<T1Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    EntityQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> selectExpression);

    /**
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default EntityQueryable<T1Proxy, T1> orderByObject(ObjectSort objectSort) {
        return orderByObject(true, objectSort);
    }

    /**
     * @param condition
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    EntityQueryable<T1Proxy, T1> orderByObject(boolean condition, ObjectSort objectSort);

}
