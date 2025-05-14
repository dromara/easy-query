package com.easy.query.api.proxy.entity.select.extension.queryable5;

import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.core.common.tuple.MergeTuple5;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientEntityQueryable5Available<T1, T2, T3, T4, T5>, EntityQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> orderBy(SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> orderBy(boolean condition, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> selectExpression) {
        if (condition) {
            getClientQueryable5().orderByAsc((t, t1, t2, t3, t4) -> {
                get1Proxy().getEntitySQLContext()._orderBy(t.getOrderSelector(), () -> {
                    selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy());
                });
            });
        }
        return getQueryable5();
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> orderByMerge(SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> orderByMerge(boolean condition, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> selectExpression) {
        return orderBy(condition, (t, t1, t2, t3, t4) -> {
            selectExpression.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

}
