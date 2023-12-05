package com.easy.query.api.proxy.entity.select.extension.queryable10;

import com.easy.query.api.proxy.entity.select.EntityQueryable10;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression10;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable10<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9,
        T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> extends ClientEntityQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, EntityQueryable10Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> {

    default EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> where(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLPredicateExpression> whereExpression) {
        return where(true, whereExpression);
    }

    default EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> where(boolean condition, SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLPredicateExpression> whereExpression) {
        if (condition) {
            getClientQueryable10().where((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                SQLPredicateExpression sqlPredicateExpression = whereExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
                sqlPredicateExpression.accept(t.getFilter());
            });
        }
        return getQueryable10();
    }

    default EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> whereMerge(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLPredicateExpression> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> whereMerge(boolean condition, SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLPredicateExpression> whereExpression) {
        return where(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            return whereExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
