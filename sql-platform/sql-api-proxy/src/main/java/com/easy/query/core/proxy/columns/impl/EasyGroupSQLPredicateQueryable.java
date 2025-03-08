package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;

import java.math.BigDecimal;

/**
 * create time 2024/2/24 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyGroupSQLPredicateQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLPredicateQueryable<T1Proxy, T1> {
    private final ManyGroupJoinContext<T1Proxy,T1> manyGroupJoinContext;
    private boolean distinct;

    public EasyGroupSQLPredicateQueryable(SQLExpression1<T1Proxy> manyGroupJoinWhereExpression,ManyGroupJoinContext<T1Proxy,T1> manyGroupJoinContext, boolean distinct) {

        this.manyGroupJoinContext = manyGroupJoinContext;
        this.manyGroupJoinContext.appendWhere(manyGroupJoinWhereExpression);
        this.distinct = distinct;
    }
    @Override
    public EntitySQLContext getEntitySQLContext() {
        return manyGroupJoinContext.getEntitySQLContext();
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        this.distinct = useDistinct;
        return this;
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        this.manyGroupJoinContext.appendWhere(whereExpression);
        return this;
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        this.manyGroupJoinContext.anyValue(whereExpression).eq(true);
    }

    @Override
    public void any() {
        anyValue().eq(true);
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        this.manyGroupJoinContext.noneValue(whereExpression).eq(true);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return this.manyGroupJoinContext.anyValue(null);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return this.manyGroupJoinContext.noneValue(null);
    }

    @Override
    public void none() {
        noneValue().eq(true);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), manyGroupJoinContext.getT1Proxy().getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinWhereExpression()).distinct(distinct).count(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), manyGroupJoinContext.getT1Proxy().getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinWhereExpression()).distinct(distinct).count();
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return count(columnSelector).asAnyType(Integer.class);
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount() {
        return count().asAnyType(Integer.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<TMember> sum = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), manyGroupJoinContext.getT1Proxy().getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinWhereExpression()).distinct(distinct).sum(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(sum, "sum");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);

    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), manyGroupJoinContext.getT1Proxy().getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinWhereExpression()).distinct(distinct).avg(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(avg, "avg");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> max = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), manyGroupJoinContext.getT1Proxy().getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinWhereExpression()).max(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(max, "max");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), max.getPropertyType());

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> min = new DefaultSQLGroupQueryable<>(manyGroupJoinContext.getT1Proxy(), manyGroupJoinContext.getT1Proxy().getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinWhereExpression()).min(columnSelector);
        String alias = manyGroupJoinContext.getOrAppendGroupProjects(min, "min");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyGroupJoinContext.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), min.getPropertyType());

    }
//    @Override
//    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
//        return sqlQueryable.sum(columnSelector);
//    }
//
//    @Override
//    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.sumBigDecimal(columnSelector);
//    }
//
//    @Override
//    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.avg(columnSelector);
//    }
//
//    @Override
//    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.max(columnSelector);
//    }
//
//    @Override
//    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.max(columnSelector);
//    }
}
