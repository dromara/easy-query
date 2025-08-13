package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyManyJoinFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.StringTypeExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;

import java.math.BigDecimal;

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
    public SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression) {
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
    public SQLQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression) {
        rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        return this;
    }

    @Override
    public void any(SQLActionExpression1<T1Proxy> whereExpression) {
        this.rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        this.rewritePredicateToSelectProvider.anyValue().eq(true);

    }

    @Override
    public void any() {
        this.rewritePredicateToSelectProvider.anyValue().eq(true);
    }

    @Override
    public void none(SQLActionExpression1<T1Proxy> whereExpression) {
        this.rewritePredicateToSelectProvider.getSubQueryContext().appendWhereExpression(whereExpression);
        this.rewritePredicateToSelectProvider.noneValue().eq(true);
    }

    @Override
    public void none() {
        this.rewritePredicateToSelectProvider.noneValue().eq(true);
    }

    @Override
    public BooleanTypeExpression<Boolean> anyValue() {
        return this.rewritePredicateToSelectProvider.anyValue();
    }

    @Override
    public BooleanTypeExpression<Boolean> noneValue() {
        return this.rewritePredicateToSelectProvider.noneValue();
    }

    private DefaultSQLGroupQueryable<T1Proxy> getDefaultSQLGroupQueryable(){
        DefaultSQLGroupQueryable<T1Proxy> t1ProxyDefaultSQLGroupQueryable = new DefaultSQLGroupQueryable<>(rewritePredicateToSelectProvider.getPropertyProxy(), rewritePredicateToSelectProvider.getPropertyProxy().getEntitySQLContext(), rewritePredicateToSelectProvider.getSubQueryContext().getWhereExpression());
        GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext = t1ProxyDefaultSQLGroupQueryable.getGroupJoinPredicateSegmentContext();
        rewritePredicateToSelectProvider.getManyGroupJoinEntityTableExpressionBuilder().addGroupJoinPredicateSegmentContext(groupJoinPredicateSegmentContext);
        return t1ProxyDefaultSQLGroupQueryable;
    }

    @Override
    public <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        NumberTypeExpression<Long> count = getDefaultSQLGroupQueryable().distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).count(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(count, "count");
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias));
            } else {
                return f.nullOrDefault(c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias).format(0));
            }
        }, Long.class);
    }


    @Override
    public NumberTypeExpression<Long> count() {
        NumberTypeExpression<Long> count = getDefaultSQLGroupQueryable().distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).count();
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(count, "count");
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias));
            } else {
                return f.nullOrDefault(c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias).format(0));
            }
        }, Long.class);
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
        NumberTypeExpression<TMember> sum = getDefaultSQLGroupQueryable().distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).sum(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(sum, "sum");
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias));
            } else {
                return f.nullOrDefault(c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias).format(0));
            }
        }, BigDecimal.class);

    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        return sum(columnSelector).asAnyType(BigDecimal.class);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        NumberTypeExpression<BigDecimal> avg = getDefaultSQLGroupQueryable().distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).avg(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(avg, "avg");
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias));
            } else {
                return f.nullOrDefault(c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias).format(0));
            }
        }, BigDecimal.class);

    }

    @Override
    public <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        AnyTypeExpression<TMember> max = getDefaultSQLGroupQueryable().max(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(max, "max");
        return new AnyTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias)), max.getPropertyType());
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        AnyTypeExpression<TMember> min = getDefaultSQLGroupQueryable().min(columnSelector);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(min, "min");
        return new AnyTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias)), min.getPropertyType());
    }

    @Override
    public StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        StringTypeExpression<String> joining = getDefaultSQLGroupQueryable().distinct(rewritePredicateToSelectProvider.getSubQueryContext().isDistinct()).joining(columnSelector, delimiter);
        String alias = rewritePredicateToSelectProvider.getOrAppendGroupProjects(joining, "joining");
        return new StringTypeExpressionImpl<>(this.getEntitySQLContext(), rewritePredicateToSelectProvider.getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(rewritePredicateToSelectProvider.getManyGroupJoinTable(),alias)), String.class);
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
        T1Proxy propertyProxy = rewritePredicateToSelectProvider.getPropertyProxy();
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = this.rewritePredicateToSelectProvider.getManyGroupJoinEntityTableExpressionBuilder().getEntityQueryExpressionBuilder();
//        EntityTableExpressionBuilder tableExpressionBuilder = entityQueryExpressionBuilder.getTable(0);
//        return propertyProxy.create(rewritePredicateToSelectProvider.getManyGroupJoinTable(),this.rewritePredicateToSelectProvider.getEntitySQLContext());
//        return ;
        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
//        Map<RelationTableKey, ManyConfiguration> manyConfigurations = entityQueryExpressionBuilder.getManyConfigurations();
//        EntityExpressionBuilder entityExpressionBuilder = this.getEntitySQLContext().getEntityExpressionBuilder();
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
        ProxyManyJoinFlatElementEntitySQLContext proxyManyJoinFlatElementEntitySQLContext = new ProxyManyJoinFlatElementEntitySQLContext(rewritePredicateToSelectProvider, entityQueryExpressionBuilder, this.getEntitySQLContext().getContextHolder(), runtimeContext, flatAdapterExpression);
        T1Proxy tPropertyProxy = propertyProxy.create(propertyProxy.getTable(), proxyManyJoinFlatElementEntitySQLContext);
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }

    @Override
    public String getValue() {
        return getSubQueryContext().getProperty();
    }
}
