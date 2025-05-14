package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderBy(SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderBy(boolean condition, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        if (condition) {
            getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
                get1Proxy().getEntitySQLContext()._orderBy(selector1.getOrderSelector(),()->{
                    selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
                });
            });
        }
        return getQueryable3();
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByMerge(SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByMerge(boolean condition, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderBy(condition, (t1, t2, t3) -> {
            selectExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

}
