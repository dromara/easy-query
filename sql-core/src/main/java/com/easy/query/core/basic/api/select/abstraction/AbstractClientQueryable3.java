package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.extension.queryable3.override.AbstractOverrideClientQueryable3;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.lambda.SQLActionExpression3;
import com.easy.query.core.expression.lambda.SQLActionExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.many.ManyColumn;
import com.easy.query.core.expression.parser.core.base.many.ManyJoinSelector;
import com.easy.query.core.expression.parser.core.base.many.ManyJoinSelectorImpl;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable3.java
 * @Description: 文件说明
 * create time 2023/2/6 23:43
 */
public abstract class AbstractClientQueryable3<T1, T2, T3> extends AbstractOverrideClientQueryable3<T1,T2,T3> implements ClientQueryable3<T1, T2, T3> {

    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;

    public AbstractClientQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class,t2Class,t3Class, sqlEntityExpression);
    }

    @Override
    protected ClientQueryable3<T1, T2, T3> getClientQueryable3() {
        return this;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return this;
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, t2Class, t3Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> leftJoin(ClientQueryable<T4> joinQueryable, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable<T4> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, t2Class, t3Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, t2Class, t3Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> rightJoin(ClientQueryable<T4> joinQueryable, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable<T4> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, t2Class, t3Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, t2Class, t3Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T4> ClientQueryable4<T1, T2, T3, T4> innerJoin(ClientQueryable<T4> joinQueryable, SQLActionExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        ClientQueryable<T4> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, t2Class, t3Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public ClientQueryable3<T1, T2, T3> subQueryToGroupJoin(boolean condition, SQLFuncExpression3<ManyJoinSelector<T1>, ManyJoinSelector<T2>, ManyJoinSelector<T3>, ManyColumn> manyPropColumnExpression) {
        if(condition){
            EntityTableExpressionBuilder table1 = entityQueryExpressionBuilder.getTable(0);
            EntityTableExpressionBuilder table2 = entityQueryExpressionBuilder.getTable(1);
            EntityTableExpressionBuilder table3 = entityQueryExpressionBuilder.getTable(2);
            ManyColumn manyColumn = manyPropColumnExpression.apply(new ManyJoinSelectorImpl<>(table1.getEntityTable()),new ManyJoinSelectorImpl<>(table2.getEntityTable()),new ManyJoinSelectorImpl<>(table3.getEntityTable()));
            EasyRelationalUtil.TableOrRelationTable tableOrRelationalTable = EasyRelationalUtil.getTableOrRelationalTable(entityQueryExpressionBuilder, manyColumn.getTable(), manyColumn.getNavValue());
            TableAvailable leftTable = tableOrRelationalTable.table;
            String property = tableOrRelationalTable.property;
            entityQueryExpressionBuilder.addSubQueryToGroupJoinJoin(new DefaultRelationTableKey(leftTable, property));
        }
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> where(boolean condition, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> whereExpression) {
        if (condition) {
            FilterContext whereFilterContext = getSQLExpressionProvider1().getWhereFilterContext();
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate(whereFilterContext);
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate(whereFilterContext);
            WherePredicate<T3> sqlWherePredicate3 = getSQLExpressionProvider3().getWherePredicate(whereFilterContext);
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2, sqlWherePredicate3);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression3<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>> selectExpression) {

        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2, sqlColumnAsSelector3);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression3<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>> selectExpression, boolean replace) {
        selectAutoInclude0(resultClass,replace);
        if(selectExpression!=null){
            ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3);
        }
        return select(resultClass);
    }

    private <TMember> List<TMember> selectAggregateList(SQLActionExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression,
                                                        SQLFuncExpression2<TableAvailable,String, SQLFunction> sqlFunctionCreator,
                                                        Class<TMember> resultClass) {

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
        SQLFunction sqlFunction = sqlFunctionCreator.apply(table, propertyName);
        return selectAggregateList(table,sqlFunction,propertyName, resultClass);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLActionExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().sum(s -> s.column(table, prop)), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLActionExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().sum(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLActionExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().max(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLActionExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().min(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLActionExpression3<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        List<TResult> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().avg(s -> s.column(table, prop)), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }
    @Override
    public ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLActionExpression3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>> selectExpression) {
        if (condition) {
            ColumnGroupSelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            ColumnGroupSelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            ColumnGroupSelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3);
        }
        return this;
    }
    @Override
    public ClientQueryable3<T1, T2, T3> having(boolean condition, SQLActionExpression3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            WhereAggregatePredicate<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3);
        }
        return this;
    }
    @Override
    public ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLActionExpression3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLActionExpression3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3);
        }
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
