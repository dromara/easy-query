package com.easy.query.core.util;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SqlExpression2;
import com.easy.query.core.expression.lambda.SqlExpression3;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.basic.api.select.Queryable4;
import com.easy.query.core.expression.lambda.SqlExpression4;
import com.easy.query.core.expression.sql.builder.ExpressionContext;


/**
 * @FileName: SqlExpressionUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:32
 * @author xuejiaming
 */
public class SqlExpressionUtil {
    private SqlExpressionUtil() {
    }

    public static <TSource> Queryable<TSource> cloneAndSelectAllQueryable(Queryable<TSource> queryable) {
        EntityQueryExpressionBuilder queryableSqlEntityExpression = queryable.getSqlEntityExpressionBuilder();
        if (SqlExpressionUtil.shouldCloneSqlEntityQueryExpression(queryableSqlEntityExpression)) {
            return queryable.cloneQueryable().select(SqlColumnSelector::columnAll);
        }
        return queryable;
    }

    /**
     * 判断当前表达式是否需要对其进行select all 如果需要select all那么就表示需要克隆一个
     *
     * @param sqlEntityExpression
     * @return
     */
    public static boolean shouldCloneSqlEntityQueryExpression(EntityQueryExpressionBuilder sqlEntityExpression) {
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
        return sqlEntityExpression.getTables().size() != 1 || !(sqlEntityExpression.getTables().get(0) instanceof AnonymousEntityTableExpressionBuilder);
    }
    public static boolean hasAnyOperate(EntityQueryExpressionBuilder sqlEntityExpression){
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasWhere() || sqlEntityExpression.hasOrder() || sqlEntityExpression.hasHaving()|| sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }
    public static boolean hasAnyOperateWithoutWhereAndOrder(EntityQueryExpressionBuilder sqlEntityExpression){
        return sqlEntityExpression.hasLimit() || sqlEntityExpression.hasHaving()|| sqlEntityExpression.isDistinct() || sqlEntityExpression.hasGroup();
    }

    public static <T1, T2> Queryable2<T1, T2> executeJoinOn(Queryable2<T1, T2> queryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        SqlWherePredicate<T1> sqlOnPredicate1 = queryable.getSqlBuilderProvider2().getSqlOnPredicate1();
        SqlWherePredicate<T2> sqlOnPredicate2 = queryable.getSqlBuilderProvider2().getSqlOnPredicate2();
        on.apply(sqlOnPredicate1, sqlOnPredicate2);
        return queryable;
    }

    public static <T1, T2, T3> Queryable3<T1, T2, T3> executeJoinOn(Queryable3<T1, T2, T3> queryable, SqlExpression3<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>> on) {
        SqlWherePredicate<T1> sqlOnPredicate1 = queryable.getSqlBuilderProvider3().getSqlOnPredicate1();
        SqlWherePredicate<T2> sqlOnPredicate2 = queryable.getSqlBuilderProvider3().getSqlOnPredicate2();
        SqlWherePredicate<T3> sqlOnPredicate3 = queryable.getSqlBuilderProvider3().getSqlOnPredicate3();
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3);
        return queryable;
    }
    public static <T1, T2, T3,T4> Queryable4<T1, T2, T3,T4> executeJoinOn(Queryable4<T1, T2, T3,T4> queryable, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        SqlWherePredicate<T1> sqlOnPredicate1 = queryable.getSqlBuilderProvider4().getSqlOnPredicate1();
        SqlWherePredicate<T2> sqlOnPredicate2 = queryable.getSqlBuilderProvider4().getSqlOnPredicate2();
        SqlWherePredicate<T3> sqlOnPredicate3 = queryable.getSqlBuilderProvider4().getSqlOnPredicate3();
        SqlWherePredicate<T4> sqlOnPredicate4 = queryable.getSqlBuilderProvider4().getSqlOnPredicate4();
        on.apply(sqlOnPredicate1, sqlOnPredicate2, sqlOnPredicate3,sqlOnPredicate4);
        return queryable;
    }


    public static EntityQueryExpressionBuilder getCountEntityQueryExpression(EntityQueryExpressionBuilder countSqlEntityExpression){
        if(countSqlEntityExpression.hasOrder()){
            countSqlEntityExpression.getOrder().clear();
        }
        if(SqlExpressionUtil.hasAnyOperateWithoutWhereAndOrder(countSqlEntityExpression)){
            return null;
        }

        //如果他只是匿名表那么就使用匿名表的内部表
        if(!SqlExpressionUtil.moreTableExpressionOrNoAnonymous(countSqlEntityExpression)){
            AnonymousEntityTableExpressionBuilder table = (AnonymousEntityTableExpressionBuilder) countSqlEntityExpression.getTable(0);
            EntityQueryExpressionBuilder entityQueryExpression = table.getEntityQueryExpressionBuilder().cloneEntityExpressionBuilder();
            //存在操作那么就返回父类
            if(!SqlExpressionUtil.hasAnyOperateWithoutWhereAndOrder(entityQueryExpression)){
                EntityQueryExpressionBuilder countEntityQueryExpression = getCountEntityQueryExpression(entityQueryExpression);
                if(countEntityQueryExpression!=null){
                    return countEntityQueryExpression;
                }
            }
        }
        countSqlEntityExpression.getProjects().getSqlSegments().clear();
        countSqlEntityExpression.getProjects().append(new SelectConstSegment(" COUNT(1) "));
        return countSqlEntityExpression;
    }


    /**
     * 返回当前sql执行策略默认也会有一个指定的
     * @param expressionContext
     * @return
     */
    public static SqlExecuteStrategyEnum getExecuteStrategy(ExpressionContext expressionContext){

        SqlExecuteStrategyEnum sqlStrategy = expressionContext.getSqlStrategy();
        //非默认的那么也不可以是全部
        if (SqlExecuteStrategyEnum.DEFAULT.equals(sqlStrategy)) {
            //如果是默认那么判断全局不是all columns即可
            EasyQueryOption easyQueryOption = expressionContext.getRuntimeContext().getEasyQueryConfiguration().getEasyQueryOption();
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
        SqlExecuteStrategyEnum executeStrategy = getExecuteStrategy(expressionContext);
        return SqlExecuteStrategyEnum.ALL_COLUMNS!=executeStrategy;
    }

    public static String getSqlOwnerColumn(EasyQueryRuntimeContext runtimeContext, TableAvailable table, String propertyName){
        String alias = table.getAlias();
        String columnName = table.getColumnName(propertyName);
        String quoteName = getQuoteName(runtimeContext,columnName);
        if (alias == null) {
            return quoteName;
        }
        return alias + "." + quoteName;
    }
    public static String getQuoteName(EasyQueryRuntimeContext runtimeContext, String value) {
        return runtimeContext.getEasyQueryConfiguration().getDialect().getQuoteName(value);
    }
}
