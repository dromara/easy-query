package com.easy.query.core.func.column;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

/**
 * create time 2023/10/12 14:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncSQLParameterExpression extends ColumnExpression {
    SQLParameter getSQLParameter();
}
