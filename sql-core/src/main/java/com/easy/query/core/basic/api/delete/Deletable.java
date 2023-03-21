package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.abstraction.SqlExecuteExpectRows;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @Created by xuejiaming
 */
public interface Deletable<T, TChain> extends SqlExecuteExpectRows {
    /**
     * 语句转成sql
     * @return
     */
    String toSql();

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
     * @param enable
     * @return
     */
    TChain useLogicDelete(boolean enable);

    /**
     * 是否允许删除命令
     *
     * @param allow
     * @return
     */
    TChain allowDeleteCommand(boolean allow);
}
