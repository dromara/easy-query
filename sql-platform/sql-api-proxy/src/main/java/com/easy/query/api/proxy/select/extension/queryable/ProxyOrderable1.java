package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyOrderSelector1;
import com.easy.query.api.proxy.select.extension.queryable.sql.impl.MultiProxyOrderSelector1Impl;
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
public interface ProxyOrderable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientProxyQueryableAvailable<T1>,ProxyQueryableAvailable<T1Proxy,T1>{


    default ProxyQueryable<T1Proxy, T1> orderBy(SQLOrderSelect... propColumns) {
        return orderBy(true, propColumns);
    }
    default ProxyQueryable<T1Proxy, T1> orderBy(boolean condition, SQLOrderSelect... propColumns){
        if (condition) {
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLOrderSelect propColumn : propColumns) {
                    getClientQueryable().orderBy(columnSelector -> {
                        propColumn.accept(columnSelector.getOrderSelector());
                    }, true);
                }
            }
        }
        return getQueryable();
    }



    default ProxyQueryable<T1Proxy, T1> orderByAsc(SQLExpression1<MultiProxyOrderSelector1<T1Proxy>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable<T1Proxy, T1> orderByAsc(boolean condition, SQLExpression1<MultiProxyOrderSelector1<T1Proxy>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default ProxyQueryable<T1Proxy, T1> orderByDesc(SQLExpression1<MultiProxyOrderSelector1<T1Proxy>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable<T1Proxy, T1> orderByDesc(boolean condition, SQLExpression1<MultiProxyOrderSelector1<T1Proxy>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default ProxyQueryable<T1Proxy, T1> orderBy(SQLExpression1<MultiProxyOrderSelector1<T1Proxy>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

   default ProxyQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<MultiProxyOrderSelector1<T1Proxy>> selectExpression, boolean asc){
       if (condition) {
           getClientQueryable().orderBy(columnSelector -> {
               selectExpression.apply(new MultiProxyOrderSelector1Impl<>(columnSelector.getOrderSelector(), get1Proxy()));
           }, asc);
       }
       return getQueryable();
   }
    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default ProxyQueryable<T1Proxy, T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
   default ProxyQueryable<T1Proxy, T1> orderByObject(boolean condition, ObjectSort objectSort){
       if (condition) {
           getClientQueryable().orderByObject(objectSort);
       }
       return getQueryable();
   }

}
