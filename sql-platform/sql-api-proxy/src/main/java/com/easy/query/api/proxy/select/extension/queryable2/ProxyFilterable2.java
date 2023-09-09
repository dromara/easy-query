package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFilterable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> whereExpression) {
        return where(true,whereExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> whereExpression) {
        if (condition) {
            getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                whereExpression.apply(new ProxyFilterImpl(wherePredicate2.getFilter()), get1Proxy(), get2Proxy());
            });
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(SQLExpression2<ProxyFilter,Tuple2<T1Proxy, T2Proxy>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(boolean condition, SQLExpression2<ProxyFilter,Tuple2<T1Proxy, T2Proxy>> whereExpression) {
        return where(condition, (filter,t1, t2) -> {
            whereExpression.apply(filter,new Tuple2<>(t1, t2));
        });
    }
}
