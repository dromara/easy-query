package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLFuncExpression2<T1Proxy, T2Proxy, SQLPredicate> whereExpression) {
        return where(true, whereExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLFuncExpression2<T1Proxy, T2Proxy, SQLPredicate> whereExpression) {
        if (condition) {
            getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                SQLPredicate sqlPredicate = whereExpression.apply(get1Proxy(), get2Proxy());
                sqlPredicate.accept(wherePredicate1.getFilter());
            });
        }
        return getQueryable2();
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(SQLFuncExpression1<Tuple2<T1Proxy, T2Proxy>, SQLPredicate> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(boolean condition, SQLFuncExpression1<Tuple2<T1Proxy, T2Proxy>, SQLPredicate> whereExpression) {
        return where(condition, (t1, t2) -> {
            return whereExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
