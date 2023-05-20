package com.easy.query.core.util;

import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.basic.api.select.Queryable4;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.impl.AnonymousUnionQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;


/**
 * @Description: 文件说明
 * @Date: 2023/3/7 12:32
 * @author xuejiaming
 */
public class EasySQLExpressionUtil {
    private EasySQLExpressionUtil() {
    }

    public static boolean expressionInvokeRoot(ToSQLContext sqlContext){
        if(sqlContext==null){
            return false;
        }
        return sqlContext.expressionInvokeCountGetIncrement()==0;
    }
    public static void tableSQLExpressionRewrite(ToSQLContext sqlContext, EntityTableSQLExpression entityTableSQLExpression){
        if(sqlContext==null){
            return;
        }
        SQLRewriteUnit sqlRewriteUnit = sqlContext.getSqlRewriteUnit();
        if(sqlRewriteUnit==null){
            return;
        }
        sqlRewriteUnit.rewriteTableName(entityTableSQLExpression);
    }
    public static <TSource> Queryable<TSource> cloneAndSelectAllQueryable(Queryable<TSource> queryable) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = queryable.getSQLEntityExpressionBuilder();
        if (EasySQLExpressionUtil.shouldCloneSQLEntityQueryExpressionBuilder(sqlEntityExpressionBuilder)) {
            return queryable.cloneQueryable().select(SQLColumnSelector::columnAll);
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
    public static boolean hasAnyOperate(EntityQueryExpressionBuilder sqlEntityExpression){
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasWhere() || sqlEntityExpression.hasOrder() || sqlEntityExpression.hasHaving()|| sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }
    public static boolean hasAnyOperateWithoutWhereAndOrder(EntityQueryExpressionBuilder sqlEntityExpression){
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasHaving()|| sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }

    public static <T1, T2> Queryable2<T1, T2> executeJoinOn(Queryable2<T1, T2> queryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        SQLWherePredicate<T1> sqlOnPredicate1 = queryable.getSqlExpressionProvider1().getSQLOnPredicate();
        SQLWherePredicate<T2> sqlOnPredicate2 = queryable.getSqlExpressionProvider2().getSQLOnPredicate();
        on.apply(sqlOnPredicate1, sqlOnPredicate2);
        return queryable;
    }

    public static <T1, T2, T3> Queryable3<T1, T2, T3> executeJoinOn(Queryable3<T1, T2, T3> queryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        SQLWherePredicate<T1> sqlOnPredicate1 = queryable.getSqlExpressionProvider1().getSQLOnPredicate();
        SQLWherePredicate<T2> sqlOnPredicate2 = queryable.getSqlExpressionProvider2().getSQLOnPredicate();
        SQLWherePredicate<T3> sqlOnPredicate3 = queryable.getSqlExpressionProvider3().getSQLOnPredicate();
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3);
        return queryable;
    }
    public static <T1, T2, T3,T4> Queryable4<T1, T2, T3,T4> executeJoinOn(Queryable4<T1, T2, T3,T4> queryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        SQLWherePredicate<T1> sqlOnPredicate1 = queryable.getSqlExpressionProvider1().getSQLOnPredicate();
        SQLWherePredicate<T2> sqlOnPredicate2 = queryable.getSqlExpressionProvider2().getSQLOnPredicate();
        SQLWherePredicate<T3> sqlOnPredicate3 = queryable.getSqlExpressionProvider3().getSQLOnPredicate();
        SQLWherePredicate<T4> sqlOnPredicate4 = queryable.getSqlExpressionProvider4().getSQLOnPredicate();
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3,sqlOnPredicate4);
        return queryable;
    }


    public static EntityQueryExpressionBuilder getCountEntityQueryExpression(EntityQueryExpressionBuilder countSQLEntityExpression){
        if(countSQLEntityExpression.hasOrder()){
            countSQLEntityExpression.getOrder().clear();
        }
        if(EasySQLExpressionUtil.hasAnyOperateWithoutWhereAndOrder(countSQLEntityExpression)){
            return null;
        }

        //如果他只是匿名表那么就使用匿名表的内部表
        if(!EasySQLExpressionUtil.moreTableExpressionOrNoAnonymous(countSQLEntityExpression)){
            AnonymousEntityTableExpressionBuilder table = (AnonymousEntityTableExpressionBuilder) countSQLEntityExpression.getTable(0);
            EntityQueryExpressionBuilder entityQueryExpression = table.getEntityQueryExpressionBuilder().cloneEntityExpressionBuilder();
            //存在操作那么就返回父类
            if(!EasySQLExpressionUtil.hasAnyOperateWithoutWhereAndOrder(entityQueryExpression)){
                EntityQueryExpressionBuilder countEntityQueryExpression = getCountEntityQueryExpression(entityQueryExpression);
                if(countEntityQueryExpression!=null){
                    return countEntityQueryExpression;
                }
            }
        }
        countSQLEntityExpression.getProjects().getSQLSegments().clear();
        countSQLEntityExpression.getProjects().append(new SelectConstSegment("COUNT(1)"));
        return countSQLEntityExpression;
    }


    /**
     * 返回当前sql执行策略默认也会有一个指定的
     * @param expressionContext
     * @return
     */
    public static SQLExecuteStrategyEnum getExecuteStrategy(ExpressionContext expressionContext){

        SQLExecuteStrategyEnum sqlStrategy = expressionContext.getSQLStrategy();
        //非默认的那么也不可以是全部
        if (SQLExecuteStrategyEnum.DEFAULT.equals(sqlStrategy)) {
            //如果是默认那么判断全局不是all columns即可
            EasyQueryOption easyQueryOption = expressionContext.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
            return easyQueryOption.getUpdateStrategy();
        } else {
            return sqlStrategy;
        }
    }

    /**
     * 是否个性化执行sql eg. null列更新,非null列更新等
     * @param expressionContext
     * @return
     */
    public static boolean sqlExecuteStrategyNonDefault(ExpressionContext expressionContext){
        SQLExecuteStrategyEnum executeStrategy = getExecuteStrategy(expressionContext);
        return SQLExecuteStrategyEnum.ALL_COLUMNS!=executeStrategy;
    }

    public static String getSQLOwnerColumn(QueryRuntimeContext runtimeContext, TableAvailable table, String propertyName){
        String alias = table.getAlias();
        String columnName = table.getColumnName(propertyName);
        String quoteName = getQuoteName(runtimeContext,columnName);
        if (alias == null) {
            return quoteName;
        }
        return alias + "." + quoteName;
    }
    public static String getQuoteName(QueryRuntimeContext runtimeContext, String value) {
        return runtimeContext.getQueryConfiguration().getDialect().getQuoteName(value);
    }
}
