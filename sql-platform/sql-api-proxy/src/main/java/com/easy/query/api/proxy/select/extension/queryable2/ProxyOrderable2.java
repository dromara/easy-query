package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyOrderSelector2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxyOrderSelector2Impl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLOrderSelect;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(SQLOrderSelect... propColumns) {
        return orderBy(true, propColumns);
    }
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(boolean condition, SQLOrderSelect... propColumns){
        if (condition) {
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLOrderSelect propColumn : propColumns) {
                    getClientQueryable2().orderBy(columnSelector -> {
                        propColumn.accept(columnSelector.getOrderSelector());
                    }, true);
                }
            }
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(SQLExpression1<MultiProxyOrderSelector2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(boolean condition, SQLExpression1<MultiProxyOrderSelector2<T1Proxy, T2Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable2().orderByAsc((selector1, selector2) -> {
                selectExpression.apply(new MultiProxyOrderSelector2Impl<>(selector1.getOrderSelector(), get1Proxy(), get2Proxy()));
            });
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(SQLExpression1<MultiProxyOrderSelector2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(boolean condition, SQLExpression1<MultiProxyOrderSelector2<T1Proxy, T2Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable2().orderByDesc((selector1, selector2) -> {
                selectExpression.apply(new MultiProxyOrderSelector2Impl<>(selector1.getOrderSelector(), get1Proxy(), get2Proxy()));
            });
        }
        return getQueryable2();
    }

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByObject(boolean condition, ObjectSort objectSort){
        if (condition) {
            getClientQueryable2().orderByObject(objectSort);
        }
        return getQueryable2();
    }

}
