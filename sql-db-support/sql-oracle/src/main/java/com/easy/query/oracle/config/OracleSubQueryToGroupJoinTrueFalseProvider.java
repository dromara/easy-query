package com.easy.query.oracle.config;

import com.easy.query.core.common.SubQueryToGroupJoinTrueFalseProvider;
import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2025/4/19 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleSubQueryToGroupJoinTrueFalseProvider implements SubQueryToGroupJoinTrueFalseProvider {
    @Override
    public void acceptValue(ColumnFuncSelector selector, boolean value) {
        if(value){
            selector.format(1);
        }else{
            selector.format(0);
        }
    }
}
