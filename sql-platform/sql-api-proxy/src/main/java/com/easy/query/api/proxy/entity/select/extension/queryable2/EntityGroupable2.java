package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupSelect;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1,T2>, EntityQueryable2Available<T1Proxy,T1,T2Proxy,T2> {


    default EntityQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(SQLFuncExpression2<T1Proxy, T2Proxy, SQLGroupSelect> selectExpression) {
        return groupBy(true,selectExpression);
    }

    default EntityQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(boolean condition, SQLFuncExpression2<T1Proxy, T2Proxy, SQLGroupSelect> selectExpression) {
        if (condition) {
            getClientQueryable2().groupBy((selector1, selector2) -> {
                SQLGroupSelect sqlGroupSelect = selectExpression.apply(get1Proxy(), get2Proxy());
                sqlGroupSelect.accept(selector1.getGroupSelector());
            });
        }
        return getQueryable2();
    }

    default EntityQueryable2<T1Proxy,T1,T2Proxy,T2> groupByMerge(SQLFuncExpression1<Tuple2<T1Proxy, T2Proxy>,SQLGroupSelect> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default EntityQueryable2<T1Proxy,T1,T2Proxy,T2> groupByMerge(boolean condition, SQLFuncExpression1<Tuple2<T1Proxy, T2Proxy>,SQLGroupSelect> selectExpression) {
        return groupBy(condition, (t1, t2) -> {
            return selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
