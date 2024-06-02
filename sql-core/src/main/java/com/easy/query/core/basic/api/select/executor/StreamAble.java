package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.expression.lambda.SQLConsumer;

import java.sql.Statement;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * create time 2023/10/20 23:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamAble<T> extends QueryAvailable<T> {

    /**
     * 可迭代的流式结果集
     *
     * <blockquote><pre>
     *     {@code
     *
     * try(JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).toStreamResult(100)){
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
     * @param fetchSize null表示不设置
     * @return
     */
    @NotNull
    default JdbcStreamResult<T> toStreamResult(Integer fetchSize) {
        return toStreamResult(s -> {
            if (fetchSize != null) {
                s.setFetchSize(fetchSize);
            }
        });
    }

   @NotNull
   JdbcStreamResult<T> toStreamResult(SQLConsumer<Statement> configurer);

    /**
     * 以stream的模式拉取数据,如果当前表达式存在include/includes操作那么将以toList+stream进行二次处理
     *
     * @param fetcher 如何消费拉取的数据
     * @param <TR>    返回结果类型
     * @return 返回结果
     */
    default <TR> TR streamBy(Function<Stream<T>, TR> fetcher) {
        return streamBy(fetcher, Integer.MAX_VALUE);
    }

    /**
     * 以stream的模式拉取数据,如果当前表达式存在include/includes操作那么{@code fetchSize}参数将不会生效
     *
     * @param fetcher   如何消费拉取的数据
     * @param fetchSize jdbc的statement用于每次拉取大小
     * @param <TR>      返回结果类型
     * @return 返回结果
     */
    default <TR> TR streamBy(Function<Stream<T>, TR> fetcher, Integer fetchSize) {
        return streamBy(fetcher, statement -> {
            statement.setFetchSize(fetchSize);
        });
    }

    /**
     * 以stream的模式拉取数据,如果当前表达式存在include/includes操作那么{@code configurer}参数将不会生效
     *
     * @param fetcher    如何处理结果迭代支持返回stream结果
     * @param configurer 支持配置jdbc的statement譬如每次拉取大小
     * @param <TR>       返回结果类型
     * @return 返回结果
     */
    <TR> TR streamBy(Function<Stream<T>, TR> fetcher, SQLConsumer<Statement> configurer);

}
