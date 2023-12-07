package com.easy.query.api.proxy.entity.select.extension.queryable8;

import com.easy.query.api.proxy.entity.select.EntityQueryable8;
import com.easy.query.core.common.tuple.MergeTuple8;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression8;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends ClientEntityQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, EntityQueryable8Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {


    default EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupBy(SQLFuncExpression8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy,SQLGroupByExpression> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupBy(boolean condition, SQLFuncExpression8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy,SQLGroupByExpression> selectExpression) {
        if (condition) {
            getClientQueryable8().groupBy((t, t1, t2, t3, t4, t5, t6, t7) -> {
                SQLGroupByExpression sqlGroupByExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy());
                sqlGroupByExpression.accept(t.getGroupSelector());
            });
        }
        return getQueryable8();
    }

    default EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupByMerge(SQLFuncExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>,SQLGroupByExpression> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupByMerge(boolean condition, SQLFuncExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>,SQLGroupByExpression> selectExpression) {
        return groupBy(condition, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            return selectExpression.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }
}
