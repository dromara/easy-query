package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
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
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractClientQueryable3<T1, T2, T3> extends AbstractClientQueryable<T1> implements ClientQueryable3<T1, T2, T3> {

    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;

    public AbstractClientQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable4(t1Class, t2Class, t3Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> leftJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable<T4> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable4(t1Class, t2Class, t3Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable4(t1Class, t2Class, t3Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> rightJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable<T4> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable4(t1Class, t2Class, t3Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable4(t1Class, t2Class, t3Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> innerJoin(ClientQueryable<T4> joinQueryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable<T4> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable4(t1Class, t2Class, t3Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public ClientQueryable3<T1, T2, T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> where(boolean condition, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> whereExpression) {
        if (condition) {
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate();
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate();
            WherePredicate<T3> sqlWherePredicate3 = getSQLExpressionProvider3().getWherePredicate();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2, sqlWherePredicate3);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression3<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>> selectExpression) {

        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2, sqlColumnAsSelector3);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLObjectApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    private <TMember> List<TMember> selectAggregateList(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, ColumnFunction columnFunction, Class<TMember> resultClass) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T3> sqlColumnResultSelector3 = getSQLExpressionProvider3().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2, sqlColumnResultSelector3);
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
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def) {

        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def) {

        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = selectAggregateList(columnSelectorExpression, avgFunction, resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }


    @Override
    public ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<GroupBySelector<T1>, GroupBySelector<T2>, GroupBySelector<T3>> selectExpression) {
        if (condition) {
            GroupBySelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            GroupBySelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            GroupBySelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3);
        }
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(predicateExpression);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> having(boolean condition, SQLExpression3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            WhereAggregatePredicate<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3);
        }
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>> selectExpression) {
        if (condition) {
            ColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            ColumnSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<ColumnSelector<T1>, ColumnSelector<T2>, ColumnSelector<T3>> selectExpression) {
        if (condition) {
            ColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            ColumnSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asAlias(String alias) {
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
}
