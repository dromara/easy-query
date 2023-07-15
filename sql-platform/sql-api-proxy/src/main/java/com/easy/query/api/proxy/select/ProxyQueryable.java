package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/6/21 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends Query<T1>,
        Interceptable<ProxyQueryable<T1Proxy, T1>>,
        LogicDeletable<ProxyQueryable<T1Proxy, T1>>,
        TableReNameable<ProxyQueryable<T1Proxy, T1>>,
        QueryStrategy<ProxyQueryable<T1Proxy, T1>> {
    T1Proxy get1Proxy();


    ClientQueryable<T1> getEntityQueryable();

    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);

    @Override
    ProxyQueryable<T1Proxy, T1> cloneQueryable();

    long countDistinct(SQLExpression2<ProxySelector, T1Proxy> selectExpression);

    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    boolean all(SQLExpression2<ProxyFilter, T1Proxy> whereExpression);

    /**
     * 防止溢出
     *
     * @param columnSelector
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return sumBigDecimalOrDefault(columnSelector, null);
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, BigDecimal def) {
        return getEntityQueryable().sumBigDecimalOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().sumOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> TMember sumOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getEntityQueryable().sumOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().maxOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getEntityQueryable().maxOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember> TMember minOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().minOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember> TMember minOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getEntityQueryable().minOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().avgOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().avgBigDecimalOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().avgFloatOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, Double def) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default BigDecimal avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<BigDecimal>> columnSelector, BigDecimal def) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default Float avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<Float>> columnSelector, Float def) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TResult def, Class<TResult> resultClass) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def, resultClass);
    }


    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    ProxyQueryable<T1Proxy, T1> select(SQLExpression2<ProxySelector, T1Proxy> selectExpression);

    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     *
     * @param trProxy
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(ProxyEntity<TRProxy, TR> trProxy);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * 多次select会在前一次基础上进行对上次结果进行匿名表处理
     *
     * @param trProxy
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression2<ProxyAsSelector<TRProxy, TR>, T1Proxy> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    ProxyQueryable<T1Proxy, T1> select(String columns);

    default ProxyQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    ProxyQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

    default ProxyQueryable<T1Proxy, T1> where(SQLExpression2<ProxyFilter, T1Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    ProxyQueryable<T1Proxy, T1> where(boolean condition, SQLExpression2<ProxyFilter, T1Proxy> whereExpression);
//    default ProxyQueryable<T1Proxy, T1> where1(SQLFuncExpression1<T1Proxy, SQLPredicate> whereExpression){
//        return this;
//    }

    /**
     * 根据id主键查询
     *
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default ProxyQueryable<T1Proxy, T1> whereById(Object id) {
        return whereById(true, id);
    }

    /**
     * @param condition
     * @param id
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    ProxyQueryable<T1Proxy, T1> whereById(boolean condition, Object id);

    /**
     * @param ids
     * @param <TProperty>
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> ProxyQueryable<T1Proxy, T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    /**
     * 根据主键id集合查询
     *
     * @param condition   是否添加该条件
     * @param ids         主键集合
     * @param <TProperty> 主键类型
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */
    <TProperty> ProxyQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default ProxyQueryable<T1Proxy, T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * @param condition
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    ProxyQueryable<T1Proxy, T1> whereObject(boolean condition, Object object);

    default ProxyQueryable<T1Proxy, T1> groupBy(SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ProxyQueryable<T1Proxy, T1> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression);

    default ProxyQueryable<T1Proxy, T1> having(SQLExpression2<ProxyAggregateFilter, T1Proxy> aggregateFilterSQLExpression) {
        return having(true, aggregateFilterSQLExpression);
    }

    ProxyQueryable<T1Proxy, T1> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> aggregateFilterSQLExpression);

    default ProxyQueryable<T1Proxy, T1> orderByAsc(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable<T1Proxy, T1> orderByAsc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default ProxyQueryable<T1Proxy, T1> orderByDesc(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable<T1Proxy, T1> orderByDesc(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default ProxyQueryable<T1Proxy, T1> orderBy(SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    ProxyQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression2<ProxyOrderSelector, T1Proxy> selectExpression, boolean asc);

    // region single order
    default ProxyQueryable<T1Proxy, T1> orderByAsc(SQLFuncExpression1<T1Proxy, SQLColumn<?>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable<T1Proxy, T1> orderByAsc(boolean condition, SQLFuncExpression1<T1Proxy, SQLColumn<?>> selectExpression) {
        return orderBy(true, selectExpression, true);
    }
    default ProxyQueryable<T1Proxy, T1> orderByDesc(SQLFuncExpression1<T1Proxy, SQLColumn<?>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable<T1Proxy, T1> orderByDesc(boolean condition, SQLFuncExpression1<T1Proxy, SQLColumn<?>> selectExpression) {
        return orderBy(true, selectExpression, false);
    }

    ProxyQueryable<T1Proxy, T1> orderBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLColumn<?>> selectExpression, boolean asc);
// endregion

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    default ProxyQueryable<T1Proxy, T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param objectSort
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取
     */
    ProxyQueryable<T1Proxy, T1> orderByObject(boolean condition, ObjectSort objectSort);


    default ProxyQueryable<T1Proxy, T1> distinct() {
        return distinct(true);
    }

    ProxyQueryable<T1Proxy, T1> distinct(boolean condition);

    @Override
    default ProxyQueryable<T1Proxy, T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable<T1Proxy, T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable<T1Proxy, T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    default ProxyQueryable<T1Proxy, T1> union(ProxyQueryable<T1Proxy, T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default ProxyQueryable<T1Proxy, T1> union(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ProxyQueryable<T1Proxy, T1> union(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2, ProxyQueryable<T1Proxy, T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ProxyQueryable<T1Proxy, T1> union(Collection<ProxyQueryable<T1Proxy, T1>> unionQueries);

    default ProxyQueryable<T1Proxy, T1> unionAll(ProxyQueryable<T1Proxy, T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default ProxyQueryable<T1Proxy, T1> unionAll(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ProxyQueryable<T1Proxy, T1> unionAll(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2, ProxyQueryable<T1Proxy, T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ProxyQueryable<T1Proxy, T1> unionAll(Collection<ProxyQueryable<T1Proxy, T1>> unionQueries);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    ProxyQueryable<T1Proxy, T1> asTracking();

    ProxyQueryable<T1Proxy, T1> asNoTracking();

    ProxyQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    ProxyQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    ProxyQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode);
}
