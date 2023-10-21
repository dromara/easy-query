package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;

/**
 * create time 2023/10/20 23:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamResultAble<T> extends QueryAvailable<T> {

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
}
