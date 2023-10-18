package com.easy.query.core.func.column;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncSelector {
    ColumnFuncSelector column(String property);
    ColumnFuncSelector column(SQLTableOwner tableOwner, String property);
    ColumnFuncSelector column(TableAvailable table, String property);
    ColumnFuncSelector value(Object val);
    ColumnFuncSelector format(Object valFormat);
    ColumnFuncSelector sql(SQLSegment sqlSegment);
    ColumnFuncSelector sqlFunc(SQLFunction sqlFunction);
}
