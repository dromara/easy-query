package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.util.EasyClassUtil;

import java.math.BigDecimal;

/**
 * create time 2024/2/11 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable {
    EasyEntityQueryable<T1Proxy, T1> getQueryable();

    default SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        getQueryable().where(whereExpression);
        return this;
    }

    default void exists(SQLExpression1<T1Proxy> whereExpression) {
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(getQueryable().where(whereExpression).limit(1).select("1"))));
    }

    default void notExists(SQLExpression1<T1Proxy> whereExpression) {
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notExists(getQueryable().where(whereExpression).limit(1).select("1"))));
    }

    default ColumnFunctionComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression) {
        Query<Long> longQuery = getQueryable().where(whereExpression).selectCount();
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }
    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        Query<TMember> sumQuery = getQueryable().selectSum(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(0)), sumQuery.getClass());
    }
    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        Query<TMember> sumQuery = getQueryable().selectSum(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }
    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        Query<BigDecimal> sumQuery = getQueryable().selectAvg(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }
    default <TMember> ColumnFunctionComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        Query<TMember> sumQuery = getQueryable().selectMax(columnSelector);
        String defaultValue = EasyClassUtil.isNumberType(sumQuery.getClass()) ? "0" : "NULL";
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(defaultValue)), sumQuery.getClass());
    }
    default <TMember> ColumnFunctionComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        Query<TMember> sumQuery = getQueryable().selectMin(columnSelector);
        String defaultValue = EasyClassUtil.isNumberType(sumQuery.getClass()) ? "0" : "NULL";
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(defaultValue)), sumQuery.getClass());
    }
}
