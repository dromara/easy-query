package com.easy.query.api4kt.select;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import kotlin.reflect.KProperty1;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 */
public interface KtQueryable<T1> extends Query<T1>,
        Interceptable<KtQueryable<T1>>,
        LogicDeletable<KtQueryable<T1>>,
        TableReNameable<KtQueryable<T1>>,
        QueryStrategy<KtQueryable<T1>> {
    ClientQueryable<T1> getEntityQueryable();

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @Override
    KtQueryable<T1> cloneQueryable();
    <TR> List<TR> toList(Class<TR> resultClass);

    long countDistinct(SQLExpression1<SQLKtColumnSelector<T1>> selectExpression);


    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    boolean all(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().sumBigDecimalOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(KProperty1<T1, TMember> column, BigDecimal def) {
        return getEntityQueryable().sumBigDecimalOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> TMember sumOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().sumOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> TMember sumOrDefault(KProperty1<T1, TMember> column, TMember def) {
        return getEntityQueryable().sumOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().maxOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(KProperty1<T1, TMember> column, TMember def) {
        return getEntityQueryable().maxOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember> TMember minOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().minOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember> TMember minOrDefault(KProperty1<T1, TMember> column, TMember def) {
        return getEntityQueryable().minOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> Double avgOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().avgOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().avgBigDecimalOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Float avgFloatOrNull(KProperty1<T1, TMember> column) {
        return getEntityQueryable().avgFloatOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Double avgOrDefault(KProperty1<T1, TMember> column, Double def) {
        return getEntityQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default BigDecimal avgOrDefault(KProperty1<T1, BigDecimal> column, BigDecimal def) {
        return getEntityQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default Float avgOrDefault(KProperty1<T1, Float> column, Float def) {
        return getEntityQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(KProperty1<T1, TMember> column, TResult def, Class<TResult> resultClass) {
        return getEntityQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def, resultClass);
    }

    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    KtQueryable<T1> select(SQLExpression1<SQLKtColumnSelector<T1>> selectExpression);

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
    <TR> KtQueryable<TR> select(Class<TR> resultClass);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     *
     * @param resultClass
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression1<SQLKtColumnAsSelector<T1, TR>> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    KtQueryable<T1> select(String columns);

    default KtQueryable<T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    KtQueryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

    default KtQueryable<T1> where(SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    KtQueryable<T1> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression);

    default KtQueryable<T1> whereById(Object id) {
        return whereById(true, id);
    }

    KtQueryable<T1> whereById(boolean condition, Object id);

    default <TProperty> KtQueryable<T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> KtQueryable<T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default KtQueryable<T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * @param condition
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取 {@link SQLKtWherePredicate}
     */
    KtQueryable<T1> whereObject(boolean condition, Object object);

    default KtQueryable<T1> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    KtQueryable<T1> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);

    default KtQueryable<T1> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    KtQueryable<T1> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);

    default KtQueryable<T1> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default KtQueryable<T1> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default KtQueryable<T1> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable<T1> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default KtQueryable<T1> orderBy(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    KtQueryable<T1> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    default KtQueryable<T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    KtQueryable<T1> orderByObject(boolean condition, ObjectSort configuration);

    default KtQueryable<T1> distinct() {
        return distinct(true);
    }

    KtQueryable<T1> distinct(boolean condition);

    @Override
    default KtQueryable<T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable<T1> limit(boolean condition, long offset, long rows);

    <T2> KtQueryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> leftJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> rightJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    <T2> KtQueryable2<T1, T2> innerJoin(KtQueryable<T2> joinQueryable, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> on);

    default KtQueryable<T1> union(KtQueryable<T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default KtQueryable<T1> union(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default KtQueryable<T1> union(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2, KtQueryable<T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    KtQueryable<T1> union(Collection<KtQueryable<T1>> unionQueries);

    default KtQueryable<T1> unionAll(KtQueryable<T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default KtQueryable<T1> unionAll(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default KtQueryable<T1> unionAll(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2, KtQueryable<T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    KtQueryable<T1> unionAll(Collection<KtQueryable<T1>> unionQueries);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    KtQueryable<T1> asTracking();

    KtQueryable<T1> asNoTracking();

    KtQueryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    KtQueryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    KtQueryable<T1> useConnectionMode(ConnectionModeEnum connectionMode);

//    SQLExpressionProvider<T1> getSQLExpressionProvider1();

}
