package com.easy.query.core.basic.api.internal;

import java.util.function.Supplier;

/**
 * create time 2024/2/26 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableLogicDeletable<TChain> {

    /**
     * 前一个表是否使用逻辑删除
     * @param tableLogicDel 返回结果 true表示使用逻辑删除,返回结果 false表示不使用逻辑删除,返回null表示不设置
     * @return
     */
    TChain tableLogicDelete(Supplier<Boolean> tableLogicDel);
}
