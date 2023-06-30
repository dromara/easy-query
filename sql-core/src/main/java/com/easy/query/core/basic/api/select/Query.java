package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryFirstOrNotNullException;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Map;

/**
 * @FileName: Query.java
 * @Description: 文件说明
 * @Date: 2023/3/3 16:30
 * @author xuejiaming
 */
public interface Query<T> {
    /**
     * 当前查询对象的字节信息
     *
     * @return 当前查询的对象字节
     */
    Class<T> queryClass();

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    Query<T> cloneQueryable();

    /**
     * 当前的查询表达式
     *
     * @return
     */
    EntityQueryExpressionBuilder getSQLEntityExpressionBuilder();

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    Query<T> select(String columns);

    /**
     * 返回执行sql
     *
     * @return
     */
    default String toSQL() {
        return toSQL(queryClass());
    }

    default String toSQL(ToSQLContext toSQLContext) {
        return toSQL(queryClass(), toSQLContext);
    }

    /**
     * 返回执行sql
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    default <TR> String toSQL(Class<TR> resultClass) {
        TableContext tableContext = getSQLEntityExpressionBuilder().getExpressionContext().getTableContext();
        return toSQL(resultClass, DefaultToSQLContext.defaultToSQLContext(tableContext));
    }

    <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext);

    /**
     * 返回long类型的数量结果
     * @return
     */
    long count();

    /**
     * 返回int类型的数量结果
     * @return
     */

    default int intCount() {
        return (int) count();
    }

    /**
     * 判断是否存在
     * eg. SELECT  1  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return 如果有行数那么就就是返回true表示存在，否则返回false表示不存在
     */
    boolean any();

    /**
     * 返回第一条,如果第一条没有就返回null
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     * @return
     */

    default T firstOrNull() {
        return firstOrNull(queryClass());
    }

    /**
     * 返回第一条,如果第一条没有就返回null,并且select并不是表的全部而是映射到 {@code resultClass} 上
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     * @param resultClass
     * @return
     * @param <TR>
     */

    <TR> TR firstOrNull(Class<TR> resultClass);

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     * @param msg
     * @return
     */
    default T firstNotNull(String msg) {
        return firstNotNull(msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     * @param msg
     * @param code
     * @return
     */
    default T firstNotNull(String msg, String code) {
        return firstNotNull(queryClass(), msg, code);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * @param resultClass
     * @param msg
     * @return
     * @param <TR>
     */

    default <TR> TR firstNotNull(Class<TR> resultClass, String msg) {
        return firstNotNull(resultClass, msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * @param resultClass
     * @param msg
     * @param code
     * @return
     * @param <TR>
     */
    <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code);

    List<T> toList();

    default Map<String, Object> toMap() {
        limit(1);
        List<Map<String, Object>> maps = toMaps();
        return EasyCollectionUtil.firstOrNull(maps);
    }

    List<Map<String, Object>> toMaps();



    default EasyPageResult<T> toPageResult(long pageIndex, long pageSize) {
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
    EasyPageResult<T> toPageResult(long pageIndex, long pageSize, long pageTotal);

    default EasyPageResult<T> toShardingPageResult(long pageIndex, long pageSize) {
        return toShardingPageResult(pageIndex, pageSize, null);
    }

    EasyPageResult<T> toShardingPageResult(long pageIndex, long pageSize, List<Long> totalLines);


    default Query<T> limit(long rows) {
        return limit(true, rows);
    }

    default Query<T> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    default Query<T> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    Query<T> limit(boolean condition, long offset, long rows);
}
