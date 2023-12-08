package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2
        , T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where(boolean condition, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> whereExpression) {
        if (condition) {
            getClientQueryable4().where((t, t1, t2, t3) -> {
                get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                    whereExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
                });
            });
        }
        return getQueryable4();
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereMerge(SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> whereMerge(boolean condition, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> whereExpression) {
        return where(condition, (t, t1, t2, t3) -> {
            whereExpression.apply(new MergeTuple4<>(t, t1, t2, t3));
        });
    }
}
