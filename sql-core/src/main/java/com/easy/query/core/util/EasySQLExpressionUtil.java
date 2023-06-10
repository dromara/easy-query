package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.impl.AnonymousUnionQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;

import java.util.Collection;
import java.util.Objects;


/**
 * @Description: 文件说明
 * @Date: 2023/3/7 12:32
 * @author xuejiaming
 */
public class EasySQLExpressionUtil {
    private EasySQLExpressionUtil() {
    }

    public static boolean expressionInvokeRoot(ToSQLContext sqlContext){
        return sqlContext.expressionInvokeCountGetIncrement()==0;
    }
    public static void tableSQLExpressionRewrite(ToSQLContext sqlContext, EntityTableSQLExpression entityTableSQLExpression){
        SQLRewriteUnit sqlRewriteUnit = sqlContext.getSQLRewriteUnit();
        if(sqlRewriteUnit==null){
            return;
        }
        sqlRewriteUnit.rewriteTableName(entityTableSQLExpression);
    }

    public static String getTableAlias(ToSQLContext toSQLContext,TableAvailable table){
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
//        if(noSelectAndGroup(sqlEntityExpression)){
//            boolean onlyOneAnonymousTable = sqlEntityExpression.getTables().size() == 1 && sqlEntityExpression.getTables().get(0) instanceof AnonymousEntityTableExpression;
//            if(onlyOneAnonymousTable){
//                return true;
//            }
//            return hasAnyOperate(sqlEntityExpression);
//        }
//        return false;
        return noSelectAndGroup(sqlEntityExpression) && (moreTableExpressionOrNoAnonymous(sqlEntityExpression)||hasAnyOperate(sqlEntityExpression));
    }
    public static boolean limitAndOrderNotSetCurrent(EntityQueryExpressionBuilder sqlEntityExpression){
        return noSelectAndGroup(sqlEntityExpression)&&!moreTableExpressionOrNoAnonymous(sqlEntityExpression)&&!hasAnyOperate(sqlEntityExpression);
    }
    public static boolean noSelectAndGroup(EntityQueryExpressionBuilder sqlEntityExpression){
        return sqlEntityExpression.getProjects().isEmpty() && !sqlEntityExpression.hasGroup();
    }

    public static boolean moreTableExpressionOrNoAnonymous(EntityQueryExpressionBuilder sqlEntityExpression) {
       if(EasyCollectionUtil.isNotSingle(sqlEntityExpression.getTables())){
           return true;
       }
        EntityTableExpressionBuilder entityTableExpressionBuilder = EasyCollectionUtil.first(sqlEntityExpression.getTables());
       if(!(entityTableExpressionBuilder instanceof  AnonymousEntityTableExpressionBuilder)){
           return true;
       }
        AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder = (AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder;
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
        if(entityQueryExpressionBuilder instanceof AnonymousUnionQueryExpressionBuilder){
            return true;
        }
        return false;
//     return sqlEntityExpression.getTables().size() != 1 || !(sqlEntityExpression.getTables().get(0) instanceof AnonymousEntityTableExpressionBuilder);
    }

    public static boolean hasAnyOperate(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasWhere() || sqlEntityExpression.hasOrder() || sqlEntityExpression.hasHaving() || sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }

    public static boolean hasAnyOperateWithoutWhereAndOrder(EntityQueryExpressionBuilder sqlEntityExpression) {
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasHaving() || sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }

    public static <T1, T2> ClientQueryable2<T1, T2> executeJoinOn(ClientQueryable2<T1, T2> queryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate();
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate();
        on.apply(sqlOnPredicate1, sqlOnPredicate2);
        return queryable;
    }

    public static <T1, T2, T3> ClientQueryable3<T1, T2, T3> executeJoinOn(ClientQueryable3<T1, T2, T3> queryable, SQLExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate();
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate();
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate();
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3);
        return queryable;
    }

    public static <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> executeJoinOn(ClientQueryable4<T1, T2, T3, T4> queryable, SQLExpression4<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>> on) {
        WherePredicate<T1> sqlOnPredicate1 = queryable.getSQLExpressionProvider1().getOnPredicate();
        WherePredicate<T2> sqlOnPredicate2 = queryable.getSQLExpressionProvider2().getOnPredicate();
        WherePredicate<T3> sqlOnPredicate3 = queryable.getSQLExpressionProvider3().getOnPredicate();
        WherePredicate<T4> sqlOnPredicate4 = queryable.getSQLExpressionProvider4().getOnPredicate();
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3, sqlOnPredicate4);
        return queryable;
    }


    public static EntityQueryExpressionBuilder getCountEntityQueryExpression(EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        if (entityQueryExpressionBuilder.hasOrder()) {
            entityQueryExpressionBuilder.getOrder().clear();
        }
        if (EasySQLExpressionUtil.hasAnyOperateWithoutWhereAndOrder(entityQueryExpressionBuilder)) {
            return null;
        }

        //如果他只是匿名表那么就使用匿名表的内部表
        if(!EasySQLExpressionUtil.moreTableExpressionOrNoAnonymous(entityQueryExpressionBuilder)){
            AnonymousEntityTableExpressionBuilder table = (AnonymousEntityTableExpressionBuilder) entityQueryExpressionBuilder.getTable(0);
            EntityQueryExpressionBuilder entityQueryExpression = table.getEntityQueryExpressionBuilder().cloneEntityExpressionBuilder();
            //存在操作那么就返回父类
            if(!EasySQLExpressionUtil.hasAnyOperateWithoutWhereAndOrder(entityQueryExpression)){
                EntityQueryExpressionBuilder countEntityQueryExpression = getCountEntityQueryExpression(entityQueryExpression);
                if(countEntityQueryExpression!=null){
                    return countEntityQueryExpression;
                }
            }
        }
        entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
        SQLSegmentFactory sqlSegmentFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
        SelectConstSegment selectCountSegment = sqlSegmentFactory.createSelectConstSegment("COUNT(1)");
        entityQueryExpressionBuilder.getProjects().append(selectCountSegment);
        return entityQueryExpressionBuilder;
    }


    /**
     * 返回当前sql执行策略默认也会有一个指定的
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

    public static String getSQLOwnerColumn(QueryRuntimeContext runtimeContext, TableAvailable table, String propertyName,ToSQLContext toSQLContext){
        String alias = getTableAlias(toSQLContext,table);
        String columnName = table.getColumnName(propertyName);
        String quoteName = getQuoteName(runtimeContext, columnName);
        if (alias == null) {
            return quoteName;
        }
        return alias + "." + quoteName;
    }

    public static String getQuoteName(QueryRuntimeContext runtimeContext, String value) {
        return runtimeContext.getQueryConfiguration().getDialect().getQuoteName(value);
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

    public static boolean entityExecuteBatch(int entitySize,ExecutorContext executorContext){
        EasyQueryOption easyQueryOption = executorContext.getEasyQueryOption();
        ExecuteMethodEnum executeMethod = executorContext.getExecuteMethod();
        if(Objects.equals(ExecuteMethodEnum.INSERT,executeMethod)){
            return entitySize>=easyQueryOption.getInsertBatchThreshold();
        }
        if(Objects.equals(ExecuteMethodEnum.UPDATE,executeMethod)){
            return entitySize>=easyQueryOption.getUpdateBatchThreshold();
        }
        return false;
    }
}
