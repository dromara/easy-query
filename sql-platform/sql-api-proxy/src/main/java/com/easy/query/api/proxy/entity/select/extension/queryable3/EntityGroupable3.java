package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByFlat(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLGroupByExpression> selectExpression) {
        return groupByFlat(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByFlat(boolean condition, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLGroupByExpression> selectExpression) {
        if (condition) {
            getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
                SQLGroupByExpression sqlGroupSelect = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
                sqlGroupSelect.accept(selector1.getGroupSelector());
            });
        }
        return getQueryable3();
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByFlatMerge(SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, SQLGroupByExpression> selectExpression) {
        return groupByFlatMerge(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByFlatMerge(boolean condition, SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, SQLGroupByExpression> selectExpression) {
        return groupByFlat(condition, (t1, t2, t3) -> {
            return selectExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }


    default <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, TRProxy>> selectExpression) {

        SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, TRProxy> keysExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        TRProxy grouping1Proxy = keysExpression.apply(new MergeTuple3<>(get1Proxy(), get2Proxy(), get3Proxy()));

        getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
            grouping1Proxy.accept(selector1.getGroupSelector());
        });

        TRProxy groupProxy = grouping1Proxy.create(null, get1Proxy().getEntitySQLContext());
        EasyClientQueryable<TR> groupQueryable = new EasyClientQueryable<>(grouping1Proxy.getEntityClass(), getClientQueryable3().getSQLEntityExpressionBuilder());
        return new EasyEntityQueryable<>(groupProxy, groupQueryable);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupByMerge(SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, TRProxy>> selectExpression) {

        return groupBy((t1, t2, t3) -> selectExpression.apply(new MergeTuple3<>(t1, t2, t3)));
    }
}
