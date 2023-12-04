package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.MultiProxyOrderSelector6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.impl.MultiProxyOrderSelector6Impl;
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
public interface ProxyOrderable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4, T5, T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderBy(SQLOrderSelect... propColumns) {
        return orderBy(true, propColumns);
    }
    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderBy(boolean condition, SQLOrderSelect... propColumns){
        if (condition) {
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLOrderSelect propColumn : propColumns) {
                    getClientQueryable6().orderBy(columnSelector -> {
                        propColumn.accept(columnSelector.getOrderSelector());
                    }, true);
                }
            }
        }
        return getQueryable6();
    }
    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByAsc(SQLExpression1<MultiProxyOrderSelector6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByAsc(boolean condition, SQLExpression1<MultiProxyOrderSelector6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable6().orderByAsc((t, t1, t2, t3, t4, t5) -> {
                selectExpression.apply(new MultiProxyOrderSelector6Impl<>(t.getOrderSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy()));
            });
        }
        return getQueryable6();
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDesc(SQLExpression1<MultiProxyOrderSelector6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDesc(boolean condition, SQLExpression1<MultiProxyOrderSelector6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable6().orderByDesc((t, t1, t2, t3, t4, t5) -> {
                selectExpression.apply(new MultiProxyOrderSelector6Impl<>(t.getOrderSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy()));
            });
        }
        return getQueryable6();
    }
    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByObject(boolean condition, ObjectSort objectSort){
        if (condition) {
            getClientQueryable6().orderByObject(objectSort);
        }
        return getQueryable6();
    }

}
