package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/4/1 22:02
 * 不具备传递性
 *
 * @author xuejiaming
 */
public interface LogicDeletable<TChain> {
    /**
     * 禁用逻辑删除
     * @return
     */
    default TChain disableLogicDelete() {
        return useLogicDelete(false);
    }

    /**
     * 启用逻辑删除
     * @return
     */
    default TChain enableLogicDelete() {
        return useLogicDelete(true);
    }

    /**
     * 是否使用逻辑删除
     * @param enable true:使用,false:不使用
     * @return
     */
    TChain useLogicDelete(boolean enable);
}
