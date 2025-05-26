package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.QueryAvailable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * create time 2023/10/20 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PageAble<T> extends QueryAvailable<T> {
    /**
     * 分页获取结果
     *
     * @param pageIndex 第几页 默认第一页为1
     * @param pageSize  每页多少条
     * @return 分页结果
     */
    @NotNull
    default EasyPageResult<T> toPageResult(long pageIndex, long pageSize) {
        return toPageResult(pageIndex, pageSize, -1);
    }

    @NotNull
    default <TResult> EasyPageResult<TResult> toPageResult(@NotNull Class<TResult> resultClass, long pageIndex, long pageSize) {
        return toPageResult(resultClass, pageIndex, pageSize, -1);
    }

    /**
     * 分页 如果{@param pageTotal}  < 0 那么将会查询一次count,否则不查询count在total非常大的时候可以有效的提高性能
     *
     * @param pageIndex 第几页 默认第一页为1
     * @param pageSize  每页多少条
     * @param pageTotal 总条数有多少
     * @return 分页结果
     */
    @NotNull
    default EasyPageResult<T> toPageResult(long pageIndex, long pageSize, long pageTotal) {
        return toPageResult(queryClass(), pageIndex, pageSize, pageTotal);
    }

    @NotNull
    <TResult> EasyPageResult<TResult> toPageResult(@NotNull Class<TResult> resultClass, long pageIndex, long pageSize, long pageTotal);

    @NotNull
    default <TPageResult> TPageResult toPageResult(@NotNull Pager<T, TPageResult> pager) {
        return pager.toResult(this);
    }

    /**
     * 分片的分页结果,对分片而言有相对较高的性能和优化点
     *
     * @param pageIndex 第几页 默认第一页为1
     * @param pageSize  总条数有多少
     * @return 分页结果
     */
    @NotNull
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
    @NotNull
    default EasyPageResult<T> toShardingPageResult(long pageIndex, long pageSize, List<Long> totalLines) {
        return toShardingPageResult(queryClass(), pageIndex, pageSize, totalLines);
    }

    @NotNull
    <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> resultClass, long pageIndex, long pageSize, List<Long> totalLines);

}
