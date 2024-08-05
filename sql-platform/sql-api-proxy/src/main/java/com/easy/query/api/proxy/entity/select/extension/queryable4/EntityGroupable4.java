package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Objects;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, TRProxy>> selectExpression) {

        SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, TRProxy> keysExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
        Objects.requireNonNull(keysExpression,"groupBy result expression is null");
        TRProxy grouping1Proxy = keysExpression.apply(new MergeTuple4<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy()));

        Objects.requireNonNull(grouping1Proxy,"groupBy result is null");
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable4().getSQLEntityExpressionBuilder();
        if (EasySQLSegmentUtil.isNotEmpty(sqlEntityExpressionBuilder.getGroup()) && EasySQLSegmentUtil.isEmpty(sqlEntityExpressionBuilder.getProjects())) {
            throw new EasyQueryInvalidOperationException("ENG:The [select] statement should be used between two consecutive [groupBy] statements to determine the query results of the preceding [groupBy].CN:连续两个[groupBy]之间应该使用[select]来确定前一次[groupBy]的查询结果");
        }
        getClientQueryable4().groupBy((selector1, selector2, selector3, selector4) -> {
            grouping1Proxy.accept(selector1.getGroupSelector());
        });

        TRProxy groupProxy = grouping1Proxy.create(null, get1Proxy().getEntitySQLContext());
        EasyClientQueryable<TR> groupQueryable = new EasyClientQueryable<>(grouping1Proxy.getEntityClass(), getClientQueryable4().getSQLEntityExpressionBuilder());
        return new EasyEntityQueryable<>(groupProxy, groupQueryable);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR> EntityQueryable<TRProxy, TR> groupByMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, TRProxy>> selectExpression) {

        return groupBy((t1, t2, t3, t4) -> selectExpression.apply(new MergeTuple4<>(t1, t2, t3, t4)));
    }
}
