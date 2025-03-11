package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;

import java.math.BigDecimal;

/**
 * create time 2024/2/24 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyGroupSQLPredicateQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLPredicateQueryable<T1Proxy, T1> {
    private final ManyJoinPredicateToGroupProjectProvider<T1Proxy,T1> manyJoinPredicateToGroupProjectProvider;
    private boolean distinct;

    public EasyGroupSQLPredicateQueryable(SQLExpression1<T1Proxy> manyGroupJoinWhereExpression, ManyJoinPredicateToGroupProjectProvider<T1Proxy,T1> manyJoinPredicateToGroupProjectProvider, boolean distinct) {

        this.manyJoinPredicateToGroupProjectProvider = manyJoinPredicateToGroupProjectProvider;
        this.manyJoinPredicateToGroupProjectProvider.appendWhere(manyGroupJoinWhereExpression);
        this.distinct = distinct;
    }
    @Override
    public EntitySQLContext getEntitySQLContext() {
        return manyJoinPredicateToGroupProjectProvider.getEntitySQLContext();
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        this.distinct = useDistinct;
        return this;
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        this.manyJoinPredicateToGroupProjectProvider.appendWhere(whereExpression);
        return this;
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        this.manyJoinPredicateToGroupProjectProvider.anyValue(whereExpression).eq(true);
    }

    @Override
    public void any() {
        anyValue().eq(true);
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        this.manyJoinPredicateToGroupProjectProvider.noneValue(whereExpression).eq(true);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return this.manyJoinPredicateToGroupProjectProvider.anyValue(null);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return this.manyJoinPredicateToGroupProjectProvider.noneValue(null);
    }

    @Override
    public void none() {
        noneValue().eq(true);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).distinct(distinct).count(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).distinct(distinct).count();
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Long.class);

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
        ColumnFunctionCompareComparableNumberChainExpression<TMember> sum = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).distinct(distinct).sum(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(sum, "sum");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);

    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).distinct(distinct).avg(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(avg, "avg");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), BigDecimal.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> max = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).max(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(max, "max");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), max.getPropertyType());

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> min = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).min(columnSelector);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(min, "min");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), min.getPropertyType());

    }

    @Override
    public ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {

        ColumnFunctionCompareComparableStringChainExpression<String> joining = new DefaultSQLGroupQueryable<>(manyJoinPredicateToGroupProjectProvider.getT1Proxy(), manyJoinPredicateToGroupProjectProvider.getT1Proxy().getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinWhereExpression()).distinct(distinct).joining(columnSelector,delimiter);
        String alias = manyJoinPredicateToGroupProjectProvider.getOrAppendGroupProjects(joining, "joining");
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), manyJoinPredicateToGroupProjectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), joining.getPropertyType());

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
