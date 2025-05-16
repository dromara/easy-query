package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.extension.queryable10.override.AbstractOverrideClientQueryable10;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLActionExpression10;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * create time 2023/3/9 12:38
 */
public abstract class AbstractClientQueryable10<T1, T2, T3, T4,T5,T6,T7,T8,T9,T10> extends AbstractOverrideClientQueryable10<T1, T2, T3, T4,T5,T6,T7,T8,T9,T10> implements ClientQueryable10<T1, T2, T3, T4,T5,T6,T7,T8,T9,T10> {
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;
    protected SQLExpressionProvider<T4> sqlExpressionProvider4;
    protected SQLExpressionProvider<T5> sqlExpressionProvider5;
    protected SQLExpressionProvider<T6> sqlExpressionProvider6;
    protected SQLExpressionProvider<T7> sqlExpressionProvider7;
    protected SQLExpressionProvider<T8> sqlExpressionProvider8;
    protected SQLExpressionProvider<T9> sqlExpressionProvider9;
    protected SQLExpressionProvider<T10> sqlExpressionProvider10;


    public AbstractClientQueryable10(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, Class<T10> t10Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, t4Class,t5Class,t6Class,t7Class,t8Class,t9Class,t10Class, sqlEntityExpression);
    }

    @Override
    public ClientQueryable10<T1, T2, T3, T4,T5, T6,T7,T8,T9,T10> getClientQueryable10() {
        return this;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return this;
    }

    @Override
    public ClientQueryable10<T1, T2, T3, T4,T5, T6,T7,T8,T9,T10> where(boolean condition, SQLActionExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> whereExpression) {
        if (condition) {
            FilterContext whereFilterContext = getSQLExpressionProvider1().getWhereFilterContext();
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate(whereFilterContext);
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate(whereFilterContext);
            WherePredicate<T3> sqlWherePredicate3 = getSQLExpressionProvider3().getWherePredicate(whereFilterContext);
            WherePredicate<T4> sqlWherePredicate4 = getSQLExpressionProvider4().getWherePredicate(whereFilterContext);
            WherePredicate<T5> sqlWherePredicate5 = getSQLExpressionProvider5().getWherePredicate(whereFilterContext);
            WherePredicate<T6> sqlWherePredicate6 = getSQLExpressionProvider6().getWherePredicate(whereFilterContext);
            WherePredicate<T7> sqlWherePredicate7 = getSQLExpressionProvider7().getWherePredicate(whereFilterContext);
            WherePredicate<T8> sqlWherePredicate8 = getSQLExpressionProvider8().getWherePredicate(whereFilterContext);
            WherePredicate<T9> sqlWherePredicate9 = getSQLExpressionProvider9().getWherePredicate(whereFilterContext);
            WherePredicate<T10> sqlWherePredicate10 = getSQLExpressionProvider10().getWherePredicate(whereFilterContext);
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2, sqlWherePredicate3, sqlWherePredicate4,sqlWherePredicate5,sqlWherePredicate6,sqlWherePredicate7,sqlWherePredicate8,sqlWherePredicate9,sqlWherePredicate10);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression10<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>, ColumnAsSelector<T8, TR>, ColumnAsSelector<T9, TR>, ColumnAsSelector<T10, TR>> selectExpression) {

        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSQLExpressionProvider4().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T5, TR> sqlColumnAsSelector5 = getSQLExpressionProvider5().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T6, TR> sqlColumnAsSelector6 = getSQLExpressionProvider6().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T7, TR> sqlColumnAsSelector7 = getSQLExpressionProvider7().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T8, TR> sqlColumnAsSelector8 = getSQLExpressionProvider8().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T9, TR> sqlColumnAsSelector9 = getSQLExpressionProvider9().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T10, TR> sqlColumnAsSelector10 = getSQLExpressionProvider10().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2, sqlColumnAsSelector3, sqlColumnAsSelector4,sqlColumnAsSelector5,sqlColumnAsSelector6,sqlColumnAsSelector7,sqlColumnAsSelector8,sqlColumnAsSelector9,sqlColumnAsSelector10);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression10<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>, ColumnAsSelector<T8, TR>, ColumnAsSelector<T9, TR>, ColumnAsSelector<T10, TR>> selectExpression, boolean replace) {
        selectAutoInclude0(resultClass,replace);
        if(selectExpression!=null){
            ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSQLExpressionProvider4().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T5, TR> sqlColumnAsSelector5 = getSQLExpressionProvider5().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T6, TR> sqlColumnAsSelector6 = getSQLExpressionProvider6().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T7, TR> sqlColumnAsSelector7 = getSQLExpressionProvider7().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T8, TR> sqlColumnAsSelector8 = getSQLExpressionProvider8().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T9, TR> sqlColumnAsSelector9 = getSQLExpressionProvider9().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T10, TR> sqlColumnAsSelector10 = getSQLExpressionProvider10().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3,sqlColumnAsSelector4,sqlColumnAsSelector5,sqlColumnAsSelector6,sqlColumnAsSelector7,sqlColumnAsSelector8,sqlColumnAsSelector9,sqlColumnAsSelector10);
        }
        return select(resultClass);
    }

    private <TMember> List<TMember> selectAggregateList(SQLActionExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, SQLFuncExpression2<TableAvailable,String, SQLFunction> sqlFunctionCreator, Class<TMember> resultClass) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T3> sqlColumnResultSelector3 = getSQLExpressionProvider3().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T4> sqlColumnResultSelector4 = getSQLExpressionProvider4().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T5> sqlColumnResultSelector5 = getSQLExpressionProvider5().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T6> sqlColumnResultSelector6 = getSQLExpressionProvider6().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T7> sqlColumnResultSelector7 = getSQLExpressionProvider7().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T8> sqlColumnResultSelector8 = getSQLExpressionProvider8().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T9> sqlColumnResultSelector9 = getSQLExpressionProvider9().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T10> sqlColumnResultSelector10 = getSQLExpressionProvider10().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2, sqlColumnResultSelector3, sqlColumnResultSelector4,sqlColumnResultSelector5,sqlColumnResultSelector6,sqlColumnResultSelector7,sqlColumnResultSelector8,sqlColumnResultSelector9,sqlColumnResultSelector10);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment) projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        SQLFunction sqlFunction = sqlFunctionCreator.apply(table, propertyName);

        return selectAggregateList(table,sqlFunction,propertyName,resultClass);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLActionExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().sum(s -> s.column(table, prop)), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLActionExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().sum(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLActionExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().max(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLActionExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().min(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLActionExpression10<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>, ColumnResultSelector<T9>, ColumnResultSelector<T10>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        List<TResult> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().avg(s -> s.column(table, prop)), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }


    @Override
    public ClientQueryable10<T1, T2, T3, T4,T5, T6,T7,T8,T9,T10> groupBy(boolean condition, SQLActionExpression10<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>, ColumnGroupSelector<T10>> selectExpression) {
        if (condition) {
            ColumnGroupSelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            ColumnGroupSelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            ColumnGroupSelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getGroupColumnSelector();
            ColumnGroupSelector<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getGroupColumnSelector();
            ColumnGroupSelector<T5> sqlGroupSelector5 = getSQLExpressionProvider5().getGroupColumnSelector();
            ColumnGroupSelector<T6> sqlGroupSelector6 = getSQLExpressionProvider6().getGroupColumnSelector();
            ColumnGroupSelector<T7> sqlGroupSelector7 = getSQLExpressionProvider7().getGroupColumnSelector();
            ColumnGroupSelector<T8> sqlGroupSelector8 = getSQLExpressionProvider8().getGroupColumnSelector();
            ColumnGroupSelector<T9> sqlGroupSelector9 = getSQLExpressionProvider9().getGroupColumnSelector();
            ColumnGroupSelector<T10> sqlGroupSelector10 = getSQLExpressionProvider10().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4,sqlGroupSelector5,sqlGroupSelector6,sqlGroupSelector7,sqlGroupSelector8,sqlGroupSelector9,sqlGroupSelector10);
        }
        return this;
    }

    @Override
    public ClientQueryable10<T1, T2, T3, T4,T5, T6,T7,T8,T9,T10> having(boolean condition, SQLActionExpression10<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>, WhereAggregatePredicate<T10>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            WhereAggregatePredicate<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getAggregatePredicate();
            WhereAggregatePredicate<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getAggregatePredicate();
            WhereAggregatePredicate<T5> sqlGroupSelector5 = getSQLExpressionProvider5().getAggregatePredicate();
            WhereAggregatePredicate<T6> sqlGroupSelector6 = getSQLExpressionProvider6().getAggregatePredicate();
            WhereAggregatePredicate<T7> sqlGroupSelector7 = getSQLExpressionProvider7().getAggregatePredicate();
            WhereAggregatePredicate<T8> sqlGroupSelector8 = getSQLExpressionProvider8().getAggregatePredicate();
            WhereAggregatePredicate<T9> sqlGroupSelector9 = getSQLExpressionProvider9().getAggregatePredicate();
            WhereAggregatePredicate<T10> sqlGroupSelector10 = getSQLExpressionProvider10().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4,sqlGroupSelector5,sqlGroupSelector6,sqlGroupSelector7,sqlGroupSelector8,sqlGroupSelector9,sqlGroupSelector10);
        }
        return this;
    }


    @Override
    public ClientQueryable10<T1, T2, T3, T4,T5, T6,T7,T8,T9,T10> orderByAsc(boolean condition, SQLActionExpression10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(true);
            ColumnOrderSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(true);
            ColumnOrderSelector<T5> sqlOrderColumnSelector5 = getSQLExpressionProvider5().getOrderColumnSelector(true);
            ColumnOrderSelector<T6> sqlOrderColumnSelector6 = getSQLExpressionProvider6().getOrderColumnSelector(true);
            ColumnOrderSelector<T7> sqlOrderColumnSelector7 = getSQLExpressionProvider7().getOrderColumnSelector(true);
            ColumnOrderSelector<T8> sqlOrderColumnSelector8 = getSQLExpressionProvider8().getOrderColumnSelector(true);
            ColumnOrderSelector<T9> sqlOrderColumnSelector9 = getSQLExpressionProvider9().getOrderColumnSelector(true);
            ColumnOrderSelector<T10> sqlOrderColumnSelector10 = getSQLExpressionProvider10().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4,sqlOrderColumnSelector5,sqlOrderColumnSelector6,sqlOrderColumnSelector7,sqlOrderColumnSelector8,sqlOrderColumnSelector9,sqlOrderColumnSelector10);
        }
        return this;
    }

    @Override
    public ClientQueryable10<T1, T2, T3, T4,T5, T6,T7,T8,T9,T10> orderByDesc(boolean condition, SQLActionExpression10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(false);
            ColumnOrderSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(false);
            ColumnOrderSelector<T5> sqlOrderColumnSelector5 = getSQLExpressionProvider5().getOrderColumnSelector(false);
            ColumnOrderSelector<T6> sqlOrderColumnSelector6 = getSQLExpressionProvider6().getOrderColumnSelector(false);
            ColumnOrderSelector<T7> sqlOrderColumnSelector7 = getSQLExpressionProvider7().getOrderColumnSelector(false);
            ColumnOrderSelector<T8> sqlOrderColumnSelector8 = getSQLExpressionProvider8().getOrderColumnSelector(false);
            ColumnOrderSelector<T9> sqlOrderColumnSelector9 = getSQLExpressionProvider9().getOrderColumnSelector(false);
            ColumnOrderSelector<T10> sqlOrderColumnSelector10 = getSQLExpressionProvider10().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4,sqlOrderColumnSelector5,sqlOrderColumnSelector6,sqlOrderColumnSelector7,sqlOrderColumnSelector8,sqlOrderColumnSelector9,sqlOrderColumnSelector10);
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

    @Override
    public SQLExpressionProvider<T4> getSQLExpressionProvider4() {
        if (sqlExpressionProvider4 == null) {
            sqlExpressionProvider4 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(3, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider4;
    }
    @Override
    public SQLExpressionProvider<T5> getSQLExpressionProvider5() {
        if (sqlExpressionProvider5 == null) {
            sqlExpressionProvider5 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(4, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider5;
    }
    @Override
    public SQLExpressionProvider<T6> getSQLExpressionProvider6() {
        if (sqlExpressionProvider6 == null) {
            sqlExpressionProvider6 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(5, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider6;
    }
    @Override
    public SQLExpressionProvider<T7> getSQLExpressionProvider7() {
        if (sqlExpressionProvider7 == null) {
            sqlExpressionProvider7 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(6, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider7;
    }
    @Override
    public SQLExpressionProvider<T8> getSQLExpressionProvider8() {
        if (sqlExpressionProvider8 == null) {
            sqlExpressionProvider8 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(7, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider8;
    }
    @Override
    public SQLExpressionProvider<T9> getSQLExpressionProvider9() {
        if (sqlExpressionProvider9 == null) {
            sqlExpressionProvider9 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(8, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider9;
    }
    @Override
    public SQLExpressionProvider<T10> getSQLExpressionProvider10() {
        if (sqlExpressionProvider10 == null) {
            sqlExpressionProvider10 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(9, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider10;
    }
}
