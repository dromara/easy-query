package com.easy.query.api4j.select;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable4<T1, T2, T3, T4> extends Queryable<T1> {
    ClientQueryable4<T1, T2, T3, T4> getClientQueryable4();

    //region where
    default Queryable4<T1, T2, T3, T4> whereById(Object id){
        return whereById(true,id);
    }
    Queryable4<T1, T2, T3, T4> whereById(boolean condition, Object id);
    default Queryable4<T1, T2, T3, T4> whereObject(Object object) {
        return whereObject(true, object);
    }

    Queryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object);

    @Override
    default Queryable4<T1, T2, T3, T4> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    default Queryable4<T1, T2, T3, T4> where(SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression);

    //endregion
    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().sumBigDecimalOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().sumOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().sumOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().maxOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().maxOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().minOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().minOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgBigDecimalOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgFloatOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable4().avgOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable4().avgFloatOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable4().avgOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4));
        }, def, resultClass);
    }
    //endregion


    //region group
    @Override
    default Queryable4<T1, T2, T3, T4> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    Queryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);

    default Queryable4<T1, T2, T3, T4> groupBy(SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression);


    @Override
    default Queryable4<T1, T2, T3, T4> having(SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    Queryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression);

    default Queryable4<T1, T2, T3, T4> having(SQLExpression4<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having((predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4));
        });
        return this;
    }

    default Queryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression4<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having(condition, (predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4));
        });
        return this;
    }

    //endregion
    //region order
    @Override
    default Queryable4<T1, T2, T3, T4> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    Queryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    default Queryable4<T1, T2, T3, T4> orderByAsc(SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression);

    @Override
    default Queryable4<T1, T2, T3, T4> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    Queryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    default Queryable4<T1, T2, T3, T4> orderByDesc(SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression);
    //endregion
    //region limit

    @Override
    default Queryable4<T1, T2, T3, T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable4<T1, T2, T3, T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable4<T1, T2, T3, T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows);

    default Queryable4<T1, T2, T3, T4> distinct() {
        return distinct(true);
    }

    Queryable4<T1, T2, T3, T4> distinct(boolean condition);
    //endregion

    @Override
    Queryable4<T1, T2, T3, T4> disableLogicDelete();

    @Override
    Queryable4<T1, T2, T3, T4> enableLogicDelete();

    @Override
    Queryable4<T1, T2, T3, T4> useLogicDelete(boolean enable);

    @Override
    Queryable4<T1, T2, T3, T4> noInterceptor();

    @Override
    Queryable4<T1, T2, T3, T4> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    Queryable4<T1, T2, T3, T4> asTracking();

    @Override
    Queryable4<T1, T2, T3, T4> asNoTracking();

    @Override
    Queryable4<T1, T2, T3, T4> queryLargeColumn(boolean queryLarge);

    @Override
    Queryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    Queryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    Queryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default Queryable4<T1, T2, T3, T4> asTable(String tableName) {
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
    Queryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs);

    @Override
    default Queryable4<T1, T2, T3, T4> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    Queryable4<T1, T2, T3, T4> asSchema(Function<String, String> schemaAs);

    @Override
    Queryable4<T1, T2, T3, T4> asAlias(String alias);
}
