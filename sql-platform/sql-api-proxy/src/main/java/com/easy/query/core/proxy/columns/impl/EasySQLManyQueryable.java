package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
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
import com.easy.query.core.proxy.extension.functions.executor.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.StringTypeExpressionImpl;
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
    private final SubQueryContext<T1Proxy, T1> subQueryContext;
    private EntityQueryable<T1Proxy, T1> easyEntityQueryable;

    public EasySQLManyQueryable(SubQueryContext<T1Proxy, T1> subQueryContext, EntityQueryable<T1Proxy, T1> easyEntityQueryable) {
        this.subQueryContext = subQueryContext;
        this.easyEntityQueryable = easyEntityQueryable;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return subQueryContext.getEntitySQLContext();
    }

    @Override
    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return subQueryContext;
    }

    @Override
    public TableAvailable getOriginalTable() {
        return subQueryContext.getLeftTable();
    }

//    @Override
//    public T1Proxy element(int index) {
//        //添加当前表的partition row_number=0 join当前表作为子表生成relationTableKey
//        EasyRelationalUtil.getManyJoinRelationTable()
//        return null;
//    }

    @Override
    public String getNavValue() {
        return subQueryContext.getFullName();
    }

    @Override
    public String getValue() {
        return subQueryContext.getProperty();
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
    public SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression) {
        this.getSubQueryContext().appendOrderByExpression(orderExpression);
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression) {
        this.getSubQueryContext().appendWhereExpression(whereExpression);
        return this;
    }

    @Override
    public void any(SQLActionExpression1<T1Proxy> whereExpression) {
        where(whereExpression).any();
    }

    private void queryableAcceptExpression() {
        if (this.subQueryContext.hasElements()) {
            if (this.subQueryContext.getWhereExpression() != null || this.subQueryContext.getOrderByExpression() != null) {
                this.easyEntityQueryable = this.easyEntityQueryable.select(s -> s);
            }
        }
        if (this.subQueryContext.getConfigureExpression() != null) {
            this.subQueryContext.getConfigureExpression().apply(this.easyEntityQueryable);
        }
        if (this.subQueryContext.getWhereExpression() != null) {
            this.easyEntityQueryable.where(this.subQueryContext.getWhereExpression());
        }
        if (this.subQueryContext.getOrderByExpression() != null) {
            this.easyEntityQueryable.orderBy(this.subQueryContext.getOrderByExpression());
        }
//        if (this.subqueryContext.hasElements()) {
//            this.easyEntityQueryable.limit(this.subqueryContext.getOffset(), this.subqueryContext.getLimit());
//        }
    }

    private boolean isDistinct() {
        return this.subQueryContext.isDistinct();
    }

    @Override
    public void any() {
        queryableAcceptExpression();
        getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(this.easyEntityQueryable.limit(1))));
    }

    @Override
    public void none(SQLActionExpression1<T1Proxy> whereExpression) {
        where(whereExpression).none();
    }

    @Override
    public void none() {
        queryableAcceptExpression();
        getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.none(this.easyEntityQueryable.limit(1))));
    }

    @Override
    public BooleanTypeExpression<Boolean> anyValue() {
        queryableAcceptExpression();
        Query<?> anyQuery = this.easyEntityQueryable.limit(1).select("1");
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.exists(anyQuery), Boolean.class);
    }

    @Override
    public BooleanTypeExpression<Boolean> noneValue() {
        queryableAcceptExpression();
        Query<?> anyQuery = this.easyEntityQueryable.limit(1).select("1");
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.not(f.exists(anyQuery)), Boolean.class);
    }

    @Override
    public <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> longQuery = this.easyEntityQueryable.selectCount(columnSelector, isDistinct());
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }

    @Override
    public NumberTypeExpression<Long> count() {
        queryableAcceptExpression();
        Query<?> longQuery = this.easyEntityQueryable.selectCount();
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.subQueryValue(longQuery), Long.class);
    }

    @Override
    public <TMember> NumberTypeExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return count(columnSelector).asAnyType(Integer.class);
    }

    @Override
    public NumberTypeExpression<Integer> intCount() {
        return count().asAnyType(Integer.class);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> sumQuery = staticSum(this.easyEntityQueryable, columnSelector, isDistinct(), null);
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), sumQuery.queryClass());
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> sumQuery = staticSum(this.easyEntityQueryable, columnSelector, isDistinct(), BigDecimal.class);
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(sumQuery).format(0)), BigDecimal.class);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<BigDecimal> avgQuery = staticAvg(this.easyEntityQueryable, columnSelector, isDistinct());
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.nullOrDefault(x -> x.subQuery(avgQuery).format(0)), BigDecimal.class);
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> maxQuery = staticMinOrMax(this.easyEntityQueryable, columnSelector, true);
        return minOrMax(maxQuery, this.getEntitySQLContext());
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        queryableAcceptExpression();
        Query<TMember> minQuery = staticMinOrMax(this.easyEntityQueryable, columnSelector, false);
        return minOrMax(minQuery, this.getEntitySQLContext());
    }

    @Override
    public StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        queryableAcceptExpression();
        Query<String> joiningQuery = staticJoining(this.easyEntityQueryable, columnSelector, delimiter, isDistinct());
        return new StringTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.anySQLFunction("{0}", x -> x.subQuery(joiningQuery)), String.class);
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this, this.easyEntityQueryable.getClientQueryable(), runtimeContext, flatAdapterExpression));
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
            return new NumberTypeExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, apply);
                }).distinct(distinct);
            }, EasyObjectUtil.nullToDefault(propertyType, apply.getPropertyType()));
        });
    }

    static <TProxy extends ProxyEntity<TProxy, T>, T, TMember extends Number> Query<String> staticJoining(EntityQueryable<TProxy, T> entityQueryable, SQLFuncExpression1<TProxy, PropTypeColumn<String>> columnSelector, String delimiter, boolean distinct) {
        return entityQueryable.selectColumn(s -> {
            PropTypeColumn<String> column = columnSelector.apply(s);
            return new NumberTypeExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
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
            return new NumberTypeExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, apply);
                }).distinct(distinct);
            }, BigDecimal.class);
        });
    }

    static <TProxy extends ProxyEntity<TProxy, T>, T, TMember> Query<TMember> staticMinOrMax(EntityQueryable<TProxy, T> entityQueryable, SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector, boolean max) {
        return entityQueryable.selectColumn(s -> {
            PropTypeColumn<TMember> apply = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<>(s.getEntitySQLContext(), s.getTable(), s.getValue(), fx -> {
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

    static <TMember> AnyTypeExpression<TMember> minOrMax(Query<TMember> subQuery, EntitySQLContext entitySQLContext) {

        boolean numberType = EasyClassUtil.isNumberType(subQuery.getClass());
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> {
            if (numberType) {
                return f.nullOrDefault(x -> x.subQuery(subQuery).format(0));
            } else {
                return f.subQueryValue(subQuery);
            }
        }, subQuery.queryClass());
    }

}
