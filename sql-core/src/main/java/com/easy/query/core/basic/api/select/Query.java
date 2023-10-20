package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.executor.QueryExecutable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: Query.java
 * @Description: 文件说明
 * @Date: 2023/3/3 16:30
 */
public interface Query<T> extends QueryAvailable<T> , QueryExecutable<T> {

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
     * queryable.select(" t.name,t.age ")通过字符串实现要查询的列
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

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     *
     * @param toSQLContext
     * @return
     */

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
     * eg. SELECT  COUNT(*)  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */
    long count();

    /**
     * 返回int类型的数量结果
     * eg. SELECT  COUNT(*)  FROM table t [WHERE t.`columns` = ?]
     *
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
     * 去重
     * eg. SELECT DISTINCT projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */
    default Query<T> distinct() {
        return distinct(true);
    }


    /**
     * 去重 {@param condition} 为true就使用distinct,false则不使用
     * eg. SELECT DISTINCT projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param condition
     * @return
     */
    Query<T> distinct(boolean condition);

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


    Query<T> asTracking();

    Query<T> asNoTracking();

    Query<T> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    Query<T> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    Query<T> useConnectionMode(ConnectionModeEnum connectionMode);
}
