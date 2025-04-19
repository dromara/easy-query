package com.easy.query.core.common;

import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2025/4/19 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSubQueryToGroupJoinTrueFalseProvider implements SubQueryToGroupJoinTrueFalseProvider{
    @Override
    public void acceptValue(ColumnFuncSelector selector, boolean value) {
        selector.value(value);
    }
}
