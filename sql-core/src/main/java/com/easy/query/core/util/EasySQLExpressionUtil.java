package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.expression.lambda.SQLExpression10;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.expression.lambda.SQLExpression8;
import com.easy.query.core.expression.lambda.SQLExpression9;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.scec.expression.ColumnMultiParamExpression;
import com.easy.query.core.expression.segment.scec.expression.ColumnParamExpression;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyAsAliasParamExpression;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyParamExpression;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpression;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SQLFormatArgument;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpression;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousUnionQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.AnonymousUnionEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.Collection;
import java.util.Objects;


/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/7 12:32
 */
public class EasySQLExpressionUtil {
    private EasySQLExpressionUtil() {
    }

    public static boolean expressionInvokeRoot(ToSQLContext sqlContext) {
        return sqlContext.expressionInvokeCountGetIncrement() == 0;
    }

    public static void tableSQLExpressionRewrite(ToSQLContext sqlContext, EntityTableSQLExpression entityTableSQLExpression) {
        SQLRewriteUnit sqlRewriteUnit = sqlContext.getSQLRewriteUnit();
        if (sqlRewriteUnit == null) {
            return;
        }
        sqlRewriteUnit.rewriteTableName(entityTableSQLExpression);
    }

    public static String getTableAlias(ToSQLContext toSQLContext, TableAvailable table) {
        return toSQLContext.getAlias(table);
    }

    public static <TSource> ClientQueryable<TSource> cloneAndSelectAllQueryable(ClientQueryable<TSource> queryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = queryable.getSQLEntityExpressionBuilder();
        if (EasySQLExpressionUtil.shouldCloneSQLEntityQueryExpressionBuilder(sqlEntityExpressionBuilder)) {
            return queryable.cloneQueryable().select(ColumnSelector::columnAll);
        }
        return queryable.cloneQueryable();
    }

    /**
     * 判断当前表达式是否需要对其进行select all 如果需要select all那么就表示需要克隆一个
     *
     * @param sqlEntityExpression
     * @return
     */
    public static boolean shouldCloneSQLEntityQueryExpressionBuilder(EntityQueryExpressionBuilder sqlEntityExpression) {
        return noSelectAndGroup(sqlEntityExpression) && (moreTableExpressionOrNoAnonymous(sqlEntityExpression) || hasAnyOperate(sqlEntityExpression));
    }

    public static boolean limitAndOrderNotSetCurrent(EntityQueryExpressionBuilder sqlEntityExpression) {
        return noSelectAndGroup(sqlEntityExpression) && !moreTableExpressionOrNoAnonymous(sqlEntityExpression) && !hasAnyOperate(sqlEntityExpression);
    }

    public static boolean noSelectAndGroup(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.getProjects().isEmpty() && !sqlEntityExpression.hasGroup();
    }

    public static boolean onlyNativeSqlExpression(EntityQueryExpressionBuilder entityQueryExpressionBuilder){
        if(entityQueryExpressionBuilder instanceof SQLAnonymousEntityQueryExpressionBuilder){
            return EasyCollectionUtil.isEmpty(entityQueryExpressionBuilder.getTables());
        }
        return false;
    }
    public static boolean moreTableExpressionOrNoAnonymous(EntityQueryExpressionBuilder sqlEntityExpression) {
        if (EasyCollectionUtil.isNotSingle(sqlEntityExpression.getTables())) {
            return true;
        }
        EntityTableExpressionBuilder entityTableExpressionBuilder = EasyCollectionUtil.first(sqlEntityExpression.getTables());
        if (!(entityTableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder)) {
            return true;
        }
        AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder = (AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder;
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
        if (entityQueryExpressionBuilder instanceof AnonymousUnionQueryExpressionBuilder) {
            return true;
        }
        return false;
//     return sqlEntityExpression.getTables().size() != 1 || !(sqlEntityExpression.getTables().get(0) instanceof AnonymousEntityTableExpressionBuilder);
    }

    /**
     * 后续是否要判断include
     *
     * @param sqlEntityExpression
     * @return
     */
    public static boolean hasAnyOperate(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasWhere() || sqlEntityExpression.hasOrder() || sqlEntityExpression.hasHaving() || sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }

    public static boolean hasAnyOperateWithoutWhereAndOrder(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasHaving() || sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }
    public static boolean hasAnyOperateWithoutWhereAndOrderAndDistinct(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasHaving() || sqlEntityExpression.hasGroup();
    }
    public static boolean hasOnlyOperateWithDistinct(EntityQueryExpressionBuilder sqlEntityExpression) {
        return !sqlEntityExpression.hasLimit() && !sqlEntityExpression.hasHaving() && sqlEntityExpression.isDistinct() && !sqlEntityExpression.hasGroup();
    }

    public static <T1, T2> ClientQueryable2<T1, T2> executeJoinOn(ClientQueryable2<T1, T2> queryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2);
        return queryable;
    }

    public static <T1, T2, T3> ClientQueryable3<T1, T2, T3> executeJoinOn(ClientQueryable3<T1, T2, T3> queryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3);
        return queryable;
    }

    public static <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> executeJoinOn(ClientQueryable4<T1, T2, T3, T4> queryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4);
        return queryable;
    }

    public static <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> executeJoinOn(ClientQueryable5<T1, T2, T3, T4,T5> queryable, SQLExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        WherePredicate<T5> sqlOnPredicate5 = queryable.getSQLExpressionProvider5().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4, sqlOnPredicate5);
        return queryable;
    }
    public static <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> executeJoinOn(ClientQueryable6<T1, T2, T3, T4,T5,T6> queryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        WherePredicate<T5> sqlOnPredicate5 = queryable.getSQLExpressionProvider5().getOnPredicate(onWhereFilterContext);
        WherePredicate<T6> sqlOnPredicate6 = queryable.getSQLExpressionProvider6().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4, sqlOnPredicate5,sqlOnPredicate6);
        return queryable;
    }
    public static <T1, T2, T3, T4, T5, T6,T7> ClientQueryable7<T1, T2, T3, T4, T5, T6,T7> executeJoinOn(ClientQueryable7<T1, T2, T3, T4,T5,T6,T7> queryable, SQLExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        WherePredicate<T5> sqlOnPredicate5 = queryable.getSQLExpressionProvider5().getOnPredicate(onWhereFilterContext);
        WherePredicate<T6> sqlOnPredicate6 = queryable.getSQLExpressionProvider6().getOnPredicate(onWhereFilterContext);
        WherePredicate<T7> sqlOnPredicate7 = queryable.getSQLExpressionProvider7().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4, sqlOnPredicate5,sqlOnPredicate6,sqlOnPredicate7);
        return queryable;
    }
    public static <T1, T2, T3, T4, T5, T6,T7,T8> ClientQueryable8<T1, T2, T3, T4, T5, T6,T7,T8> executeJoinOn(ClientQueryable8<T1, T2, T3, T4,T5,T6,T7,T8> queryable, SQLExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        WherePredicate<T5> sqlOnPredicate5 = queryable.getSQLExpressionProvider5().getOnPredicate(onWhereFilterContext);
        WherePredicate<T6> sqlOnPredicate6 = queryable.getSQLExpressionProvider6().getOnPredicate(onWhereFilterContext);
        WherePredicate<T7> sqlOnPredicate7 = queryable.getSQLExpressionProvider7().getOnPredicate(onWhereFilterContext);
        WherePredicate<T8> sqlOnPredicate8 = queryable.getSQLExpressionProvider8().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4, sqlOnPredicate5,sqlOnPredicate6,sqlOnPredicate7,sqlOnPredicate8);
        return queryable;
    }
    public static <T1, T2, T3, T4, T5, T6,T7,T8,T9> ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> executeJoinOn(ClientQueryable9<T1, T2, T3, T4,T5,T6,T7,T8,T9> queryable, SQLExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        WherePredicate<T5> sqlOnPredicate5 = queryable.getSQLExpressionProvider5().getOnPredicate(onWhereFilterContext);
        WherePredicate<T6> sqlOnPredicate6 = queryable.getSQLExpressionProvider6().getOnPredicate(onWhereFilterContext);
        WherePredicate<T7> sqlOnPredicate7 = queryable.getSQLExpressionProvider7().getOnPredicate(onWhereFilterContext);
        WherePredicate<T8> sqlOnPredicate8 = queryable.getSQLExpressionProvider8().getOnPredicate(onWhereFilterContext);
        WherePredicate<T9> sqlOnPredicate9 = queryable.getSQLExpressionProvider9().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4, sqlOnPredicate5,sqlOnPredicate6,sqlOnPredicate7,sqlOnPredicate8,sqlOnPredicate9);
        return queryable;
    }
    public static <T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> ClientQueryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> executeJoinOn(ClientQueryable10<T1, T2, T3, T4,T5,T6,T7,T8,T9,T10> queryable, SQLExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> on) {
        FilterContext onWhereFilterContext = queryable.getSQLExpressionProvider1().getOnWhereFilterContext();
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate(onWhereFilterContext);
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate(onWhereFilterContext);
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate(onWhereFilterContext);
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate(onWhereFilterContext);
        WherePredicate<T5> sqlOnPredicate5 = queryable.getSQLExpressionProvider5().getOnPredicate(onWhereFilterContext);
        WherePredicate<T6> sqlOnPredicate6 = queryable.getSQLExpressionProvider6().getOnPredicate(onWhereFilterContext);
        WherePredicate<T7> sqlOnPredicate7 = queryable.getSQLExpressionProvider7().getOnPredicate(onWhereFilterContext);
        WherePredicate<T8> sqlOnPredicate8 = queryable.getSQLExpressionProvider8().getOnPredicate(onWhereFilterContext);
        WherePredicate<T9> sqlOnPredicate9 = queryable.getSQLExpressionProvider9().getOnPredicate(onWhereFilterContext);
        WherePredicate<T10> sqlOnPredicate10 = queryable.getSQLExpressionProvider10().getOnPredicate(onWhereFilterContext);
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4, sqlOnPredicate5,sqlOnPredicate6,sqlOnPredicate7,sqlOnPredicate8,sqlOnPredicate9,sqlOnPredicate10);
        return queryable;
    }


    public static EntityQueryExpressionBuilder getCountEntityQueryExpression(EntityQueryExpressionBuilder entityQueryExpressionBuilder,boolean isDistinct) {
        processRemoveOrderAndLimit(entityQueryExpressionBuilder);
        if (EasySQLExpressionUtil.hasAnyOperateWithoutWhereAndOrder(entityQueryExpressionBuilder)) {
            return null;
        }
        if(EasySQLExpressionUtil.onlyNativeSqlExpression(entityQueryExpressionBuilder)){
            return null;
        }

        if(!entityQueryExpressionBuilder.hasWhere()){
            //如果他只是匿名表那么就使用匿名表的内部表
            if (!EasySQLExpressionUtil.moreTableExpressionOrNoAnonymous(entityQueryExpressionBuilder)) {
                AnonymousEntityTableExpressionBuilder table = (AnonymousEntityTableExpressionBuilder) entityQueryExpressionBuilder.getTable(0);
                EntityQueryExpressionBuilder entityQueryExpression = table.getEntityQueryExpressionBuilder().cloneEntityExpressionBuilder();
                //存在操作那么就返回父类
                if (!EasySQLExpressionUtil.hasAnyOperateWithoutWhereAndOrder(entityQueryExpression)) {
                    EntityQueryExpressionBuilder countEntityQueryExpression = getCountEntityQueryExpression(entityQueryExpression,isDistinct||entityQueryExpression.isDistinct());
                    if (countEntityQueryExpression != null) {
                        return countEntityQueryExpression;
                    }
                }else{
                    if (EasySQLExpressionUtil.hasOnlyOperateWithDistinct(entityQueryExpression)) {

                        processRemoveOrderAndLimit(entityQueryExpression);
                        return processSelectCountProject(entityQueryExpression,isDistinct||entityQueryExpression.isDistinct());
                    }
                }
            }
        }
        return processSelectCountProject(entityQueryExpressionBuilder,isDistinct);
    }

    private static void processRemoveOrderAndLimit(EntityQueryExpressionBuilder entityQueryExpressionBuilder){
        if (entityQueryExpressionBuilder.hasOrder()) {
            entityQueryExpressionBuilder.getOrder().clear();
        }
        if (entityQueryExpressionBuilder.hasLimit()) {
            entityQueryExpressionBuilder.setOffset(0);
            entityQueryExpressionBuilder.setRows(0);
        }
    }
    /**
     * 如果是distinct 的count需要做特殊处理
     * @param entityQueryExpressionBuilder
     * @param isDistinct
     * @return
     */
    private static EntityQueryExpressionBuilder processSelectCountProject(EntityQueryExpressionBuilder entityQueryExpressionBuilder,boolean isDistinct){
        SQLSegmentFactory sqlSegmentFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
        if(isDistinct){
            SQLBuilderSegment sqlBuilderSegment = entityQueryExpressionBuilder.getProjects().cloneSQLBuilder();
            entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
            SQLSegment sqlSegment = sqlSegmentFactory.createSelectCountDistinctSegment(sqlBuilderSegment.getSQLSegments());
            entityQueryExpressionBuilder.getProjects().append(sqlSegment);
            entityQueryExpressionBuilder.setDistinct(false);

        }else{
            entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
            SQLSegment sqlSegment = sqlSegmentFactory.createSelectConstSegment("COUNT(*)");
            entityQueryExpressionBuilder.getProjects().append(sqlSegment);
        }
        return entityQueryExpressionBuilder;
    }


    /**
     * 返回当前sql执行策略默认也会有一个指定的
     *
     * @param expressionContext
     * @return
     */
    public static SQLExecuteStrategyEnum getExecuteStrategy(ExpressionContext expressionContext, ExecutorContext executorContext) {

        SQLExecuteStrategyEnum sqlStrategy = expressionContext.getSQLStrategy();
        //非默认的那么也不可以是全部
        if (!SQLExecuteStrategyEnum.DEFAULT.equals(sqlStrategy)) {
            return sqlStrategy;
        }
        //如果是默认那么判断全局不是all columns即可
        EasyQueryOption easyQueryOption = expressionContext.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        if (Objects.equals(ExecuteMethodEnum.INSERT, executorContext.getExecuteMethod())) {
            return easyQueryOption.getInsertStrategy();
        } else if (Objects.equals(ExecuteMethodEnum.UPDATE, executorContext.getExecuteMethod())) {
            return easyQueryOption.getUpdateStrategy();
        }
        throw new UnsupportedOperationException("cant get sql execute strategy");
    }

    /**
     * 是否个性化执行sql eg. null列更新,非null列更新等
     *
     * @param expressionContext
     * @return
     */
    public static boolean sqlExecuteStrategyIsAllColumns(ExpressionContext expressionContext, ExecutorContext executorContext) {
        SQLExecuteStrategyEnum executeStrategy = getExecuteStrategy(expressionContext, executorContext);
        return SQLExecuteStrategyEnum.ALL_COLUMNS == executeStrategy;
    }

    public static String getSQLOwnerColumnByProperty(QueryRuntimeContext runtimeContext, TableAvailable table, String propertyName, ToSQLContext toSQLContext) {
        String columnName = table.getColumnName(propertyName);
        return getSQLOwnerColumn(runtimeContext, table, columnName, toSQLContext);
    }

    public static String getSQLOwnerColumnMetadata(QueryRuntimeContext runtimeContext, TableAvailable table, ColumnMetadata columnMetadata, ToSQLContext toSQLContext) {
        return getSQLOwnerColumn(runtimeContext, table, columnMetadata.getName(), toSQLContext);
    }

    public static String getSQLOwnerColumn(QueryRuntimeContext runtimeContext, TableAvailable table, String columnName, ToSQLContext toSQLContext) {
        if (columnName == null) {
            throw new IllegalArgumentException("column name cannot be null");
        }
        String tableAlias = getTableAlias(toSQLContext, table);
        String quoteName = getQuoteName(runtimeContext, columnName);
        if (tableAlias == null) {
            return quoteName;
        }
        return tableAlias + "." + quoteName;
    }


    public static String getQuoteName(QueryRuntimeContext runtimeContext, String value) {
        return runtimeContext.getQueryConfiguration().getDialect().getQuoteName(value);
    }

    public static String getCTEColumns(QueryRuntimeContext runtimeContext, EntityQuerySQLExpression querySQLExpression){

        boolean unionExpression = querySQLExpression instanceof AnonymousUnionEntityQuerySQLExpression;
        if (!unionExpression) {
            throw new EasyQueryInvalidOperationException("querySQLExpression not instanceof AnonymousUnionEntityQuerySQLExpression");
        }
        EntityQuerySQLExpression entityQuerySQLExpression = ((AnonymousUnionEntityQuerySQLExpression) querySQLExpression).getEntityQuerySQLExpressions().get(0);
        SQLBuilderSegment projects = entityQuerySQLExpression.getProjects();
        if (EasySQLSegmentUtil.isEmpty(projects)) {
            throw new EasyQueryInvalidOperationException("projects is empty");
        }
        StringBuilder columns = new StringBuilder();
        int i = 0;
        for (SQLSegment sqlSegment : projects.getSQLSegments()) {
            if (!(sqlSegment instanceof SQLEntityAliasSegment)) {
                throw new EasyQueryInvalidOperationException("sqlSegment not instanceof SQLEntityAliasSegment");
            }
            SQLEntityAliasSegment sqlEntityAliasSegment = (SQLEntityAliasSegment) sqlSegment;
            if (i++ != 0) {
                columns.append(",");
            }
            if (sqlEntityAliasSegment.getAlias() != null) {
                columns.append(EasySQLExpressionUtil.getQuoteName(runtimeContext, sqlEntityAliasSegment.getAlias()));
            } else if (sqlEntityAliasSegment.getTable() != null && sqlEntityAliasSegment.getPropertyName() != null) {
                columns.append(EasySQLExpressionUtil.getQuoteName(runtimeContext, sqlEntityAliasSegment.getTable().getColumnName(sqlEntityAliasSegment.getPropertyName())));
            }else{
                throw new EasyQueryInvalidOperationException("sqlSegment is SQLEntityAliasSegment,can not get column name");
            }
        }
        return columns.toString();
    }


    public static String getSingleKeyPropertyName(TableAvailable table) {
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if (EasyCollectionUtil.isEmpty(keyProperties)) {
            throw new EasyQueryNoPrimaryKeyException("entity:" + EasyClassUtil.getSimpleName(table.getEntityMetadata().getEntityClass()) + " no primary key");
        }
        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryMultiPrimaryKeyException("entity:" + EasyClassUtil.getSimpleName(table.getEntityMetadata().getEntityClass()) + " has multi primary key");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    public static boolean entityExecuteBatch(int entitySize, ExecutorContext executorContext) {
        EasyQueryOption easyQueryOption = executorContext.getEasyQueryOption();
        ExecuteMethodEnum executeMethod = executorContext.getExecuteMethod();
        if (Objects.equals(ExecuteMethodEnum.INSERT, executeMethod)) {
            return entitySize >= easyQueryOption.getInsertBatchThreshold();
        }
        if (Objects.equals(ExecuteMethodEnum.UPDATE, executeMethod)) {
            return entitySize >= easyQueryOption.getUpdateBatchThreshold();
        }
        return false;
    }


    public static Object parseParamExpression(QueryRuntimeContext runtimeContext, ParamExpression paramExpression, ToSQLContext toSQLContext) {
        if (paramExpression instanceof ColumnPropertyParamExpression) {
            ColumnPropertyParamExpression columnPropertyExpression = (ColumnPropertyParamExpression) paramExpression;
            return SQLFormatArgument.create(()->{
                return columnPropertyExpression.toSQL(runtimeContext, toSQLContext);
            });

        } else if (paramExpression instanceof ColumnParamExpression) {
            ColumnParamExpression columnParamExpression = (ColumnParamExpression) paramExpression;
            return SQLFormatArgument.create(()->{
                columnParamExpression.addParams(toSQLContext);
                return "?";
            });
        }  else if (paramExpression instanceof ColumnMultiParamExpression) {
            ColumnMultiParamExpression columnMultiParamExpression = (ColumnMultiParamExpression) paramExpression;
            return SQLFormatArgument.create(()->{
                columnMultiParamExpression.addParams(toSQLContext);
                return EasyCollectionUtil.join(columnMultiParamExpression.getParamSize(),",","?");
            });
        } else if(paramExpression instanceof FormatValueParamExpression){
            FormatValueParamExpression constValueParamExpression = (FormatValueParamExpression) paramExpression;
            return SQLFormatArgument.create(constValueParamExpression::toSQLSegment);
        } else if(paramExpression instanceof SubQueryParamExpression){
            SubQueryParamExpression subQueryParamExpression = (SubQueryParamExpression) paramExpression;
            return SQLFormatArgument.create(()-> subQueryParamExpression.toSQL(toSQLContext));
        } else if(paramExpression instanceof ColumnPropertyAsAliasParamExpression){
            ColumnPropertyAsAliasParamExpression columnPropertyAsAliasParamExpression = (ColumnPropertyAsAliasParamExpression) paramExpression;
            return SQLFormatArgument.create(()-> columnPropertyAsAliasParamExpression.toSQL(runtimeContext));
        }else if(paramExpression instanceof SQLSegmentParamExpression){
            SQLSegmentParamExpression sqlSegmentParamExpression = (SQLSegmentParamExpression) paramExpression;
            return SQLFormatArgument.create(()-> sqlSegmentParamExpression.toSQL(toSQLContext));
        }
        throw new EasyQueryInvalidOperationException("can not process ParamExpression:" + EasyClassUtil.getInstanceSimpleName(paramExpression));
    }
}
