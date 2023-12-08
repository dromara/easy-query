package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(SQLExpression2<T1Proxy, T2Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderBy(boolean condition, SQLExpression2<T1Proxy, T2Proxy> selectExpression) {
        if (condition) {
            getClientQueryable2().orderByAsc((selector1, selector2) -> {
                get1Proxy().getEntitySQLContext()._orderBy(selector1.getOrderSelector(),()->{
                    selectExpression.apply(get1Proxy(), get2Proxy());
                });
            });
        }
        return getQueryable2();
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderByMerge(SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> orderByMerge(boolean condition, SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> selectExpression) {
        return orderBy(condition, (t1, t2) -> {
            selectExpression.apply(new MergeTuple2<>(t1, t2));
        });
    }

}
