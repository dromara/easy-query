package com.easy.query.core.common;

import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2025/4/19 14:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryToGroupJoinTrueFalseProvider {

    void acceptValue(ColumnFuncSelector selector, boolean value);
}
