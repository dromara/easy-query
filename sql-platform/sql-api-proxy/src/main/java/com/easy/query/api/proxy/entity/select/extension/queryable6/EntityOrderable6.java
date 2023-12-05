package com.easy.query.api.proxy.entity.select.extension.queryable6;

import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression6;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLOrderByExpression;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientEntityQueryable6Available<T1, T2, T3, T4, T5, T6>, EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderBy(SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, SQLOrderByExpression> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderBy(boolean condition, SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, SQLOrderByExpression> selectExpression) {
        if (condition) {
            getClientQueryable6().orderByAsc((t, t1, t2, t3, t4, t5) -> {
                SQLOrderByExpression sqlOrderByExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
                sqlOrderByExpression.accept(t.getOrderSelector());
            });
        }
        return getQueryable6();
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByMerge(SQLFuncExpression1<Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, SQLOrderByExpression> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByMerge(boolean condition, SQLFuncExpression1<Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, SQLOrderByExpression> selectExpression) {
        return orderBy(condition, (t, t1, t2, t3, t4, t5) -> {
            return selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDesc(SQLExpression7<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDesc(boolean condition, SQLExpression7<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> selectExpression) {
        if (condition) {
            getClientQueryable6().orderByDesc((t, t1, t2, t3, t4, t5) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(t.getOrderSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
            });
        }
        return getQueryable6();
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDescMerge(SQLExpression2<ProxyOrderSelector, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> orderByDescMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        return orderByDesc(condition, (selector, t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(selector, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

}
