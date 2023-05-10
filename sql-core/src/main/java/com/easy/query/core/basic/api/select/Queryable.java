package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.dynamic.order.EasyOrderBy;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.SqlExpression2;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xuejiaming
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 */
public interface Queryable<T1> extends Query<T1>, Interceptable<Queryable<T1>>, LogicDeletable<Queryable<T1>>, TableReNameable<Queryable<T1>> {
    /**
     * 只clone表达式共享上下文
     *
     * @return
     */
    Queryable<T1> cloneQueryable();

    long count();

    default int intCount() {
        return (int) count();
    }

    long countDistinct(SqlExpression<SqlColumnSelector<T1>> selectExpression);

    /**
     * 判断是否存在
     * eg. SELECT  1  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return 如果有行数那么就就是返回true表示存在，否则返回false表示不存在
     */
    boolean any();


    /**
     *
     *  SELECT NOT EXISTS (
     *           SELECT 1
     *           FROM `table` AS `t`
     *           WHERE (`t`.`columns` = ?))
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    boolean all(SqlExpression<SqlWherePredicate<T1>> whereExpression);
    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(Property<T1, TMember> column) {
        return sumBigDecimalOrDefault(column, null);
    }

    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalNotNull(Property<T1, TMember> column) {
        return sumBigDecimalOrDefault(column, BigDecimal.ZERO);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(Property<T1, TMember> column) {
        return sumOrDefault(column, null);
    }

    <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def);

    default <TMember extends Comparable<?>> TMember maxOrNull(Property<T1, TMember> column) {
        return maxOrDefault(column, null);
    }

    <TMember extends Comparable<?>> TMember maxOrDefault(Property<T1, TMember> column, TMember def);

    default <TMember> TMember minOrNull(Property<T1, TMember> column) {
        return minOrDefault(column, null);
    }

    <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def);

    default <TMember extends Number> TMember avgOrNull(Property<T1, TMember> column) {
        return avgOrDefault(column, null);
    }

    <TMember extends Number> TMember avgOrDefault(Property<T1, TMember> column, TMember def);

    default Integer lenOrNull(Property<T1, ?> column) {
        return lenOrDefault(column, null);
    }

    Integer lenOrDefault(Property<T1, ?> column, Integer def);


    default T1 firstOrNull() {
        return firstOrNull(queryClass());
    }

    <TR> TR firstOrNull(Class<TR> resultClass);

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryConcurrentException}
     *
     * @param msg
     * @return
     */
    default T1 firstNotNull(String msg) {
        return firstNotNull(msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryConcurrentException}
     *
     * @param msg
     * @param code
     * @return
     */
    default T1 firstNotNull(String msg, String code) {
        return firstNotNull(queryClass(), msg, code);
    }

    default <TR> TR firstNotNull(Class<TR> resultClass, String msg) {
        return firstNotNull(resultClass, msg, null);
    }

    <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code);

    List<T1> toList();

    default Map<String, Object> toMap() {
        limit(1);
        List<Map<String, Object>> maps = toMaps();
        return EasyCollectionUtil.firstOrNull(maps);
    }

    List<Map<String, Object>> toMaps();


    <TR> List<TR> toList(Class<TR> resultClass);

    /**
     * 返回执行sql
     *
     * @return
     */
    default String toSql() {
        return toSql(queryClass());
    }

    default String toSql(SqlParameterCollector sqlParameterCollector) {
        return toSql(queryClass(), sqlParameterCollector);
    }

    /**
     * 返回执行sql
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    default <TR> String toSql(Class<TR> resultClass) {
        return toSql(resultClass, null);
    }

    <TR> String toSql(Class<TR> resultClass, SqlParameterCollector sqlParameterCollector);

    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    Queryable<T1> select(SqlExpression<SqlColumnSelector<T1>> selectExpression);

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
     *
     * @param resultClass
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    Queryable<T1> select(String columns);

    Queryable<T1> select(ColumnSegment columnSegment, boolean clearAll);

    default Queryable<T1> where(SqlExpression<SqlWherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    Queryable<T1> where(boolean condition, SqlExpression<SqlWherePredicate<T1>> whereExpression);

    default Queryable<T1> whereById(Object id) {
        return whereById(true, id);
    }

    Queryable<T1> whereById(boolean condition, Object id);

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
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取 {@link SqlWherePredicate}
     */
    Queryable<T1> whereObject(boolean condition, Object object);

    default Queryable<T1> groupBy(SqlExpression<SqlGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable<T1> groupBy(boolean condition, SqlExpression<SqlGroupBySelector<T1>> selectExpression);

    default Queryable<T1> having(SqlExpression<SqlAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    Queryable<T1> having(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateExpression);

    default Queryable<T1> orderByAsc(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default Queryable<T1> orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default Queryable<T1> orderByDesc(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable<T1> orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default Queryable<T1> orderBy(SqlExpression<SqlColumnSelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    Queryable<T1> orderBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link EasyOrderBy} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SqlColumnSelector}
     */
    default Queryable<T1> orderByDynamic(EasyOrderBy configuration) {
        return orderByDynamic(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link EasyOrderBy} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SqlColumnSelector}
     */
    Queryable<T1> orderByDynamic(boolean condition, EasyOrderBy configuration);

    default Queryable<T1> distinct() {
        return distinct(true);
    }

    Queryable<T1> distinct(boolean condition);

    default Queryable<T1> limit(long rows) {
        return limit(true, rows);
    }

    default Queryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    default Queryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    Queryable<T1> limit(boolean condition, long offset, long rows);

    default EasyPageResult<T1> toPageResult(long pageIndex, long pageSize) {
        return toPageResult(pageIndex, pageSize, -1);
    }

    /**
     * 分页 如果{@param pageTotal}  < 0 那么将会查询一次count,否则不查询count在total非常大的时候可以有效的提高性能
     *
     * @param pageIndex
     * @param pageSize
     * @param pageTotal
     * @return
     */
    EasyPageResult<T1> toPageResult(long pageIndex, long pageSize, long pageTotal);

    //    PageResult<T1> toPageResult(long pageIndex, long pageSize, SqlExpression<ColumnSelector<T1>> selectExpression);
//    <TR> PageResult<TR> toPageResult(long pageIndex, long pageSize, Class<TR> clazz);
//    <TR> PageResult<TR> toPageResult(long pageIndex, long pageSize, Class<TR> clazz, SqlExpression<ColumnAsSelector<T1,TR>> selectExpression);
    <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on);

    <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on);

    EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1();

//    Queryable<T1> unionAll(Queryable<T1>... otherQueryables);

//    Queryable<T1> logicDelete(boolean use);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link com.easy.query.core.api.client.EasyQuery#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    Queryable<T1> asTracking();

    Queryable<T1> asNoTracking();


}
