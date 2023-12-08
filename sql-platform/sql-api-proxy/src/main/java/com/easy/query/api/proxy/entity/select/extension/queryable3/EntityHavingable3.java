package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(SQLExpression3<T1Proxy, T2Proxy, T3Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression3<T1Proxy, T2Proxy, T3Proxy> predicateExpression) {
        if (condition) {
            getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
                get1Proxy().getEntitySQLContext()._having(predicate1.getAggregateFilter(),()->{
                    predicateExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
                });
            });
        }
        return getQueryable3();
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> havingMerge(SQLExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> havingMerge(boolean condition, SQLExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> predicateExpression) {
        return having(condition, (t1, t2, t3) -> {
             predicateExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

}
