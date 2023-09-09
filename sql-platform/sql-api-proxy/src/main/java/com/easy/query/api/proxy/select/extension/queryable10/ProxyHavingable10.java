package com.easy.query.api.proxy.select.extension.queryable10;

import com.easy.query.api.proxy.select.ProxyQueryable10;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression11;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable10<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9,
        T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> extends ClientProxyQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, ProxyQueryable10Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> {

    default ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> having(SQLExpression11<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    default ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> having(boolean condition, SQLExpression11<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> predicateExpression) {
        if (condition) {
            getClientQueryable10().having((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                predicateExpression.apply(new ProxyAggregateFilterImpl(t.getAggregateFilter()), getProxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
            });
        }
        return getQueryable10();
    }

    default ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> havingMerge(SQLExpression2<ProxyAggregateFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> havingMerge(boolean condition, SQLExpression2<ProxyAggregateFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> predicateExpression) {
        return having(condition, (filter, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            predicateExpression.apply(filter, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

}
