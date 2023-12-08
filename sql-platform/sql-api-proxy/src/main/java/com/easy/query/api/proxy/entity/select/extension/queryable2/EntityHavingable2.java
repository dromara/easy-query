package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> having(SQLExpression2<T1Proxy, T2Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> having(boolean condition, SQLExpression2<T1Proxy, T2Proxy> predicateExpression) {
        if (condition) {
            getClientQueryable2().having((predicate1, predicate2) -> {
                get1Proxy().getEntitySQLContext()._having(predicate1.getAggregateFilter(), () -> {
                    predicateExpression.apply(get1Proxy(), get2Proxy());
                });
            });
        }
        return getQueryable2();
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> havingMerge(SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> havingMerge(boolean condition, SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> predicateExpression) {
        return having(condition, (t1, t2) -> {
            predicateExpression.apply(new MergeTuple2<>(t1, t2));
        });
    }

}
