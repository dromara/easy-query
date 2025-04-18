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
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 */
public interface KtQueryable3<T1, T2, T3> extends KtQueryable<T1> {
    ClientQueryable3<T1, T2, T3> getClientQueryable3();

    <T4> KtQueryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on);

    <T4> KtQueryable4<T1, T2, T3, T4> leftJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on);

    <T4> KtQueryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on);

    <T4> KtQueryable4<T1, T2, T3, T4> rightJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on);

    <T4> KtQueryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on);

    <T4> KtQueryable4<T1, T2, T3, T4> innerJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on);

    //region where
    default KtQueryable3<T1, T2, T3> whereById(Object id){
        return whereById(true,id);
    }
    KtQueryable3<T1, T2, T3> whereById(boolean condition, Object id);
    @Override
    default KtQueryable3<T1, T2, T3> whereObject(Object object) {
        return whereObject(true, object);
    }

    @Override
    KtQueryable3<T1, T2, T3> whereObject(boolean condition, Object object);

    @Override
    default KtQueryable3<T1, T2, T3> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    KtQueryable3<T1, T2, T3> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    default KtQueryable3<T1, T2, T3> where(SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> whereExpression) {
        return where(true, whereExpression);
    }

    KtQueryable3<T1, T2, T3> where(boolean condition, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> whereExpression);

    //endregion

    //region select
    <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>> selectExpression);
    //endregion

    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().sumBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().sumOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().maxOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().maxOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().minOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().minOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgFloatOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().avgBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable3().avgFloatOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def, resultClass);
    }
    //endregion

    //region group
    @Override
    default KtQueryable3<T1, T2, T3> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    KtQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    default KtQueryable3<T1, T2, T3> groupBy(SQLExpression3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    KtQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>> selectExpression);


    @Override
    default KtQueryable3<T1, T2, T3> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    KtQueryable3<T1, T2, T3> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);

    default KtQueryable3<T1, T2, T3> having(SQLExpression3<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3));
        });
        return this;
    }

    default KtQueryable3<T1, T2, T3> having(boolean condition, SQLExpression3<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having(condition, (predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3));
        });
        return this;
    }

    //endregion
    //region order
    @Override
    default KtQueryable3<T1, T2, T3> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    KtQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    default KtQueryable3<T1, T2, T3> orderByAsc(SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    KtQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression);

    @Override
    default KtQueryable3<T1, T2, T3> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    KtQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    default KtQueryable3<T1, T2, T3> orderByDesc(SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    KtQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression);


    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    @Override
    default KtQueryable3<T1, T2, T3> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    @Override
    KtQueryable3<T1, T2, T3> orderByObject(boolean condition, ObjectSort configuration);
    //endregion


    //region include

    @Override
    default <TProperty> KtQueryable3<T1, T2, T3> include(SQLFuncExpression1<SQLKtNavigateInclude<T1>,  KtQueryable<TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    default <TProperty> KtQueryable3<T1, T2, T3> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>,  KtQueryable<TProperty>> navigateIncludeSQLExpression) {
        getClientQueryable3().include(condition, (include1) -> {
            return navigateIncludeSQLExpression.apply(new SQLKtNavigateIncludeImpl<>(include1)).getClientQueryable();
        });
        return this;
    }

    //endregion
    //region limit

    @Override
    default KtQueryable3<T1, T2, T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable3<T1, T2, T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable3<T1, T2, T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable3<T1, T2, T3> limit(boolean condition, long offset, long rows);

    default KtQueryable3<T1, T2, T3> distinct() {
        return distinct(true);
    }

    KtQueryable3<T1, T2, T3> distinct(boolean condition);
    //endregion

    @Override
    KtQueryable3<T1, T2, T3> disableLogicDelete();

    @Override
    KtQueryable3<T1, T2, T3> enableLogicDelete();

    @Override
    KtQueryable3<T1, T2, T3> useLogicDelete(boolean enable);

    @Override
    KtQueryable3<T1, T2, T3> noInterceptor();

    @Override
    KtQueryable3<T1, T2, T3> useInterceptor(String name);

    @Override
    KtQueryable3<T1, T2, T3> noInterceptor(String name);

    @Override
    KtQueryable3<T1, T2, T3> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    KtQueryable3<T1, T2, T3> asTracking();

    @Override
    KtQueryable3<T1, T2, T3> asNoTracking();

    @Override
    KtQueryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge);

    @Override
    KtQueryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    KtQueryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    KtQueryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default KtQueryable3<T1, T2, T3> asTable(String tableName) {
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
    KtQueryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs);


    @Override
    default KtQueryable3<T1, T2, T3> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    KtQueryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs);

    @Override
    KtQueryable3<T1, T2, T3> asAlias(String alias);
    @Override
    default KtQueryable3<T1, T2, T3> asTableLink(String linkAs) {
        return asTableLink(o->linkAs);
    }

    @Override
    KtQueryable3<T1, T2, T3> asTableLink(Function<String, String> linkAs);
    @Override
    KtQueryable3<T1, T2, T3> asTableSegment(BiFunction<String, String, String> segmentAs);
}
