package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyFlatElementEntitySQLContext;
import com.easy.query.core.proxy.core.ProxyManyJoinFlatElementEntitySQLContext;
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
import java.util.Map;
import java.util.Set;

/**
 * create time 2024/6/5 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyManyJoinSQLManyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {

    private final RewritePredicateToSelectProvider<T1Proxy, T1> rewritePredicateToSelectProvider;
    private final boolean required;

    public EasyManyJoinSQLManyQueryable(SubQueryContext<T1Proxy, T1> subQueryContext, AnonymousManyJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder, T1Proxy propertyProxy) {
        String property = subQueryContext.getProperty();
        this.required = subQueryContext.getLeftTable().getEntityMetadata().getNavigateNotNull(property).isRequired();
        this.rewritePredicateToSelectProvider = new RewritePredicateToSelectProvider<>(subQueryContext, manyGroupJoinEntityTableExpressionBuilder, propertyProxy,required);
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        rewritePredicateToSelectProvider.getSubQueryContext().distinct(useDistinct);
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> orderExpression) {
        rewritePredicateToSelectProvider.getSubQueryContext().appendOrderByExpression(orderExpression);
        return this;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return rewritePredicateToSelectProvider.getEntitySQLContext();
    }

    @Override
    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return rewritePredicateToSelectProvider.getSubQueryContext();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return rewritePredicateToSelectProvider.getOriginalTable();
    }

//    @Override
//    public T1Proxy element(int index) {
//        return null;
//    }

    @Override
    public String getNavValue() {
        return rewritePredicateToSelectProvider.getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return rewritePredicateToSelectProvider.getPropertyProxy();
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        return this;
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        this.rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        this.rewritePredicateToSelectProvider.anyValue().eq(true);

    }

    @Override
    public void any() {
        this.rewritePredicateToSelectProvider.anyValue().eq(true);
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        this.rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        this.rewritePredicateToSelectProvider.noneValue().eq(true);
    }

    @Override
    public void none() {
        this.rewritePredicateToSelectProvider.noneValue().eq(true);
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return this.rewritePredicateToSelectProvider.anyValue();
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return this.rewritePredicateToSelectProvider.noneValue();
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).count(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).format(0));
            }
        }, Long.class);
    }


    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).count();
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(count, "count");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).format(0));
            }
        }, Long.class);
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
        ColumnFunctionCompareComparableNumberChainExpression<TMember> sum = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).sum(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(sum, "sum");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).format(0));
            }
        }, BigDecimal.class);

    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).avg(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(avg, "avg");
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).format(0));
            }
        }, BigDecimal.class);

    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> max = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).max(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(max, "max");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), max.getPropertyType());
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        ColumnFunctionCompareComparableAnyChainExpression<TMember> min = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).min(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(min, "min");
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), min.getPropertyType());
    }

    @Override
    public ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        ColumnFunctionCompareComparableStringChainExpression<String> joining = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression()).distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).joining(columnSelector, delimiter);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(joining, "joining");
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), String.class);
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
        T1Proxy propertyProxy = rewritePredicateToSelectProvider.getPropertyProxy();
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = this.rewritePredicateToSelectProvider.getManyGroupJoinEntityTableExpressionBuilder().getEntityQueryExpressionBuilder();
//        EntityTableExpressionBuilder tableExpressionBuilder = entityQueryExpressionBuilder.getTable(0);
//        return propertyProxy.create(rewritePredicateToSelectProvider.getManyGroupJoinTable(),this.rewritePredicateToSelectProvider.getEntitySQLContext());
//        return ;
        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = this.rewritePredicateToSelectProvider.getManyGroupJoinEntityTableExpressionBuilder().getEntityQueryExpressionBuilder();
//        Map<RelationTableKey, ManyConfiguration> manyConfigurations = entityQueryExpressionBuilder.getManyConfigurations();
        EntityExpressionBuilder entityExpressionBuilder = this.getEntitySQLContext().getEntityExpressionBuilder();
//        if(manyConfigurations!=null){
//            for (Map.Entry<RelationTableKey, ManyConfiguration> kv : manyConfigurations.entrySet()) {
//                entityExpressionBuilder.putManyConfiguration(kv.getKey(), kv.getValue());
//            }
//        }
//        Set<RelationTableKey> manyJoinConfigurationSets = entityQueryExpressionBuilder.getManyJoinConfigurationSets();
//        if(manyJoinConfigurationSets!=null){
//            for (RelationTableKey manyJoinConfigurationSet : manyJoinConfigurationSets) {
//                entityExpressionBuilder.addSubQueryToGroupJoinJoin(manyJoinConfigurationSet);
//            }
//        }
//
////        T1Proxy tPropertyProxy = propertyProxy.create(propertyProxy.getTable(), new ProxyFlatElementEntitySQLContext(this, this.easyEntityQueryable.getClientQueryable(), runtimeContext, flatAdapterExpression));
        ProxyManyJoinFlatElementEntitySQLContext proxyManyJoinFlatElementEntitySQLContext = new ProxyManyJoinFlatElementEntitySQLContext(rewritePredicateToSelectProvider, entityExpressionBuilder, this.getEntitySQLContext().getContextHolder(), runtimeContext, flatAdapterExpression,sqlPredicateExpression->{

            this.rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(s->{
                sqlPredicateExpression.accept(this.rewritePredicateToSelectProvider.getSubQueryContext().getEntitySQLContext().getFilter());
            });
            this.rewritePredicateToSelectProvider.anyValue().eq(true);
        });
        T1Proxy tPropertyProxy = propertyProxy.create(propertyProxy.getTable(), proxyManyJoinFlatElementEntitySQLContext);
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }

    @Override
    public String getValue() {
        return getSubQueryContext().getProperty();
    }
}
