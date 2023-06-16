package com.easy.query.api4j.select;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
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
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.util.EasyLambdaUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author xuejiaming
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 */
public interface Queryable<T1> extends Query<T1>,
        Interceptable<Queryable<T1>>,
        LogicDeletable<Queryable<T1>>,
        TableReNameable<Queryable<T1>>,
        QueryStrategy<Queryable<T1>> {
    ClientQueryable<T1> getEntityQueryable();

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @Override
    Queryable<T1> cloneQueryable();

    long countDistinct(SQLExpression1<SQLColumnSelector<T1>> selectExpression);



    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    boolean all(SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().sumBigDecimalOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def) {
        return getEntityQueryable().sumBigDecimalOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> TMember sumOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().sumOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def) {
        return getEntityQueryable().sumOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().maxOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(Property<T1, TMember> column, TMember def) {
        return getEntityQueryable().maxOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember> TMember minOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().minOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def) {
        return getEntityQueryable().minOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> Double avgOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().avgOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().avgBigDecimalOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Float avgFloatOrNull(Property<T1, TMember> column) {
        return getEntityQueryable().avgFloatOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Double avgOrDefault(Property<T1, TMember> column, Double def) {
        return getEntityQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default BigDecimal avgOrDefault(Property<T1, BigDecimal> column, BigDecimal def) {
        return getEntityQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default Float avgOrDefault(Property<T1, Float> column, Float def) {
        return getEntityQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(Property<T1, TMember> column, TResult def, Class<TResult> resultClass) {
        return getEntityQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def, resultClass);
    }

    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    Queryable<T1> select(SQLExpression1<SQLColumnSelector<T1>> selectExpression);

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
    <TR> Queryable<TR> select(Class<TR> resultClass);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * 多次select会在前一次基础上进行对上次结果进行匿名表处理
     *
     * @param resultClass
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression1<SQLColumnAsSelector<T1, TR>> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    Queryable<T1> select(String columns);

   default Queryable<T1> select(ColumnSegment columnSegment, boolean clearAll){
       return select(Collections.singletonList(columnSegment), clearAll);
   }

    Queryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

    default Queryable<T1> where(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable<T1> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression);

    /**
     * 根据id主键查询
     *
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default Queryable<T1> whereById(Object id) {
        return whereById(true, id);
    }

    /**
     * @param condition
     * @param id
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    Queryable<T1> whereById(boolean condition, Object id);

    /**
     * @param ids
     * @param <TProperty>
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> Queryable<T1> whereByIds(Collection<TProperty> ids) {
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
    <TProperty> Queryable<T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default Queryable<T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * @param condition
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取 {@link SQLWherePredicate}
     */
    Queryable<T1> whereObject(boolean condition, Object object);

    default Queryable<T1> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable<T1> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);

    default Queryable<T1> having(SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    Queryable<T1> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression);

    default Queryable<T1> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default Queryable<T1> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default Queryable<T1> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable<T1> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default Queryable<T1> orderBy(SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    Queryable<T1> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    default Queryable<T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    Queryable<T1> orderByObject(boolean condition, ObjectSort configuration);

    default Queryable<T1> distinct() {
        return distinct(true);
    }

    Queryable<T1> distinct(boolean condition);

    @Override
    default Queryable<T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable<T1> limit(boolean condition, long offset, long rows);
    <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on);

    default Queryable<T1> union(Queryable<T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default Queryable<T1> union(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default Queryable<T1> union(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2, Queryable<T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    Queryable<T1> union(Collection<Queryable<T1>> unionQueries);

    default Queryable<T1> unionAll(Queryable<T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default Queryable<T1> unionAll(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default Queryable<T1> unionAll(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2, Queryable<T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    Queryable<T1> unionAll(Collection<Queryable<T1>> unionQueries);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    Queryable<T1> asTracking();

    Queryable<T1> asNoTracking();

    Queryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    Queryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    Queryable<T1> useConnectionMode(ConnectionModeEnum connectionMode);

//    SQLExpressionProvider<T1> getSQLExpressionProvider1();

}
