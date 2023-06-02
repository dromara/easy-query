package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 */
public abstract class AbstractClientQueryable4<T1, T2, T3, T4> extends AbstractObjectQueryable<T1> implements ClientQueryable4<T1, T2, T3, T4> {
    private final Class<T2> t2Class;
    private final Class<T3> t3Class;
    private final Class<T4> t4Class;
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;
    protected SQLExpressionProvider<T4> sqlExpressionProvider4;


    public AbstractClientQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
    }

    @Override
    protected Class<?> matchQueryEntityClass(Class<?> propertyQueryEntityClass) {

        Class<?> queryEntityClass = super.matchQueryEntityClass(propertyQueryEntityClass);
        if (queryEntityClass != null) {
            return queryEntityClass;
        }
        if (Objects.equals(t2Class, propertyQueryEntityClass)) {
            return t2Class;
        }
        if (Objects.equals(t3Class, propertyQueryEntityClass)) {
            return t3Class;
        }
        if (Objects.equals(t4Class, propertyQueryEntityClass)) {
            return t4Class;
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
        if (entityClass == t3Class) {
            return getSQLExpressionProvider3().getWherePredicate();
        }
        if (entityClass == t4Class) {
            return getSQLExpressionProvider4().getWherePredicate();
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
        if (entityClass == t3Class) {
            return getSQLExpressionProvider3().getOrderColumnSelector(asc);
        }
        if (entityClass == t4Class) {
            return getSQLExpressionProvider4().getOrderColumnSelector(asc);
        }
        return null;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> whereExpression) {
        if (condition) {
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate();
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate();
            WherePredicate<T3> sqlWherePredicate3 = getSQLExpressionProvider3().getWherePredicate();
            WherePredicate<T4> sqlWherePredicate4 = getSQLExpressionProvider4().getWherePredicate();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2, sqlWherePredicate3, sqlWherePredicate4);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression4<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>> selectExpression) {

        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSQLExpressionProvider4().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2, sqlColumnAsSelector3, sqlColumnAsSelector4);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    private <TMember> List<TMember> selectAggregateList(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, ColumnFunction easyFunc) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T3> sqlColumnResultSelector3 = getSQLExpressionProvider3().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T4> sqlColumnResultSelector4 = getSQLExpressionProvider4().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2, sqlColumnResultSelector3, sqlColumnResultSelector4);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment) projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList((Class<TMember>) columnMetadata.getPropertyType());
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def) {
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def) {
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def) {
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, TMember def) {
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, avgFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public Integer lenOrDefault(SQLExpression4<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>> columnSelectorExpression, Integer def) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T3> sqlColumnResultSelector3 = getSQLExpressionProvider3().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T4> sqlColumnResultSelector4 = getSQLExpressionProvider4().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2, sqlColumnResultSelector3, sqlColumnResultSelector4);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggreagate query not found column");
        }
        ColumnFunction lenFunction = runtimeContext.getColumnFunctionFactory().createLenFunction();
        List<Integer> result = cloneQueryable().select(lenFunction.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<GroupBySelector<T1>, GroupBySelector<T2>, GroupBySelector<T3>, GroupBySelector<T4>> selectExpression) {
        if (condition) {
            GroupBySelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            GroupBySelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            GroupBySelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getGroupColumnSelector();
            GroupBySelector<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4);
        }
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>, ColumnSelector<T4>> selectExpression) {
        if (condition) {
            ColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            ColumnSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(true);
            ColumnSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4);
        }
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>, ColumnSelector<T4>> selectExpression) {
        if (condition) {
            ColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            ColumnSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(false);
            ColumnSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4);
        }
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }


    @Override
    public SQLExpressionProvider<T2> getSQLExpressionProvider2() {
        if (sqlExpressionProvider2 == null) {
            sqlExpressionProvider2 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(1, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider2;
    }

    @Override
    public SQLExpressionProvider<T3> getSQLExpressionProvider3() {
        if (sqlExpressionProvider3 == null) {
            sqlExpressionProvider3 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(2, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider3;
    }

    @Override
    public SQLExpressionProvider<T4> getSQLExpressionProvider4() {
        if (sqlExpressionProvider4 == null) {
            sqlExpressionProvider4 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(3, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider4;
    }
}
