package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.abstraction.SqlExecuteExpectRows;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @Created by xuejiaming
 */
public interface Deletable<T,TChain> extends SqlExecuteExpectRows {
    String toSql();
    TChain disableLogicDelete();
}
