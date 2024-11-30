package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/5/20 08:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryStrategy<TChain> {
    /**
     * 查询是否忽略列为large的
     * 针对columnAll调用
     * @param queryLarge
     * @return
     */
    @Deprecated
    TChain queryLargeColumn(boolean queryLarge);
}
