package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/6 21:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOnDuplicateKeyIgnore<TChain> {

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onDuplicateKeyIgnore();

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    default TChain onConflictDoNothing(){
        return onDuplicateKeyIgnore();
    }

}
