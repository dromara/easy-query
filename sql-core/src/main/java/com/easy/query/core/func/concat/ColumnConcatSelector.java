package com.easy.query.core.func.concat;

import com.easy.query.core.expression.parser.core.SQLTableOwner;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnConcatSelector {
    ColumnConcatSelector column(String property);
    ColumnConcatSelector column(SQLTableOwner tableOwner, String property);
}
