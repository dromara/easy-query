package com.easy.query.api.proxy.entity.select.extension.queryable6;

import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression6;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientEntityQueryable6Available<T1, T2, T3, T4, T5, T6>, EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> where(SQLActionExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> where(boolean condition, SQLActionExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> whereExpression) {
        if (condition) {
            getClientQueryable6().where((t, t1, t2, t3, t4, t5) -> {
                get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                    whereExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
                });
            });
        }
        return getQueryable6();
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereMerge(SQLActionExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> whereMerge(boolean condition, SQLActionExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> whereExpression) {
        return where(condition, (t, t1, t2, t3, t4, t5) -> {
            whereExpression.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }
}
