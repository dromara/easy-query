package com.easy.query.api4j.select;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.api4j.sql.impl.SQLNavigateIncludeImpl;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 */
public interface Queryable2<T1, T2> extends Queryable<T1> {
    ClientQueryable2<T1, T2> getClientQueryable2();

    <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on);

    //region where
    default Queryable2<T1, T2> whereById(Object id){
        return whereById(true,id);
    }
    Queryable2<T1, T2> whereById(boolean condition, Object id);

    default Queryable2<T1, T2> whereObject(Object object) {
        return whereObject(true, object);
    }

    Queryable2<T1, T2> whereObject(boolean condition, Object object);

    @Override
    default Queryable2<T1, T2> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable2<T1, T2> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    default Queryable2<T1, T2> where(SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression) {
        getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return this;
    }

    default Queryable2<T1, T2> where(boolean condition, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression) {
        getClientQueryable2().where(condition, (wherePredicate1, wherePredicate2) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2));
        });
        return this;
    }

    //endregion

    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().sumBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().sumOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().maxOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().maxOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().minOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().minOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgFloatOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().avgBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable2().avgFloatOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def, resultClass);
    }

    //endregion

    //region group
    @Override
    default Queryable2<T1, T2> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    Queryable2<T1, T2> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);

    default Queryable2<T1, T2> groupBy(SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression) {
        getClientQueryable2().groupBy((selector1, selector2) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2));
        });
        return this;
    }

    default Queryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression) {
        getClientQueryable2().groupBy(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2));
        });
        return this;
    }

    @Override
    default Queryable2<T1, T2> having(SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    Queryable2<T1, T2> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression);

    default Queryable2<T1, T2> having(SQLExpression2<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having((predicate1, predicate2) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2));
        });
        return this;
    }

    default Queryable2<T1, T2> having(boolean condition, SQLExpression2<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having(condition, (predicate1, predicate2) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2));
        });
        return this;
    }

    //endregion
    //region order
    @Override
    default Queryable2<T1, T2> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    default Queryable2<T1, T2> orderByAsc(SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByAsc((selector1, selector2) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
        });
        return this;
    }

    default Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByAsc(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
        });
        return this;
    }

    @Override
    default Queryable2<T1, T2> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    default Queryable2<T1, T2> orderByDesc(SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByDesc((selector1, selector2) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
        });
        return this;
    }

    default Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByDesc(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
        });
        return this;
    }
    //endregion


    //region include

    default  <TProperty> Queryable2<T1, T2> include(SQLFuncExpression2<SQLNavigateInclude<T1>,SQLNavigateInclude<T2>, Queryable<TProperty>> navigateIncludeSQLExpression){
        return include(true,navigateIncludeSQLExpression);
    }
   default  <TProperty> Queryable2<T1, T2> include(boolean condition,SQLFuncExpression2<SQLNavigateInclude<T1>,SQLNavigateInclude<T2>,Queryable<TProperty>> navigateIncludeSQLExpression){
        getClientQueryable2().include(condition,(include1,include2)->{
            return navigateIncludeSQLExpression.apply(new SQLNavigateIncludeImpl<>(include1),new SQLNavigateIncludeImpl<>(include2)).getClientQueryable();
        });
        return this;
   }

    //endregion

    //region limit

    @Override
    default Queryable2<T1, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable2<T1, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable2<T1, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable2<T1, T2> limit(boolean condition, long offset, long rows);

    default Queryable2<T1, T2> distinct() {
        return distinct(true);
    }

    Queryable2<T1, T2> distinct(boolean condition);

    //endregion
    @Override
    Queryable2<T1, T2> disableLogicDelete();

    @Override
    Queryable2<T1, T2> enableLogicDelete();

    @Override
    Queryable2<T1, T2> useLogicDelete(boolean enable);

    @Override
    Queryable2<T1, T2> noInterceptor();

    @Override
    Queryable2<T1, T2> useInterceptor(String name);

    @Override
    Queryable2<T1, T2> noInterceptor(String name);

    @Override
    Queryable2<T1, T2> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    Queryable2<T1, T2> asTracking();

    @Override
    Queryable2<T1, T2> asNoTracking();

    @Override
    Queryable2<T1, T2> queryLargeColumn(boolean queryLarge);

    @Override
    Queryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    Queryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    Queryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default Queryable2<T1, T2> asTable(String tableName) {
        return asTable(old -> tableName);
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableNameAs
     * @return
     */
    @Override
    Queryable2<T1, T2> asTable(Function<String, String> tableNameAs);

    @Override
    default Queryable2<T1, T2> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    Queryable2<T1, T2> asSchema(Function<String, String> schemaAs);

    @Override
    Queryable2<T1, T2> asAlias(String alias);
}
