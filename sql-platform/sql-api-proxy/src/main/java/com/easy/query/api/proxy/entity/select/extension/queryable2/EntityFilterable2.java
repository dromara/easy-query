package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression2<T1Proxy, T2Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression2<T1Proxy, T2Proxy> whereExpression) {
        if (condition) {
            getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                get1Proxy().getEntitySQLContext()._where(wherePredicate1.getFilter(),()->{
                    whereExpression.apply(get1Proxy(), get2Proxy());
                });
            });
        }
        return getQueryable2();
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(boolean condition, SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> whereExpression) {
        return where(condition, (t1, t2) -> {
            whereExpression.apply(new MergeTuple2<>(t1, t2));
        });
    }
}
