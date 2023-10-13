package com.easy.query.core.func.column;

import com.easy.query.core.expression.parser.core.SQLTableOwner;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncSelector {
    ColumnFuncSelector column(String property);
    ColumnFuncSelector column(SQLTableOwner tableOwner, String property);
    ColumnFuncSelector value(Object val);
    ColumnFuncSelector format(Object valFormat);
}
