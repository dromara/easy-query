package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.ColumnSegment;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/6/1 17:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable<T1> extends Query<T1>,
        Interceptable<ClientQueryable<T1>>,
        LogicDeletable<ClientQueryable<T1>>,
        TableReNameable<ClientQueryable<T1>>,
        QueryStrategy<ClientQueryable<T1>> {
    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @Override
    ClientQueryable<T1> cloneQueryable();

    long countDistinct(SQLExpression1<ColumnSelector<T1>> selectExpression);


    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    boolean all(SQLExpression1<WherePredicate<T1>> whereExpression);

    /**
     * 防止溢出
     *
     * @param property
     * @return
     */
    default BigDecimal sumBigDecimalOrNull(String property) {
        return sumBigDecimalOrDefault(property, null);
    }

    /**
     * 防止溢出
     *
     * @param property
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(String property) {
        return sumBigDecimalOrDefault(property, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(String property, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(String property) {
        return sumOrDefault(property, null);
    }

    <TMember extends Number> TMember sumOrDefault(String property, TMember def);

    default <TMember extends Comparable<?>> TMember maxOrNull(String property) {
        return maxOrDefault(property, null);
    }

    <TMember extends Comparable<?>> TMember maxOrDefault(String property, TMember def);

    default <TMember> TMember minOrNull(String property) {
        return minOrDefault(property, null);
    }

    <TMember> TMember minOrDefault(String property, TMember def);

    default <TMember extends Number> TMember avgOrNull(String property) {
        return avgOrDefault(property, null);
    }

    <TMember extends Number> TMember avgOrDefault(String property, TMember def);

    default Integer lenOrNull(String property) {
        return lenOrDefault(property, null);
    }

    Integer lenOrDefault(String property, Integer def);


    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    ClientQueryable<T1> select(SQLExpression1<ColumnSelector<T1>> selectExpression);

    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    <TR> ClientQueryable<TR> select(Class<TR> resultClass);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     *
     * @param resultClass
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression1<ColumnAsSelector<T1, TR>> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    ClientQueryable<T1> select(String columns);

    default ClientQueryable<T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    ClientQueryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

    default ClientQueryable<T1> where(SQLExpression1<WherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable<T1> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression);

    default ClientQueryable<T1> whereById(Object id) {
        return whereById(true, id);
    }

    ClientQueryable<T1> whereById(boolean condition, Object id);

    default ClientQueryable<T1> whereByIds(Object... ids) {
        return whereByIds(true, ids);
    }

    ClientQueryable<T1> whereByIds(boolean condition, Object... ids);

    default <TProperty> ClientQueryable<T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ClientQueryable<T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default ClientQueryable<T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * @param condition
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取 {@link WherePredicate}
     */
    ClientQueryable<T1> whereObject(boolean condition, Object object);

    default ClientQueryable<T1> groupBy(SQLExpression1<GroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable<T1> groupBy(boolean condition, SQLExpression1<GroupBySelector<T1>> selectExpression);

    default ClientQueryable<T1> having(SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable<T1> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression);

    default ClientQueryable<T1> orderByAsc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ClientQueryable<T1> orderByAsc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default ClientQueryable<T1> orderByDesc(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ClientQueryable<T1> orderByDesc(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default ClientQueryable<T1> orderBy(SQLExpression1<ColumnSelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    ClientQueryable<T1> orderBy(boolean condition, SQLExpression1<ColumnSelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    default ClientQueryable<T1> orderByDynamic(ObjectSort configuration) {
        return orderByDynamic(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    ClientQueryable<T1> orderByDynamic(boolean condition, ObjectSort configuration);

    default ClientQueryable<T1> distinct() {
        return distinct(true);
    }


    ClientQueryable<T1> distinct(boolean condition);


    @Override
    default ClientQueryable<T1> limit(long rows) {
        return limit(true, rows);
    }


    @Override
    default ClientQueryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }


    @Override
    default ClientQueryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }


    @Override
    ClientQueryable<T1> limit(boolean condition, long offset, long rows);


    <T2> ClientQueryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> leftJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> rightJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    <T2> ClientQueryable2<T1, T2> innerJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on);

    default ClientQueryable<T1> union(ClientQueryable<T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default ClientQueryable<T1> union(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ClientQueryable<T1> union(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2, ClientQueryable<T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ClientQueryable<T1> union(Collection<ClientQueryable<T1>> unionQueries);

    default ClientQueryable<T1> unionAll(ClientQueryable<T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default ClientQueryable<T1> unionAll(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ClientQueryable<T1> unionAll(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2, ClientQueryable<T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ClientQueryable<T1> unionAll(Collection<ClientQueryable<T1>> unionQueries);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    ClientQueryable<T1> asTracking();

    ClientQueryable<T1> asNoTracking();

    ClientQueryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    ClientQueryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    ClientQueryable<T1> useConnectionMode(ConnectionModeEnum connectionMode);

    SQLExpressionProvider<T1> getSQLExpressionProvider1();

}
