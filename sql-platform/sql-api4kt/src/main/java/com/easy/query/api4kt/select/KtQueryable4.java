package com.easy.query.api4kt.select;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtNavigateIncludeImpl;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface KtQueryable4<T1, T2, T3, T4> extends KtQueryable<T1> {
    ClientQueryable4<T1, T2, T3, T4> getClientQueryable4();

    //region where
    default KtQueryable4<T1, T2, T3, T4> whereById(Object id){
        return whereById(true,id);
    }
    KtQueryable4<T1, T2, T3, T4> whereById(boolean condition, Object id);
    @Override
    default KtQueryable4<T1, T2, T3, T4> whereObject(Object object) {
        return whereObject(true, object);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object);

    @Override
    default KtQueryable4<T1, T2, T3, T4> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    default KtQueryable4<T1, T2, T3, T4> where(SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> whereExpression) {
        return where(true, whereExpression);
    }

    KtQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> whereExpression);

    //endregion
    //region select
    <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().sumBigDecimalOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().sumOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().sumOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().maxOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().maxOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().minOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().minOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgBigDecimalOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgFloatOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable4().avgOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable4().avgFloatOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable4().avgOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def, resultClass);
    }
    //endregion


    //region group
    @Override
    default KtQueryable4<T1, T2, T3, T4> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    default KtQueryable4<T1, T2, T3, T4> groupBy(SQLExpression4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    KtQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>> selectExpression);

    @Override
    default KtQueryable4<T1, T2, T3, T4> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);

    default KtQueryable4<T1, T2, T3, T4> having(SQLExpression4<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having((predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4));
        });
        return this;
    }

    default KtQueryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression4<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having(condition, (predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4));
        });
        return this;
    }

    //endregion
    //region order
    @Override
    default KtQueryable4<T1, T2, T3, T4> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    default KtQueryable4<T1, T2, T3, T4> orderByAsc(SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    KtQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression);

    @Override
    default KtQueryable4<T1, T2, T3, T4> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    default KtQueryable4<T1, T2, T3, T4> orderByDesc(SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    KtQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression);



    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    @Override
    default KtQueryable4<T1, T2, T3, T4> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    @Override
    KtQueryable4<T1, T2, T3, T4> orderByObject(boolean condition, ObjectSort configuration);
    //endregion


    //region include

    @Override
    default <TProperty> KtQueryable4<T1, T2, T3, T4> include(SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    default <TProperty> KtQueryable4<T1, T2, T3, T4> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TProperty>> navigateIncludeSQLExpression) {
        getClientQueryable4().include(condition, (include1) -> {
            return navigateIncludeSQLExpression.apply(new SQLKtNavigateIncludeImpl<>(include1)).getClientQueryable();
        });
        return this;
    }

    //endregion

    //region limit

    @Override
    default KtQueryable4<T1, T2, T3, T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable4<T1, T2, T3, T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable4<T1, T2, T3, T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows);

    default KtQueryable4<T1, T2, T3, T4> distinct() {
        return distinct(true);
    }

    KtQueryable4<T1, T2, T3, T4> distinct(boolean condition);
    //endregion

    @Override
    KtQueryable4<T1, T2, T3, T4> disableLogicDelete();

    @Override
    KtQueryable4<T1, T2, T3, T4> enableLogicDelete();

    @Override
    KtQueryable4<T1, T2, T3, T4> useLogicDelete(boolean enable);

    @Override
    KtQueryable4<T1, T2, T3, T4> noInterceptor();

    @Override
    KtQueryable4<T1, T2, T3, T4> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    KtQueryable4<T1, T2, T3, T4> asTracking();

    @Override
    KtQueryable4<T1, T2, T3, T4> asNoTracking();

    @Override
    KtQueryable4<T1, T2, T3, T4> queryLargeColumn(boolean queryLarge);

    @Override
    KtQueryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    KtQueryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    KtQueryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default KtQueryable4<T1, T2, T3, T4> asTable(String tableName) {
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
    KtQueryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs);


    @Override
    default KtQueryable4<T1, T2, T3, T4> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    KtQueryable4<T1, T2, T3, T4> asSchema(Function<String, String> schemaAs);

    @Override
    KtQueryable4<T1, T2, T3, T4> asAlias(String alias);
}
