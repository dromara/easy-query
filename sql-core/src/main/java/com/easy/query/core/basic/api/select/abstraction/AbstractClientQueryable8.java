package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.extension.queryable8.override.AbstractOverrideClientQueryable8;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLActionExpression8;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * create time 2023/3/9 12:38
 */
public abstract class AbstractClientQueryable8<T1, T2, T3, T4,T5,T6,T7,T8> extends AbstractOverrideClientQueryable8<T1, T2, T3, T4,T5,T6,T7,T8> implements ClientQueryable8<T1, T2, T3, T4,T5,T6,T7,T8> {
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;
    protected SQLExpressionProvider<T4> sqlExpressionProvider4;
    protected SQLExpressionProvider<T5> sqlExpressionProvider5;
    protected SQLExpressionProvider<T6> sqlExpressionProvider6;
    protected SQLExpressionProvider<T7> sqlExpressionProvider7;
    protected SQLExpressionProvider<T8> sqlExpressionProvider8;


    public AbstractClientQueryable8(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class,Class<T8> t8Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, t4Class,t5Class,t6Class,t7Class,t8Class, sqlEntityExpression);
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4,T5, T6,T7,T8> getClientQueryable8() {
        return this;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return this;
    }


    @Override
    public <T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> leftJoin(Class<T9> joinClass, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, t2Class, t3Class, t4Class, t5Class,t6Class,t7Class,t8Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> leftJoin(ClientQueryable<T9> joinQueryable, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        ClientQueryable<T9> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, t2Class, t3Class, t4Class, t5Class,t6Class,t7Class,t8Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> rightJoin(Class<T9> joinClass, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, t2Class, t3Class, t4Class, t5Class,t6Class,t7Class,t8Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> rightJoin(ClientQueryable<T9> joinQueryable, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        ClientQueryable<T9> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, t2Class, t3Class, t4Class, t5Class,t6Class,t7Class,t8Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> innerJoin(Class<T9> joinClass, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, t2Class, t3Class, t4Class, t5Class,t6Class,t7Class,t8Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> innerJoin(ClientQueryable<T9> joinQueryable, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        ClientQueryable<T9> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, t2Class, t3Class, t4Class,t5Class,t6Class,t7Class,t8Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4,T5, T6,T7,T8> where(boolean condition, SQLActionExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> whereExpression) {
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
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2, sqlWherePredicate3, sqlWherePredicate4,sqlWherePredicate5,sqlWherePredicate6,sqlWherePredicate7,sqlWherePredicate8);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression8<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>, ColumnAsSelector<T8, TR>> selectExpression) {

        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSQLExpressionProvider4().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T5, TR> sqlColumnAsSelector5 = getSQLExpressionProvider5().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T6, TR> sqlColumnAsSelector6 = getSQLExpressionProvider6().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T7, TR> sqlColumnAsSelector7 = getSQLExpressionProvider7().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T8, TR> sqlColumnAsSelector8 = getSQLExpressionProvider8().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2, sqlColumnAsSelector3, sqlColumnAsSelector4,sqlColumnAsSelector5,sqlColumnAsSelector6,sqlColumnAsSelector7,sqlColumnAsSelector8);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression8<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>, ColumnAsSelector<T8, TR>> selectExpression, boolean replace) {
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
            selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3,sqlColumnAsSelector4,sqlColumnAsSelector5,sqlColumnAsSelector6,sqlColumnAsSelector7,sqlColumnAsSelector8);
        }
        return select(resultClass);
    }

    private <TMember> List<TMember> selectAggregateList(SQLActionExpression8<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>> columnSelectorExpression, ColumnFunction columnFunction, Class<TMember> resultClass) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T3> sqlColumnResultSelector3 = getSQLExpressionProvider3().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T4> sqlColumnResultSelector4 = getSQLExpressionProvider4().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T5> sqlColumnResultSelector5 = getSQLExpressionProvider5().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T6> sqlColumnResultSelector6 = getSQLExpressionProvider6().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T7> sqlColumnResultSelector7 = getSQLExpressionProvider7().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T8> sqlColumnResultSelector8 = getSQLExpressionProvider8().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2, sqlColumnResultSelector3, sqlColumnResultSelector4,sqlColumnResultSelector5,sqlColumnResultSelector6,sqlColumnResultSelector7,sqlColumnResultSelector8);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment) projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        Class<TMember> tMemberClass = resultClass == null ? (Class<TMember>) table.getEntityMetadata().getColumnNotNull(propertyName).getPropertyType() : resultClass;
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, entityQueryExpressionBuilder.getExpressionContext(), columnFunction, null);

        return cloneQueryable().select(funcColumnSegment, true).toList(tMemberClass);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLActionExpression8<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLActionExpression8<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>> columnSelectorExpression, TMember def) {
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLActionExpression8<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>> columnSelectorExpression, TMember def) {
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLActionExpression8<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>> columnSelectorExpression, TMember def) {
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLActionExpression8<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>, ColumnResultSelector<T6>, ColumnResultSelector<T7>, ColumnResultSelector<T8>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = selectAggregateList(columnSelectorExpression, avgFunction, resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }


    @Override
    public ClientQueryable8<T1, T2, T3, T4,T5, T6,T7,T8> groupBy(boolean condition, SQLActionExpression8<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>> selectExpression) {
        if (condition) {
            ColumnGroupSelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            ColumnGroupSelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            ColumnGroupSelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getGroupColumnSelector();
            ColumnGroupSelector<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getGroupColumnSelector();
            ColumnGroupSelector<T5> sqlGroupSelector5 = getSQLExpressionProvider5().getGroupColumnSelector();
            ColumnGroupSelector<T6> sqlGroupSelector6 = getSQLExpressionProvider6().getGroupColumnSelector();
            ColumnGroupSelector<T7> sqlGroupSelector7 = getSQLExpressionProvider7().getGroupColumnSelector();
            ColumnGroupSelector<T8> sqlGroupSelector8 = getSQLExpressionProvider8().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4,sqlGroupSelector5,sqlGroupSelector6,sqlGroupSelector7,sqlGroupSelector8);
        }
        return this;
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4,T5, T6,T7,T8> having(boolean condition, SQLActionExpression8<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            WhereAggregatePredicate<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getAggregatePredicate();
            WhereAggregatePredicate<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getAggregatePredicate();
            WhereAggregatePredicate<T5> sqlGroupSelector5 = getSQLExpressionProvider5().getAggregatePredicate();
            WhereAggregatePredicate<T6> sqlGroupSelector6 = getSQLExpressionProvider6().getAggregatePredicate();
            WhereAggregatePredicate<T7> sqlGroupSelector7 = getSQLExpressionProvider7().getAggregatePredicate();
            WhereAggregatePredicate<T8> sqlGroupSelector8 = getSQLExpressionProvider8().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4,sqlGroupSelector5,sqlGroupSelector6,sqlGroupSelector7,sqlGroupSelector8);
        }
        return this;
    }


    @Override
    public ClientQueryable8<T1, T2, T3, T4,T5, T6,T7,T8> orderByAsc(boolean condition, SQLActionExpression8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(true);
            ColumnOrderSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(true);
            ColumnOrderSelector<T5> sqlOrderColumnSelector5 = getSQLExpressionProvider5().getOrderColumnSelector(true);
            ColumnOrderSelector<T6> sqlOrderColumnSelector6 = getSQLExpressionProvider6().getOrderColumnSelector(true);
            ColumnOrderSelector<T7> sqlOrderColumnSelector7 = getSQLExpressionProvider7().getOrderColumnSelector(true);
            ColumnOrderSelector<T8> sqlOrderColumnSelector8 = getSQLExpressionProvider8().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4,sqlOrderColumnSelector5,sqlOrderColumnSelector6,sqlOrderColumnSelector7,sqlOrderColumnSelector8);
        }
        return this;
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4,T5, T6,T7,T8> orderByDesc(boolean condition, SQLActionExpression8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(false);
            ColumnOrderSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(false);
            ColumnOrderSelector<T5> sqlOrderColumnSelector5 = getSQLExpressionProvider5().getOrderColumnSelector(false);
            ColumnOrderSelector<T6> sqlOrderColumnSelector6 = getSQLExpressionProvider6().getOrderColumnSelector(false);
            ColumnOrderSelector<T7> sqlOrderColumnSelector7 = getSQLExpressionProvider7().getOrderColumnSelector(false);
            ColumnOrderSelector<T8> sqlOrderColumnSelector8 = getSQLExpressionProvider8().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4,sqlOrderColumnSelector5,sqlOrderColumnSelector6,sqlOrderColumnSelector7,sqlOrderColumnSelector8);
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
}
