package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.impl.EasySQLPredicateQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnIncludeColumn2Impl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.util.EasyClassUtil;

import java.math.BigDecimal;

/**
 * create time 2024/2/11 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable, PropColumn, LogicDeletable<SQLQueryable<T1Proxy, T1>> {//,ProxyEntity<T1Proxy,T1>

    EntityQueryable<T1Proxy, T1> getQueryable();

    TableAvailable getOriginalTable();

    String getNavValue();
    T1Proxy getProxy();

    @Override
   default String getValue(){
        return getNavValue();
    }

    default SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        getQueryable().where(whereExpression);
        return new EasySQLPredicateQueryable<>(this);
    }

    /**
     * 存在任意一个满足条件
     *
     * @param whereExpression
     */
    default void any(SQLExpression1<T1Proxy> whereExpression) {
        where(whereExpression).any();
    }


    /**
     * 存在任意一个满足条件
     */
    default void any() {
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(getQueryable().limit(1).select("1"))));
    }

    default void none(SQLExpression1<T1Proxy> whereExpression) {
        where(whereExpression).none();
    }

    default void none() {
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.none(getQueryable().limit(1).select("1"))));
    }

    default ColumnFunctionComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression) {
        Query<Long> longQuery = getQueryable().where(whereExpression).selectCount();
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }

    default ColumnFunctionComparableNumberChainExpression<Long> count() {
        return count(x -> {
        });
    }

    default ColumnFunctionComparableNumberChainExpression<Integer> intCount(SQLExpression1<T1Proxy> whereExpression) {
        Query<Integer> longQuery = getQueryable().where(whereExpression).selectCount(Integer.class);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Integer.class);
    }

    default ColumnFunctionComparableNumberChainExpression<Integer> intCount() {
        return intCount(x -> {
        });
    }

    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        Query<TMember> sumQuery = getQueryable().selectSum(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), sumQuery.getClass());
    }

    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        Query<TMember> sumQuery = getQueryable().selectSum(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }

    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        Query<BigDecimal> avgQuery = getQueryable().selectAvg(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(avgQuery).format(0)), BigDecimal.class);
    }

    default <TMember> ColumnFunctionComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        Query<TMember> maxQuery = getQueryable().selectMax(columnSelector);
        return minOrMax(maxQuery, this.getEntitySQLContext());
    }

    default <TMember> ColumnFunctionComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        Query<TMember> minQuery = getQueryable().selectMin(columnSelector);
        return minOrMax(minQuery, this.getEntitySQLContext());
    }

    static <TMember> ColumnFunctionComparableAnyChainExpression<TMember> minOrMax(Query<TMember> subQuery, EntitySQLContext entitySQLContext) {

        boolean numberType = EasyClassUtil.isNumberType(subQuery.getClass());
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            if (numberType) {
                return f.nullOrDefault(x -> x.subQuery(subQuery).format(0));
            } else {
                return f.subQueryValue(subQuery);
            }
        }, subQuery.getClass());
    }


    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> void set(SQLQueryable<TPropertyProxy, TProperty> columnProxy) {
        set(columnProxy, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> void set(SQLQueryable<TPropertyProxy, TProperty> columnProxy, SQLFuncExpression1<TPropertyProxy, ProxyEntity<T1Proxy, T1>> navigateSelectExpression) {
        getEntitySQLContext().accept(new SQLColumnIncludeColumn2Impl<>(columnProxy.getOriginalTable(), columnProxy.getNavValue(), getNavValue(), columnProxy.getQueryable().get1Proxy(), navigateSelectExpression));
    }

    /**
     * 仅支持toList查询返回展开
     * @return
     */
    default T1Proxy flatElement() {
        QueryRuntimeContext runtimeContext = this.getProxy().getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this,runtimeContext));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }
//    default T1Proxy flatElement(SQLExpression1<EntityQueryable<T1Proxy, T1>> flatAdapterExpression) {
//        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), getProxy().getEntitySQLContext());
//        tPropertyProxy.setNavValue(getNavValue());
//        return tPropertyProxy;
//    }
//    default T1Proxy flatElement1() {
//        QueryRuntimeContext runtimeContext = this.getProxy().getEntitySQLContext().getRuntimeContext();
//        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this,runtimeContext));
//        tPropertyProxy.setNavValue(getNavValue());
//        return tPropertyProxy;
//    }

//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> SQLQueryable<TPropertyProxy, TProperty> select(SQLFuncExpression1<T1Proxy, TPropertyProxy> selectColumnExpression) {
//        EntityQueryable<TPropertyProxy, TProperty> select = this.getQueryable().select(selectColumnExpression);
//        return new EasySQLQueryable<>(this.getEntitySQLContext(), select, this.getOriginalTable());
//    }
}
