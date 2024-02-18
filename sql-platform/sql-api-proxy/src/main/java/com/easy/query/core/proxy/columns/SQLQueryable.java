package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
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
public interface SQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable {
    EasyEntityQueryable<T1Proxy, T1> getQueryable();
    TableAvailable getOriginalTable();
    String getNavValue();

    void reply(EntityQueryable<T1Proxy, T1> queryable);

    SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression);


    SQLQueryable<T1Proxy, T1> limit(long rows);
    SQLQueryable<T1Proxy, T1> limit(long offset, long rows);

    /**
     * 存在任意一个满足条件
     * @param whereExpression
     */
    default void any(SQLExpression1<T1Proxy> whereExpression) {
        reply(getQueryable());
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(getQueryable().where(whereExpression).limit(1).select("1"))));
    }

    /**
     * 存在任意一个满足条件
     */
    default void any() {
        any(x->{});
    }

    default void none(SQLExpression1<T1Proxy> whereExpression) {
        reply(getQueryable());
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.none(getQueryable().where(whereExpression).limit(1).select("1"))));
    }

    default void none() {
        none(x->{});
    }

    default ColumnFunctionComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression) {
        reply(getQueryable());
        Query<Long> longQuery = getQueryable().where(whereExpression).selectCount();
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Long> count() {
       return count(x->{});
    }
    default ColumnFunctionComparableNumberChainExpression<Integer> intCount(SQLExpression1<T1Proxy> whereExpression) {
        reply(getQueryable());
        Query<Integer> longQuery = getQueryable().where(whereExpression).selectCount(Integer.class);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Integer.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Integer> intCount() {
        return intCount(x->{});
    }
    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        reply(getQueryable());
        Query<TMember> sumQuery = getQueryable().selectSum(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(0)), sumQuery.getClass());
    }
    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        reply(getQueryable());
        Query<TMember> sumQuery = getQueryable().selectSum(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }
    default <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        reply(getQueryable());
        Query<BigDecimal> avgQuery = getQueryable().selectAvg(columnSelector);
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x->x.subQuery(avgQuery).format(0)), BigDecimal.class);
    }
    default <TMember> ColumnFunctionComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        reply(getQueryable());
        Query<TMember> maxQuery = getQueryable().selectMax(columnSelector);
        return minOrMax(maxQuery,this.getEntitySQLContext());
    }
    default <TMember> ColumnFunctionComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy,TMember>> columnSelector) {
        reply(getQueryable());
        Query<TMember> minQuery = getQueryable().selectMin(columnSelector);
        return minOrMax(minQuery,this.getEntitySQLContext());
    }
    static <TMember> ColumnFunctionComparableAnyChainExpression<TMember> minOrMax(Query<TMember> subQuery, EntitySQLContext entitySQLContext){

        boolean numberType = EasyClassUtil.isNumberType(subQuery.getClass());
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            if(numberType){
                return f.nullOrDefault(x->x.subQuery(subQuery).format(0));
            }else{
                return f.subQueryValue(subQuery);
            }
        }, subQuery.getClass());
    }



    default  <TPropertyProxy extends ProxyEntity<TPropertyProxy,TProperty>, TProperty> void set(SQLQueryable<TPropertyProxy,TProperty> columnProxy) {
        set(columnProxy,null);
    }
    default  <TPropertyProxy extends ProxyEntity<TPropertyProxy,TProperty>, TProperty> void set(SQLQueryable<TPropertyProxy,TProperty> columnProxy,SQLFuncExpression1<ProxyEntity<TPropertyProxy, TProperty>, ProxyEntity<T1Proxy, T1>> navigateSelectExpression) {
        getEntitySQLContext().accept(new SQLColumnIncludeColumn2Impl<>(getOriginalTable(), columnProxy.getNavValue(), getNavValue(), columnProxy.getQueryable().get1Proxy(),navigateSelectExpression));
    }
}
