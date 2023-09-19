package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pagination;
import com.easy.query.core.api.pagination.ShardingPagination;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryFirstOrNotNullException;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Map;

/**
 * @author xuejiaming
 * @FileName: Query.java
 * @Description: 文件说明
 * @Date: 2023/3/3 16:30
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
     * 返回第一条,如果第一条没有就返回null
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return
     */

    default T firstOrNull() {
        return firstOrNull(queryClass());
    }

    /**
     * 返回第一条,如果第一条没有就返回null,并且select并不是表的全部而是映射到 {@code resultClass} 上
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass
     * @param <TR>
     * @return
     */

    <TR> TR firstOrNull(Class<TR> resultClass);

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param msg
     * @return
     */
    default T firstNotNull(String msg) {
        return firstNotNull(msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param msg
     * @param code
     * @return
     */
    default T firstNotNull(String msg, String code) {
        return firstNotNull(queryClass(), msg, code);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass
     * @param msg
     * @param <TR>
     * @return
     */

    default <TR> TR firstNotNull(Class<TR> resultClass, String msg) {
        return firstNotNull(resultClass, msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass 返回结果
     * @param msg
     * @param code
     * @param <TR>
     * @return
     */
    <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code);

    /**
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return 获取查询结果集
     */
    default List<T> toList() {
        return toList(queryClass());
    }

    /**
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 映射对象
     * @param <TR>        映射对象类型
     * @return 获取查询结果集
     */
    <TR> List<TR> toList(Class<TR> resultClass);

    /**
     * 可迭代的流式结果集
     *
     * <blockquote><pre>
     *     {@code
     *
     * try(JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).toStreamResult()){
     *
     *             int i = 0;
     *             for (BlogEntity blog : streamResult.getStreamIterable()) {
     *                 String indexStr = String.valueOf(i);
     *                 Assert.assertEquals(indexStr, blog.getId());
     *                 Assert.assertEquals(indexStr, blog.getCreateBy());
     *                 Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
     *                 Assert.assertEquals(indexStr, blog.getUpdateBy());
     *                 i++;
     *             }
     *         } catch (SQLException e) {
     *             throw new RuntimeException(e);
     *         }
     * </pre></blockquote>
     *
     * @return
     */
    default JdbcStreamResult<T> toStreamResult() {
        return toStreamResult(queryClass());
    }

    /**
     * 可迭代的流式相当于 queryable.select(resultClass,o->o.columnAll()).toStreamResult();
     * <blockquote><pre>
     *     {@code
     *
     * try(JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).toStreamResult()){
     *
     *             int i = 0;
     *             for (BlogEntity blog : streamResult.getStreamIterable()) {
     *                 String indexStr = String.valueOf(i);
     *                 Assert.assertEquals(indexStr, blog.getId());
     *                 Assert.assertEquals(indexStr, blog.getCreateBy());
     *                 Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
     *                 Assert.assertEquals(indexStr, blog.getUpdateBy());
     *                 i++;
     *             }
     *         } catch (SQLException e) {
     *             throw new RuntimeException(e);
     *         }
     * </pre></blockquote>
     *
     * @param resultClass 映射结果集
     * @param <TR>
     * @return 可迭代的流式结果
     */
    <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass);

    /**
     * 将查询结果第一条映射到map中,map的key值忽略大小写,其中key为列名
     *
     * @return map结果
     */
    default Map<String, Object> toMap() {
        limit(1);
        List<Map<String, Object>> maps = toMaps();
        return EasyCollectionUtil.firstOrNull(maps);
    }

    /**
     * 将查询结果映射到map中,map的key值忽略大小写,其中key为列名
     *
     * @return map集合结果
     */
    List<Map<String, Object>> toMaps();


    /**
     * 分页获取结果
     *
     * @param pageIndex 第几页 默认第一页为1
     * @param pageSize  每页多少条
     * @return 分页结果
     */
    default EasyPageResult<T> toPageResult(long pageIndex, long pageSize) {
        return toPageResult(pageIndex, pageSize, -1);
    }

    /**
     * 分页 如果{@param pageTotal}  < 0 那么将会查询一次count,否则不查询count在total非常大的时候可以有效的提高性能
     *
     * @param pageIndex 第几页 默认第一页为1
     * @param pageSize  每页多少条
     * @param pageTotal 总条数有多少
     * @return 分页结果
     */
    EasyPageResult<T> toPageResult(long pageIndex, long pageSize, long pageTotal);

    /**
     * 进行用户自定义分页结果
     * @param pageIndex
     * @param pageSize
     * @param pageTotal
     * @return
     */
    default Pagination<T> toPage(long pageIndex, long pageSize, long pageTotal) {
        return new Pagination<>(this, pageIndex, pageSize, pageTotal);
    }

    /**
     * 进行用户自定义分页结果
     * @param pageIndex
     * @param pageSize
     * @return
     */
    default Pagination<T> toPage(long pageIndex, long pageSize) {
        return toPage(pageIndex, pageSize, -1);
    }

    /**
     * 分片的分页结果,对分片而言有相对较高的性能和优化点
     *
     * @param pageIndex 第几页 默认第一页为1
     * @param pageSize  总条数有多少
     * @return 分页结果
     */
    default EasyPageResult<T> toShardingPageResult(long pageIndex, long pageSize) {
        return toShardingPageResult(pageIndex, pageSize, null);
    }

    /**
     * 分片的分页结果,对分片而言有相对较高的性能和优化点,通过{@param totalLines}指定顺序分片的结果无需count即可精确到具体
     *
     * @param pageIndex  第几页 默认第一页为1
     * @param pageSize   总条数有多少
     * @param totalLines 分页各个分页节点的数量
     * @return 分页结果
     */
    EasyPageResult<T> toShardingPageResult(long pageIndex, long pageSize, List<Long> totalLines);


    /**
     * 进行用户自定义分页结果
     * @param pageIndex
     * @param pageSize
     * @param totalLines
     * @return
     */
    default ShardingPagination<T> toShardingPage(long pageIndex, long pageSize, List<Long> totalLines) {
        return new ShardingPagination<>(this, pageIndex, pageSize, totalLines);
    }

    /**
     * 进行用户自定义分页结果
     * @param pageIndex
     * @param pageSize
     * @return
     */
    default ShardingPagination<T> toShardingPage(long pageIndex, long pageSize) {
        return toShardingPage(pageIndex, pageSize, null);
    }
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
