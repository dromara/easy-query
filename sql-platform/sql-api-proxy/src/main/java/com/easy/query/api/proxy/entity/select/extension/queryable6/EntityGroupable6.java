package com.easy.query.api.proxy.entity.select.extension.queryable6;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression6;
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
public interface EntityGroupable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientEntityQueryable6Available<T1, T2, T3, T4, T5, T6>, EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR>
    EntityQueryable<TRProxy, TR> groupBy(
            SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy,
                    SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, TRProxy>> selectExpression) {

        SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, TRProxy> keysExpression =
                selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
        Objects.requireNonNull(keysExpression,"groupBy result expression is null");
        TRProxy grouping1Proxy = keysExpression.apply(new MergeTuple6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy()));

        Objects.requireNonNull(grouping1Proxy,"groupBy result is null");
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        if (EasySQLSegmentUtil.isNotEmpty(sqlEntityExpressionBuilder.getGroup()) && EasySQLSegmentUtil.isEmpty(sqlEntityExpressionBuilder.getProjects())) {
            throw new EasyQueryInvalidOperationException("ENG:The [select] statement should be used between two consecutive [groupBy] statements to determine the query results of the preceding [groupBy].CN:连续两个[groupBy]之间应该使用[select]来确定前一次[groupBy]的查询结果");
        }
        getClientQueryable6().groupBy((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            grouping1Proxy.accept(selector1.getGroupSelector());
        });

        TRProxy groupProxy = grouping1Proxy.create(null, get1Proxy().getEntitySQLContext());
        EasyClientQueryable<TR> groupQueryable = new EasyClientQueryable<>(grouping1Proxy.getEntityClass(), getClientQueryable6().getSQLEntityExpressionBuilder());
        return new EasyEntityQueryable<>(groupProxy, groupQueryable);
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression, TR>
    EntityQueryable<TRProxy, TR> groupByMerge(
            SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>,
                    SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, TRProxy>> selectExpression) {

        return groupBy((t1, t2, t3, t4, t5, t6) -> selectExpression.apply(new MergeTuple6<>(t1, t2, t3, t4, t5, t6)));
    }

}
