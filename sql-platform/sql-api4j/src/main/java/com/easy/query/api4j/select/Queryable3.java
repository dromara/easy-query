package com.easy.query.api4j.select;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 */
public interface Queryable3<T1, T2, T3> extends Queryable<T1> {

    ClientQueryable3<T1, T2, T3> getClientQueryable3();

    <T4> Queryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3, T4> leftJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3, T4> rightJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    <T4> Queryable4<T1, T2, T3, T4> innerJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on);

    //region where
    default Queryable3<T1, T2, T3> whereById(Object id) {
        return whereById(true, id);
    }

    Queryable3<T1, T2, T3> whereById(boolean condition, Object id);

    @Override
    default Queryable3<T1, T2, T3> whereObject(Object object) {
        return whereObject(true, object);
    }

    @Override
    Queryable3<T1, T2, T3> whereObject(boolean condition, Object object);

    @Override
    default Queryable3<T1, T2, T3> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    Queryable3<T1, T2, T3> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    default Queryable3<T1, T2, T3> where(SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable3<T1, T2, T3> where(boolean condition, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression);

    //endregion

    //region select
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().sumBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().sumOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().maxOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().maxOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().minOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().minOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgFloatOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().avgBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable3().avgFloatOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def, resultClass);
    }
    //endregion

    //region group
    @Override
    default Queryable3<T1, T2, T3> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);

    default Queryable3<T1, T2, T3> groupBy(SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression);


    @Override
    default Queryable3<T1, T2, T3> having(SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    Queryable3<T1, T2, T3> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression);

    default Queryable3<T1, T2, T3> having(SQLExpression3<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3));
        });
        return this;
    }

    default Queryable3<T1, T2, T3> having(boolean condition, SQLExpression3<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having(condition, (predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3));
        });
        return this;
    }

    //endregion
    //region order
    @Override
    default Queryable3<T1, T2, T3> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    default Queryable3<T1, T2, T3> orderByAsc(SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression);

    @Override
    default Queryable3<T1, T2, T3> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression);

    default Queryable3<T1, T2, T3> orderByDesc(SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression);



    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    @Override
    default Queryable3<T1, T2, T3> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    @Override
    Queryable3<T1, T2, T3> orderByObject(boolean condition, ObjectSort configuration);
    //endregion


    //region include

    @Override
    default <TProperty> Queryable3<T1, T2, T3> include(SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TProperty> Queryable3<T1, T2, T3> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TProperty>> navigateIncludeSQLExpression);

    //endregion

    //region limit

    @Override
    default Queryable3<T1, T2, T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable3<T1, T2, T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable3<T1, T2, T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable3<T1, T2, T3> limit(boolean condition, long offset, long rows);

    default Queryable3<T1, T2, T3> distinct() {
        return distinct(true);
    }

    Queryable3<T1, T2, T3> distinct(boolean condition);
    //endregion

    @Override
    Queryable3<T1, T2, T3> disableLogicDelete();

    @Override
    Queryable3<T1, T2, T3> enableLogicDelete();

    @Override
    Queryable3<T1, T2, T3> useLogicDelete(boolean enable);

    @Override
    Queryable3<T1, T2, T3> noInterceptor();

    @Override
    Queryable3<T1, T2, T3> useInterceptor(String name);

    @Override
    Queryable3<T1, T2, T3> noInterceptor(String name);

    @Override
    Queryable3<T1, T2, T3> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    Queryable3<T1, T2, T3> asTracking();

    @Override
    Queryable3<T1, T2, T3> asNoTracking();

    @Override
    Queryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge);

    @Override
    Queryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    Queryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    Queryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default Queryable3<T1, T2, T3> asTable(String tableName) {
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
    Queryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs);

    @Override
    default Queryable3<T1, T2, T3> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    Queryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs);

    @Override
    Queryable3<T1, T2, T3> asAlias(String alias);
    @Override
    default Queryable3<T1, T2, T3> asTableLink(String linkAs) {
        return asTableLink(o->linkAs);
    }

    @Override
    Queryable3<T1, T2, T3> asTableLink(Function<String, String> linkAs);
}
