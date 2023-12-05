package com.easy.query.api.proxy.entity.select.extension.queryable5;

import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression5;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientEntityQueryable5Available<T1, T2, T3, T4, T5>, EntityQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(SQLFuncExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, SQLPredicateExpression> whereExpression) {
        return where(true, whereExpression);
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(boolean condition, SQLFuncExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, SQLPredicateExpression> whereExpression) {
        if (condition) {
            getClientQueryable5().where((t, t1, t2, t3, t4) -> {
                SQLPredicateExpression sqlPredicateExpression = whereExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy());
                sqlPredicateExpression.accept(t.getFilter());
            });
        }
        return getQueryable5();
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> whereMerge(SQLFuncExpression1<Tuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>,SQLPredicateExpression> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> whereMerge(boolean condition, SQLFuncExpression1<Tuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>,SQLPredicateExpression> whereExpression) {
        return where(condition, (t, t1, t2, t3, t4) -> {
            return whereExpression.apply(new Tuple5<>(t, t1, t2, t3, t4));
        });
    }
}
