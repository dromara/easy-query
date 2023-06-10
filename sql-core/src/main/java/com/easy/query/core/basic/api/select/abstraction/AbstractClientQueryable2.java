package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractClientQueryable2<T1, T2> extends AbstractClientQueryable<T1> implements ClientQueryable2<T1, T2> {

    protected final Class<T2> t2Class;
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;

    public AbstractClientQueryable2(Class<T1> t1Class, Class<T2> t2Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> leftJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable3(t1Class, t2Class, selectAllT2Queryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> rightJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable3(t1Class, t2Class, selectAllT2Queryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> innerJoin(ClientQueryable<T3> joinQueryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable3(t1Class, t2Class, selectAllT2Queryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }


    @Override
    protected Class<?> matchQueryEntityClass(Class<?> propertyQueryEntityClass) {
        Class<?> queryEntityClass = super.matchQueryEntityClass(propertyQueryEntityClass);
        if(queryEntityClass!=null){
            return queryEntityClass;
        }
        if(Objects.equals(t2Class,propertyQueryEntityClass)){
            return t2Class;
        }
        return null;
    }

    @Override
    protected WherePredicate<?> matchWhereObjectSQLPredicate(Class<?> entityClass) {
        if (entityClass == t1Class) {
            return getSQLExpressionProvider1().getWherePredicate();
        }
        if (entityClass == t2Class) {
            return getSQLExpressionProvider2().getWherePredicate();
        }
        return null;
    }

    @Override
    protected ColumnSelector<?> matchOrderBySQLColumnSelector(Class<?> entityClass, boolean asc) {
        ColumnSelector<?> sqlColumnSelector = super.matchOrderBySQLColumnSelector(entityClass, asc);
        if (sqlColumnSelector != null) {
            return sqlColumnSelector;
        }
        if (entityClass == t2Class) {
            return getSQLExpressionProvider2().getOrderColumnSelector(asc);
        }
        return null;
    }

    @Override
    public ClientQueryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> where(boolean condition, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression) {
        if (condition) {
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate();
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2);
        }
        return this;
    }


    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression) {
        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    private <TMember> List<TMember> selectAggregateList(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, ColumnFunction columnFunction, Class<TMember> resultClass) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment) projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        Class<TMember> tMemberClass = resultClass == null ? (Class<TMember>) table.getEntityMetadata().getColumnNotNull(propertyName).getPropertyType() : resultClass;
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, null);
        return cloneQueryable().select(funcColumnSegment, true).toList(tMemberClass);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def) {
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def) {
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def) {
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = selectAggregateList(columnSelectorExpression, avgFunction, resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<ColumnSelector<T1>, ColumnSelector<T2>> selectExpression) {
        if (condition) {
            ColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<ColumnSelector<T1>, ColumnSelector<T2>> selectExpression) {
        if (condition) {
            ColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> groupBy(boolean condition, SQLExpression2<GroupBySelector<T1>, GroupBySelector<T2>> selectExpression) {
        if (condition) {
            GroupBySelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            GroupBySelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2);
        }
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> having(boolean condition, SQLExpression2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2);
        }
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }


    @Override
    public ClientQueryable2<T1, T2> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }

    @Override
    public SQLExpressionProvider<T2> getSQLExpressionProvider2() {
        if(sqlExpressionProvider2==null){
            sqlExpressionProvider2=runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(1,this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider2;
    }

}
