package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOnDuplicateKeyUpdate<TChain> {
    default TChain onConflictDoUpdate(){
        return onDuplicateKeyUpdate();
    }
    TChain onDuplicateKeyUpdate();
}
