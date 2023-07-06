package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/6 21:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOnDuplicateKeyIgnore<TChain> {
    /**
     * 部分数据库不支持
     * @return
     */
    TChain onDuplicateKeyIgnore();
}
