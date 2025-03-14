package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SubQueryContext;
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
public class EasySQLManyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {
    private final SubQueryContext<T1Proxy, T1> subqueryContext;
    private final EntityQueryable<T1Proxy, T1> easyEntityQueryable;

    public EasySQLManyQueryable(SubQueryContext<T1Proxy, T1> subqueryContext, EntityQueryable<T1Proxy, T1> easyEntityQueryable) {
        this.subqueryContext = subqueryContext;
        this.easyEntityQueryable = easyEntityQueryable;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return subqueryContext.getEntitySQLContext();
    }

    @Override
    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return subqueryContext;
    }

    @Override
    public TableAvailable getOriginalTable() {
        return subqueryContext.getLeftTable();
    }

//    @Override
//    public T1Proxy element(int index) {
//        //添加当前表的partition row_number=0 join当前表作为子表生成relationTableKey
//        EasyRelationalUtil.getManyJoinRelationTable()
//        return null;
//    }

    @Override
    public String getNavValue() {
        return subqueryContext.getFullName();
    }

    @Override
    public String getValue() {
        return subqueryContext.getProperty();
    }

    @Override
    public T1Proxy getProxy() {
        return easyEntityQueryable.get1Proxy();
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        this.getSubQueryContext().distinct(useDistinct);
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> orderExpression) {
        this.getSubQueryContext().appendOrderByExpression(orderExpression);
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        this.getSubQueryContext().appendWhereExpression(whereExpression);
        return this;
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        where(whereExpression).any();
    }

    private void queryableAcceptExpression() {
        if (this.subqueryContext.getConfigureExpression() != null) {
            this.subqueryContext.getConfigureExpression().apply(this.easyEntityQueryable);
        }
        if (this.subqueryContext.getWhereExpression() != null) {
            this.easyEntityQueryable.where(this.subqueryContext.getWhereExpression());
        }
        if (this.subqueryContext.getOrderByExpression() != null) {
            this.easyEntityQueryable.orderBy(this.subqueryContext.getOrderByExpression());
        }
        if (this.subqueryContext.hasElements()) {
            this.easyEntityQueryable.limit(this.subqueryContext.getFromIndex(), this.subqueryContext.getToIndex() - this.subqueryContext.getFromIndex() + 1);
        }
    }

    private boolean isDistinct() {
        return this.subqueryContext.isDistinct();
    }

    @Override
    public void any() {
        queryableAcceptExpression();
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(this.easyEntityQueryable.limit(1))));
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        where(whereExpression).none();
    }

    @Override
    public void none() {
        queryableAcceptExpression();
        getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.none(this.easyEntityQueryable.limit(1))));
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        queryableAcceptExpression();
        Query<?> anyQuery = this.easyEntityQueryable.limit(1).select("1");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.exists(anyQuery), Boolean.class);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        queryableAcceptExpression();
        Query<?> anyQuery = this.easyEntityQueryable.limit(1).select("1");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.not(f.exists(anyQuery)), Boolean.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> longQuery = this.easyEntityQueryable.selectCount(columnSelector, isDistinct());
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        queryableAcceptExpression();
        Query<?> longQuery = this.easyEntityQueryable.selectCount();
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
        queryableAcceptExpression();
        Query<TMember> sumQuery = staticSum(this.easyEntityQueryable, columnSelector, isDistinct(), null);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), sumQuery.queryClass());
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> sumQuery = staticSum(this.easyEntityQueryable, columnSelector, isDistinct(), BigDecimal.class);
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<BigDecimal> avgQuery = staticAvg(this.easyEntityQueryable, columnSelector, isDistinct());
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(avgQuery).format(0)), BigDecimal.class);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> maxQuery = staticMinOrMax(this.easyEntityQueryable, columnSelector, true);
        return minOrMax(maxQuery, this.getEntitySQLContext());
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> minQuery = staticMinOrMax(this.easyEntityQueryable, columnSelector, false);
        return minOrMax(minQuery, this.getEntitySQLContext());
    }

    @Override
    public ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        queryableAcceptExpression();
        Query<String> joiningQuery = staticJoining(this.easyEntityQueryable, columnSelector, delimiter, isDistinct());
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.anySQLFunction("{0}", x -> x.subQuery(joiningQuery)), String.class);
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this, this.easyEntityQueryable.getClientQueryable(), runtimeContext, flatAdapterExpression));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> configureToSubQuery(SQLExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression) {
        this.subqueryContext.appendConfigureExpression(configureExpression);
        return this;
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
