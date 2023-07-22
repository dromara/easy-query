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
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
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
public interface KtQueryable2<T1, T2> extends KtQueryable<T1> {
    ClientQueryable2<T1, T2> getClientQueryable2();

    <T3> KtQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> leftJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> rightJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    <T3> KtQueryable3<T1, T2, T3> innerJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on);

    //region where
    default KtQueryable2<T1, T2> whereById(Object id){
        return whereById(true,id);
    }
    KtQueryable2<T1, T2> whereById(boolean condition, Object id);

    @Override
    default KtQueryable2<T1, T2> whereObject(Object object) {
        return whereObject(true, object);
    }

    @Override
    KtQueryable2<T1, T2> whereObject(boolean condition, Object object);

    @Override
    default KtQueryable2<T1, T2> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    KtQueryable2<T1, T2> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    default KtQueryable2<T1, T2> where(SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression) {
        return where(true, whereExpression);
    }

    KtQueryable2<T1, T2> where(boolean condition, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression);

    //endregion

    //region select
    <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>> selectExpression);
    //endregion
    //region aggregate

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().sumBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().sumOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().maxOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().maxOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().minOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().minOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgFloatOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().avgBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable2().avgFloatOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def, resultClass);
    }
    //endregion

    //region group
    @Override
    default KtQueryable2<T1, T2> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    default KtQueryable2<T1, T2> groupBy(SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression);

    @Override
    default KtQueryable2<T1, T2> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    KtQueryable2<T1, T2> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);

    default KtQueryable2<T1, T2> having(SQLExpression2<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having((predicate1, predicate2) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2));
        });
        return this;
    }

    default KtQueryable2<T1, T2> having(boolean condition, SQLExpression2<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having(condition, (predicate1, predicate2) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2));
        });
        return this;
    }

    //endregion
    //region order
    @Override
    default KtQueryable2<T1, T2> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    default KtQueryable2<T1, T2> orderByAsc(SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression);

    @Override
    default KtQueryable2<T1, T2> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression);

    default KtQueryable2<T1, T2> orderByDesc(SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression);


    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    @Override
    default KtQueryable2<T1, T2> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    @Override
    KtQueryable2<T1, T2> orderByObject(boolean condition, ObjectSort configuration);
    //endregion


    //region include

    default  <TProperty> KtQueryable2<T1, T2> include(SQLFuncExpression2<SQLKtNavigateInclude<T1>,SQLKtNavigateInclude<T2>, KtQueryable<TProperty>> navigateIncludeSQLExpression){
        return include(true,navigateIncludeSQLExpression);
    }
    default  <TProperty> KtQueryable2<T1, T2> include(boolean condition,SQLFuncExpression2<SQLKtNavigateInclude<T1>,SQLKtNavigateInclude<T2>,KtQueryable<TProperty>> navigateIncludeSQLExpression){
        getClientQueryable2().include(condition,(include1,include2)->{
            return navigateIncludeSQLExpression.apply(new SQLKtNavigateIncludeImpl<>(include1),new SQLKtNavigateIncludeImpl<>(include2)).getClientQueryable();
        });
        return this;
    }

    //endregion

    //region limit

    @Override
    default KtQueryable2<T1, T2> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable2<T1, T2> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable2<T1, T2> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable2<T1, T2> limit(boolean condition, long offset, long rows);

    default KtQueryable2<T1, T2> distinct() {
        return distinct(true);
    }

    KtQueryable2<T1, T2> distinct(boolean condition);

    //endregion
    @Override
    KtQueryable2<T1, T2> disableLogicDelete();

    @Override
    KtQueryable2<T1, T2> enableLogicDelete();

    @Override
    KtQueryable2<T1, T2> useLogicDelete(boolean enable);

    @Override
    KtQueryable2<T1, T2> noInterceptor();

    @Override
    KtQueryable2<T1, T2> useInterceptor(String name);

    @Override
    KtQueryable2<T1, T2> noInterceptor(String name);

    @Override
    KtQueryable2<T1, T2> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    KtQueryable2<T1, T2> asTracking();

    @Override
    KtQueryable2<T1, T2> asNoTracking();

    @Override
    KtQueryable2<T1, T2> queryLargeColumn(boolean queryLarge);

    @Override
    KtQueryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    KtQueryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    KtQueryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default KtQueryable2<T1, T2> asTable(String tableName) {
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
    KtQueryable2<T1, T2> asTable(Function<String, String> tableNameAs);

    @Override
    default KtQueryable2<T1, T2> asSchema(String tableName) {
        return asSchema(old -> tableName);
    }

    @Override
    KtQueryable2<T1, T2> asSchema(Function<String, String> schemaAs);

    @Override
    KtQueryable2<T1, T2> asAlias(String alias);
}
