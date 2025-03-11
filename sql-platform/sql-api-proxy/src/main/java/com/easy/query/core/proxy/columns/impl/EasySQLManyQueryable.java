package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;

/**
 * create time 2024/6/5 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLManyQueryable<TProxy, T1Proxy, T1> {
    private final EntitySQLContext entitySQLContext;
    private final EntityQueryable<T1Proxy, T1> easyEntityQueryable;
    private final TableAvailable originalTable;
    private TProxy tProxy;
    private boolean distinct = false;

    public EasySQLManyQueryable(EntitySQLContext entitySQLContext, EntityQueryable<T1Proxy, T1> easyEntityQueryable, TableAvailable originalTable) {

        this.entitySQLContext = entitySQLContext;
        this.easyEntityQueryable = easyEntityQueryable;
        this.originalTable = originalTable;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public EntityQueryable<T1Proxy, T1> getQueryable() {
        return easyEntityQueryable;
    }

    @Override
    public TableAvailable getOriginalTable() {
        return originalTable;
    }

    @Override
    public String getNavValue() {
        return easyEntityQueryable.get1Proxy().getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return easyEntityQueryable.get1Proxy();
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        this.distinct = useDistinct;
        return this;
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        getQueryable().where(whereExpression);
        return new EasySQLPredicateQueryable<>(this, this.distinct);
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        where(whereExpression).any();
    }

    @Override
    public void any() {
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(getQueryable().limit(1))));
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        where(whereExpression).none();
    }

    @Override
    public void none() {
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.none(getQueryable().limit(1))));
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        Query<?> anyQuery = getQueryable().limit(1).select("1");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.exists(anyQuery), Boolean.class);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        Query<?> anyQuery = getQueryable().limit(1).select("1");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.not(f.exists(anyQuery)), Boolean.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        Query<TMember> longQuery = getQueryable().selectCount(columnSelector, distinct);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        Query<?> longQuery = getQueryable().selectCount();
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
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
        Query<TMember> sumQuery = staticSum(getQueryable(), columnSelector, distinct, null);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), sumQuery.queryClass());
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        Query<TMember> sumQuery = staticSum(getQueryable(), columnSelector, distinct, BigDecimal.class);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        Query<BigDecimal> avgQuery = staticAvg(getQueryable(), columnSelector, distinct);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(avgQuery).format(0)), BigDecimal.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        Query<TMember> maxQuery = staticMinOrMax(getQueryable(), columnSelector, true);
        return minOrMax(maxQuery, this.getEntitySQLContext());
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        Query<TMember> minQuery = staticMinOrMax(getQueryable(), columnSelector, false);
        return minOrMax(minQuery, this.getEntitySQLContext());
    }

    @Override
    public ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        Query<String> joiningQuery = staticJoining(getQueryable(), columnSelector, delimiter, distinct);
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.anySQLFunction("{0}",x -> x.subQuery(joiningQuery)), String.class);
    }

    @Override
    public SQLQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
        easyEntityQueryable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void _setProxy(TProxy tProxy) {
        this.tProxy = tProxy;
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this, runtimeContext, flatAdapterExpression));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }


    /**
     * 静态方法封装对sum的处理
     *
     * @param entityQueryable
     * @param columnSelector
     * @param distinct
     * @param propertyType    null那么就使用columnSelector.getPropertyType()
     * @param <TProxy>
     * @param <T>
     * @param <TMember>
     * @return
     */
    static <TProxy extends ProxyEntity<TProxy, T>, T, TMember extends Number> Query<TMember> staticSum(EntityQueryable<TProxy, T> entityQueryable, SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector, boolean distinct, Class<?> propertyType) {
        return entityQueryable.selectColumn(s -> {
            ColumnNumberFunctionAvailable<TMember> apply = columnSelector.apply(s);
            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, apply);
                }).distinct(distinct);
            }, EasyObjectUtil.nullToDefault(propertyType, apply.getPropertyType()));
        });
    }

    static <TProxy extends ProxyEntity<TProxy, T>, T, TMember extends Number> Query<String> staticJoining(EntityQueryable<TProxy, T> entityQueryable, SQLFuncExpression1<TProxy, PropTypeColumn<String>> columnSelector, String delimiter, boolean distinct) {
        return entityQueryable.selectColumn(s -> {
            PropTypeColumn<String> column = columnSelector.apply(s);
            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
                return fx.joining(x -> {
                    x.value(delimiter);
                    PropTypeColumn.columnFuncSelector(x, column);
                }, distinct);
            }, BigDecimal.class);
        });
    }

    static <TProxy extends ProxyEntity<TProxy, T>, T, TMember extends Number> Query<BigDecimal> staticAvg(EntityQueryable<TProxy, T> entityQueryable, SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector, boolean distinct) {
        return entityQueryable.selectColumn(s -> {
            ColumnNumberFunctionAvailable<TMember> apply = columnSelector.apply(s);
            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, apply);
                }).distinct(distinct);
            }, BigDecimal.class);
        });
    }

    static <TProxy extends ProxyEntity<TProxy, T>, T, TMember> Query<TMember> staticMinOrMax(EntityQueryable<TProxy, T> entityQueryable, SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector, boolean max) {
        return entityQueryable.selectColumn(s -> {
            PropTypeColumn<TMember> apply = columnSelector.apply(s);
            return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
                if (max) {
                    return fx.max(x -> {
                        PropTypeColumn.columnFuncSelector(x, apply);
                    });
                } else {
                    return fx.min(x -> {
                        PropTypeColumn.columnFuncSelector(x, apply);
                    });
                }
            }, apply.getPropertyType());
        });
    }

    static <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> minOrMax(Query<TMember> subQuery, EntitySQLContext entitySQLContext) {

        boolean numberType = EasyClassUtil.isNumberType(subQuery.getClass());
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            if (numberType) {
                return f.nullOrDefault(x -> x.subQuery(subQuery).format(0));
            } else {
                return f.subQueryValue(subQuery);
            }
        }, subQuery.queryClass());
    }

}
